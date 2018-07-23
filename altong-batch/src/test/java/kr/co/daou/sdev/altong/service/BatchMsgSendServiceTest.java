package kr.co.daou.sdev.altong.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.daou.sdev.altong.LibApplication;
import kr.co.daou.sdev.altong.enumeration.AlertStatusType;
import kr.co.daou.sdev.altong.dto.alert.AlertMasterDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibApplication.class)
@ActiveProfiles("h2")
public class BatchMsgSendServiceTest {

	@Autowired
	private AlertMasterService alertMasterService;

	@Autowired
	BatchMsgSendService batchMsgSendService;
	
	@Before
	public void setup() {
		
		AlertMasterDto alertMasterDto = new AlertMasterDto();
		alertMasterDto.setAlertStatus(AlertStatusType.READY);
		alertMasterDto.setMessage("테스트 메시지");
		alertMasterDto.setProjectNo(2);
		alertMasterService.saveMaster(alertMasterDto);
	}
	
	@Test
	public void firstMsgFetchTest() {
		batchMsgSendService.fetchFirstMessage();
	}
	
}
