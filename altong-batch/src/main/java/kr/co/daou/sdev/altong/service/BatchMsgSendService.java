package kr.co.daou.sdev.altong.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import kr.co.daou.sdev.altong.AltongConst;
import kr.co.daou.sdev.altong.batch.send.MsgSendWorker;
import kr.co.daou.sdev.altong.domain.alert.AlertMaster;
import kr.co.daou.sdev.altong.domain.alert.AlertStatusType;
import kr.co.daou.sdev.altong.send.UnityMsg;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 메시지발송 Job에서 사용될 서비스
 * kr.co.daou.sdev.altong.service
 * MsgSendService.java
 * </pre>
 *
 *	@author  : yoonsm
 *  @Date    :2017. 12. 19.
 *  @Version : 0.1
 */
@Slf4j
@Service
public class BatchMsgSendService {

	@Autowired
	private Environment env;
	private List<MsgSendWorker> sendProcessList = new ArrayList<MsgSendWorker>(); // 송신 프로세스 리스트

	@Autowired
	private MsgSendService msgSendService;
	
	int sendProcessCnt = 0;


	/**
	 * <pre>
	 * 처리내용 :발송프로세스를 관리한다.
	 * 지정된 쓰레드 개수만큼 쓰레드를 늘린다. ( 한번에 한개씩 )
	 * </pre>
	 * @Method Name : manageSendProcess
	 */
	public void manageSendProcess() {
		
		log.info("발송 프로세스 관리");
		if(sendProcessList.size() < env.getProperty("send.worker.count",Integer.class)) {
			MsgSendWorker worker = new MsgSendWorker(msgSendService);
			worker.setName(Integer.toString(sendProcessCnt++));
			
			log.debug("worker create thread no : {}",worker.getName());
			sendProcessList.add(worker);
			worker.start();
		}
	}
	
	
	public void fetchFirstMessage() {
		// 패치카운트
		int listCnt = 0;
		try {
			// 캠페인을 취득한다. 해당캠페인의 프로젝트 및 멤버를 취득
			List<AlertMaster> alertMasterList = msgSendService.getAlertMasterList(1);
			// 취득한 캠페인의 상태를 발송중으로 갱신
			alertMasterList.stream().forEach(alert -> msgSendService.saveAlertMasterStatus(alert, AlertStatusType.ING));
			
			if(alertMasterList != null ) listCnt = alertMasterList.size();

			if (listCnt > 0) {
				while (listCnt > 0) {
					AlertMaster alertMaster = alertMasterList.get(listCnt - 1);
					MsgSendWorker worker = getIdleWorker();
					UnityMsg unityMsg = msgSendService.createUnityMsg(alertMaster, 1);

					worker.sendMsg(unityMsg, alertMaster, 1);

					listCnt--;
					Thread.sleep(AltongConst.THREAD_SLEEP);
				}
			} else {
				log.debug("alert 대상이 없음");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void fetchSecondMessage() {
		
		// 패치카운트
		int listCnt = 0;
		try {
			// 캠페인을 취득한다. 해당캠페인의 프로젝트 및 멤버를 취득(2차 발송 대상)
			List<AlertMaster> alertMasterList = msgSendService.getAlertMasterList(2);
			// 취득한 캠페인의 상태를 발송중으로 갱신
			alertMasterList.stream().forEach(alert -> msgSendService.saveAlertMasterStatus(alert, AlertStatusType.REING));
			
			if(alertMasterList != null ) listCnt = alertMasterList.size();

			if (listCnt > 0) {
				while (listCnt > 0) {
					AlertMaster alertMaster = alertMasterList.get(listCnt - 1);
					MsgSendWorker worker = getIdleWorker();
					UnityMsg unityMsg = msgSendService.createUnityMsg(alertMaster, 2);

					worker.sendMsg(unityMsg, alertMaster, 2);

					listCnt--;
					Thread.sleep(AltongConst.THREAD_SLEEP);
				}
			} else {
				log.debug("alert 대상이 없음");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		
	}

	
	/**
	 * <pre>
	 * 처리내용 : 메시지 발송가능한 워커를 취득한다.
	 * </pre>
	 * @Method Name : getIdleWorker
	 * @return
	 */
	public MsgSendWorker getIdleWorker() {
		synchronized ( sendProcessList  ){
			for(MsgSendWorker msw : sendProcessList){
				if(!msw.isWorking()) return msw;
			}
			return null;
		}
	}
	
	/**
	 * <pre>
	 * 처리내용 : 스프링이 종료될때 호출되는 메소드
	 * </pre>
	 * @Method Name : destroyService
	 */
	@PreDestroy
	public void destroyService(){
		while (!sendProcessList.isEmpty()) {
			for (Iterator<MsgSendWorker> it = sendProcessList.iterator(); it.hasNext();) {
				MsgSendWorker value = it.next();
				if (!value.isWorking()) {
					log.info("{} thread 종료 처리 중..", value.getName());
					value.stopProcess();
					it.remove();
				} else {
					try {
						Thread.sleep(500);
						log.info("{} 미처리 송신건 처리중 종료까지 대기",value.getName());
					} catch (InterruptedException e) {
						log.error("{} 미처리 송신건 처리중 에러",value.getName());
					}
				}
			}
		}
	}
}
