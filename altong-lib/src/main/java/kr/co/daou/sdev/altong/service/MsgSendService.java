package kr.co.daou.sdev.altong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import kr.co.daou.sdev.altong.domain.alert.AlertMaster;
import kr.co.daou.sdev.altong.enumeration.AlertStatusType;
import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;
import kr.co.daou.sdev.altong.dto.alert.AlertResultDto;
import kr.co.daou.sdev.altong.send.DaouOfficeUnityMsgImpl;
import kr.co.daou.sdev.altong.send.MailUnityMsgImpl;
import kr.co.daou.sdev.altong.send.PushUnityMsgImpl;
import kr.co.daou.sdev.altong.send.SmsUnityMsgImpl;
import kr.co.daou.sdev.altong.send.UnityMsg;
import kr.co.daou.sdev.altong.send.model.ConnectInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * kr.co.daou.sdev.altong.service
 * MsgSendService.java
 * 메시지 송신을 담당하는 서비스
 * </pre>
 *
 *	@author  : yoonsm
 *  @Date    :2017. 11. 14.
 *  @Version : 0.1
 */
@Slf4j
@Service
public class MsgSendService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private AlertMasterService alertMasterService;
	
	@Autowired
	private AlertResultService alertResultService; 
	
	/**
	 * <pre>
	 * 처리내용 : 알림 상태를 갱신한다.
	 * </pre>
	 * @Method Name : saveAlertMasterStatus
	 * @param alertMaster
	 * @param alertStatusType
	 */
	public void saveAlertMasterStatus(AlertMaster alertMaster,AlertStatusType alertStatusType) {
		if(alertStatusType != null)
			alertMasterService.changeMasterStatus(alertMaster.getAlertNo(), alertStatusType);
	}

	/**
	 * <pre>
	 * 처리내용 : 알림 상태를 갱신한다.(Result)
	 * </pre>
	 * @Method Name : saveAlertMasterStatus
	 * @param alertResult
	 * @param sendStatusType
	 */
	public void saveResult(AlertMaster alertMaster, Boolean sendStatus) {
		// 발송 성공
		if(sendStatus == true) {
			// 발송 결과 이력 성공 처리
			alertMaster.getProject().getMembers().stream().forEach(member -> this.saveResult(member, alertMaster, true));
		}
		// 발송 실패
		else {
			// 발송 결과 이력 실패 처리
			alertMaster.getProject().getMembers().stream().forEach(member -> this.saveResult(member, alertMaster, false));
		}
		
	}
	
	private void saveResult(Member member, AlertMaster alertMaster, Boolean sendStatus) {
		AlertResultDto alertResultDto = new AlertResultDto();
		alertResultDto.setSendStatus(sendStatus);
		alertResultDto.setMemberNo(member.getMemberNo());
		alertResultDto.setAlertNo(alertMaster.getAlertNo());
		alertResultDto.setSendType(alertMaster.getProject().getSendType());
		alertResultService.saveResult(alertResultDto);	
	}
	
	/**
	 * <pre>
	 * 처리내용 : 첫번째 발송 대상 알림마스터를 패치한다.
	 * </pre>
	 * @Method Name : getAlertFirstMasterList
	 * @return
	 */
	public List<AlertMaster> getAlertMasterList(int count) {
		if(count == 1) 
			return alertMasterService.getAlertFirstMasterList();
		else if(count == 2)
			return alertMasterService.getAlertSecondMasterList();
		else
			return null;
	}

	/**
	 * <pre>
	 * 처리내용 : 데이타객체 alertMaster를 기반으로 Unity
	 * </pre>
	 * @Method Name : createUnityMsg
	 * @param alertMaster
	 * @return
	 */
	public UnityMsg createUnityMsg(AlertMaster alertMaster, int count) {
		
		// alertMaster에서 프로젝트를 정보를 취득하고 
		Project  project =  alertMaster.getProject();
		UnityMsg unityMsg = createUnityMsg(project.getSendType());
		
		// 실패일때 실패 코드 삽입
		if(unityMsg == null) {
			return null;
		}

		UnityMsgContents contents = new UnityMsgContents();
		contents.setProjectName(project.getProjectName());
		contents.setLevel("INFO");
		contents.setBody(alertMaster.getMessage());

		if(count == 1) {
			log.debug("createUnityMsg sendType = {} ", project.getSendType().toString());
			project.getMembers().stream().forEach(member -> contents.addTarget(member.getTarget(project.getSendType())));
		}else if(count == 2) {
			log.debug("createUnityMsg reSendType = {}", project.getResendType().toString());
			project.getMembers().stream().forEach(member -> contents.addTarget(member.getTarget(project.getResendType())));
		// 실패처리
		} else
			return null;
		
		unityMsg.setContents(contents);
		
		return unityMsg;
	}
	
	/**
	 * <pre>
	 * 처리내용 : 객체를 생성한다. 접속이 안될 경우 null 을 리턴한다.
	 * </pre>
	 * @Method Name : createUnityMsg
	 * @param sendType
	 * @param contents
	 * @return
	 */
	public UnityMsg createUnityMsg(SendType sendType) {
		
		UnityMsg unityMsg = null;
		
		try {
			switch (sendType) {
			case SMS:
				unityMsg = new SmsUnityMsgImpl(getConnectInfo(sendType));
				break;
			case DAOU_OFFICE:
				unityMsg = new DaouOfficeUnityMsgImpl(getConnectInfo(sendType));
				break;
			case PUSH:
				unityMsg = new PushUnityMsgImpl(getConnectInfo(sendType));
				break;
			case EMAIL:
				unityMsg = new MailUnityMsgImpl(getConnectInfo(sendType));
				break;
			}
			// 접속에 성공한 경우 객체를 리턴한다.
			if(unityMsg != null && unityMsg.isConnect()) {
				log.info("UnityMsg connected ... {}",sendType.getName());
				return unityMsg;
			} else {
				log.error("UnityMsg connect fail ... {}",sendType.getName());
			}
		} catch (Exception e) {
			log.error("UnityMsg Exception ... {}",sendType.getName());
			unityMsg = null;
		}
		return unityMsg;
	}
	
	private ConnectInfo getConnectInfo(SendType sendType) {

		ConnectInfo connectInfo = new ConnectInfo();
		connectInfo.setLocale("ko");
		connectInfo.setHost(env.getProperty("connectInfo."+sendType.getName()+".host"));
		connectInfo.setUserId(env.getProperty("connectInfo."+sendType.getName()+".userId"));
		connectInfo.setPassword(env.getProperty("connectInfo."+sendType.getName()+".password"));
		connectInfo.setPort(env.getProperty("connectInfo."+sendType.getName()+".port",Integer.class));
		
		return connectInfo;
	}
}
