package kr.co.daou.sdev.altong.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.daou.sdev.altong.service.BatchMsgSendService;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 데이타베이스에 격납된 메시지를 fetch 해서 발송을 담당하는 Job 
 * kr.co.daou.sdev.altong.schedule
 * MsgSendControllJob.java
 * </pre>
 *
 *	@author  : yoonsm
 *  @Date    :2017. 12. 19.
 *  @Version : 0.1
 */
@Slf4j
@Component
public class MsgSendControllJob {
	
	@Autowired
	BatchMsgSendService msgSendService;
	
	@Scheduled(fixedDelayString = "${scheduled.connection}", initialDelay = 100)
	public void manageConnect() {
		log.debug("- manageConnect -");
		msgSendService.manageSendProcess();
	
	}
	
	@Scheduled(fixedDelayString = "${scheduled.first}", initialDelay = 1000)
	public void fetchFirstMessage() {
		log.debug("- fetchFirstMessage -");
		msgSendService.fetchFirstMessage();
	}
	
	@Scheduled(fixedDelayString = "${scheduled.second}", initialDelay = 1000)
	public void fetchSecondtMessage() {
		log.debug("- fetchSecondtMessage -");
		msgSendService.fetchSecondMessage();
	}
	
}
