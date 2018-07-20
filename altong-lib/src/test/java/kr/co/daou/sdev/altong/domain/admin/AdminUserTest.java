package kr.co.daou.sdev.altong.domain.admin;

import org.junit.Test;

import java.time.LocalDateTime;

public class AdminUserTest {
	
	@Test
	public void test() throws Exception {
		LocalDateTime lastLoginDate = LocalDateTime.of(2017, 11, 1, 0, 0 , 0);

		System.out.println(lastLoginDate.isAfter(LocalDateTime.now().minusMonths(3)));
	}
}
