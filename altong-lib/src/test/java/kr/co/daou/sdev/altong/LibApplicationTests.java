package kr.co.daou.sdev.altong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibApplication.class)
@ActiveProfiles("test")
public class LibApplicationTests {

	@Test
	public void contextLoads() {
	}

}
