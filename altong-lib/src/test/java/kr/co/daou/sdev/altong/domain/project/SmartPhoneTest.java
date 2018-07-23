package kr.co.daou.sdev.altong.domain.project;

import kr.co.daou.sdev.altong.enumeration.SmartPhoneType;
import org.junit.Test;

public class SmartPhoneTest {
	
	@Test
	public void test() throws Exception {
		SmartPhone smartPhone = new SmartPhone(SmartPhoneType.ANDROID, "dfd");
//		smartPhone.setPushNo("dff");
//		smartPhone.getPushNo();
	}
}
