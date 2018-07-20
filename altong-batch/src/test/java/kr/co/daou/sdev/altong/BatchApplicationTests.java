package kr.co.daou.sdev.altong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchApplication.class)
@ActiveProfiles("test")
public class BatchApplicationTests {

	@Test
	public void contextLoads() {
	}

}
