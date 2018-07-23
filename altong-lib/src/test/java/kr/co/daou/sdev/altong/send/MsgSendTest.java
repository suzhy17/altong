package kr.co.daou.sdev.altong.send;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.daou.sdev.altong.LibApplication;
import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;
import kr.co.daou.sdev.altong.service.MsgSendService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibApplication.class)
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:config-lib-test.properties")
public class MsgSendTest {

	@Autowired
	MsgSendService msgSendService;
	
	@Before
	public void init() {
	}
	
	@Test
	public void test_sendDaouOfficeMsg() {
		log.debug("Daou Office Test");
		UnityMsgContents contents = new UnityMsgContents();
		contents.setBody("이건 테스트 메시지입니다.");
		contents.setLevel("INFO");
		contents.setProjectName("비즈뿌리오");
		contents.addTarget("3068");
		//contents.addTarget("3075");
		//contents.addTarget("3376");
		UnityMsg unityMsg = msgSendService.createUnityMsg(SendType.DAOU_OFFICE);
		unityMsg.send(contents);

	}

	@Test
	public void test_sendSmsMsg() {
		
		log.debug("SMS Test");
		UnityMsgContents contents = new UnityMsgContents();
		contents.setBody("이건 테스트 메시지입니다.");
		contents.setLevel("INFO");
		contents.setProjectName("비즈뿌리오");
		contents.addTarget("01072342293");
		contents.addTarget("01092896777");
		
		UnityMsg unityMsg = msgSendService.createUnityMsg(SendType.SMS);
		unityMsg.send(contents);
	}

	
	@Test
	public void test_sendEmailMsg() {
		
		log.debug("Email Test");
		UnityMsgContents contents = new UnityMsgContents();
		contents.setBody("<strong>이건 테스트 메시지입니다.</strong>");
		contents.setLevel("INFO");
		contents.setProjectName("비즈뿌리오");
		contents.addTarget("yoonsm@daou.co.kr");
		
		UnityMsg unityMsg = msgSendService.createUnityMsg(SendType.EMAIL);
		unityMsg.send(contents);
	}

	
}
