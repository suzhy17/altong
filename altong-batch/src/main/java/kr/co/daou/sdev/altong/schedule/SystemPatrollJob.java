package kr.co.daou.sdev.altong.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.daou.sdev.altong.service.BatchMsgSendService;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 통합 알림 시스템이 정상적으로 운영되고 있는지 체크하는 Job Component
 * kr.co.daou.sdev.altong.schedule
 * SystemPatrollJob.java
 * </pre>
 *
 *	@author  : yoonsm
 *  @Date    :2017. 12. 19.
 *  @Version : 0.1
 */
@Slf4j
@Component
public class SystemPatrollJob {

	@Autowired
	BatchMsgSendService msgSendService;
	
	@Scheduled(fixedRate=10000,initialDelay=1000)
	public void checkMsg() {
		log.debug("- check message -");
	}
}
