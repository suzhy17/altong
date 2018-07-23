package kr.co.daou.sdev.altong.batch.send;

import org.apache.commons.lang3.StringUtils;

import kr.co.daou.sdev.altong.AltongConst;
import kr.co.daou.sdev.altong.domain.alert.AlertMaster;
import kr.co.daou.sdev.altong.enumeration.AlertStatusType;
import kr.co.daou.sdev.altong.send.UnityMsg;
import kr.co.daou.sdev.altong.service.MsgSendService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MsgSendWorker extends Thread{

	private boolean isLive = true;
	private UnityMsg unityMsg = null;
	private AlertMaster alertMaster = null;
	private MsgSendService msgSendService;
	private int count = 0;

	public MsgSendWorker(MsgSendService msgSendService) {
		this.msgSendService = msgSendService;
	}
	
	public void sendMsg(UnityMsg unityMsg, AlertMaster alertMaster, int count) {
		if(unityMsg != null) {
			this.unityMsg = unityMsg;
			this.alertMaster = alertMaster;
			this.count = count;
		} else {
			log.info("thread {} is working target project : {}",this.getName(),unityMsg.getContents().getProjectName());
		}
	}
	
	public boolean isLive() {
		return isLive;
	}

	public boolean isWorking() {
		return (unityMsg != null)?true:false;
	}
	
	/**
	 * <pre>
	 * 처리내용 : 처리가 모두 끝나면 쓰레드를 종료한다.
	 * </pre>
	 * @Method Name : stopProcess
	 */
	public void stopProcess() {
		this.isLive = false;
	}
	
	private void setFree() {
		unityMsg = null;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted() && isLive) {
				if (isWorking()) {
					
					log.info("발송처리 시작 : {}",unityMsg.getContents().getProjectName());
					
					AlertStatusType alertStatusTypeFail;
					
					// 1회차 발송
					if(count == 1)
						alertStatusTypeFail = AlertStatusType.REREADY;		// 2차 발송 대기로 변경
					// 2회차 발송
					else if(count == 2)
						alertStatusTypeFail = AlertStatusType.ERROR;			// 발송 실패로 변경
					else
						alertStatusTypeFail = null;
					
					// 발송 실패일때(isConnect => fail)
					if(unityMsg == null) {
						// 캠페인 상태 변경(실패)
						msgSendService.saveAlertMasterStatus(alertMaster, alertStatusTypeFail);
						
						// 전송 결과 등록(실패)
						msgSendService.saveResult(alertMaster, false);
						log.info("발송처리 결과 : 실패(connect fail) {}",alertMaster.getAlertNo().toString());
					}else {

						// 발송 성공
						if(StringUtils.equals( unityMsg.send(),AltongConst.RESULT.SUCCESS.getValue())){
							// 캠페인 상태 변경( 발송완료)
							msgSendService.saveAlertMasterStatus(alertMaster, AlertStatusType.OK);
							
							log.debug("MsgSendWorker발송성공={}"+alertMaster.getAlertNo().toString());
							// 전송 결과 등록(성공)
							msgSendService.saveResult(alertMaster, true);
							log.info("발송처리 결과 : 성공 {}",alertMaster.getAlertNo().toString());
							
						// 발송 실패	
						} else {
							// 캠페인 상태 변경(실패)
							msgSendService.saveAlertMasterStatus(alertMaster, alertStatusTypeFail);
							
							// 전송 결과 등록(실패)
							msgSendService.saveResult(alertMaster, false);
							log.info("발송처리 결과 : 실패(send fail) {}",alertMaster.getAlertNo());
						}
					}

					sleep(AltongConst.SEND_SLEEP_TIME);
					setFree();
				}
				sleep(AltongConst.SEND_SLEEP_TIME);
			}
			log.info("thread {} stop",this.getName());
		} catch (InterruptedException e) {
			log.error("InterruptedException");
		}
	}
}
