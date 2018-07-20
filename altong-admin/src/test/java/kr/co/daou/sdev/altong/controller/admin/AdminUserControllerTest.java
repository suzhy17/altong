package kr.co.daou.sdev.altong.controller.admin;

import kr.co.daou.sdev.altong.WebApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AdminUserControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

	/**
	 * 
	 *
	 * @throws Exception Exception
	 */
	@Test
	@Transactional
	public void test_registerAdmin() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("userId", "master");
		params.add("mobileNo" , "01012345678");
		params.add("name" , "마스터");
		params.add("email" , "master@daou.co.kr");

		
		// when
		ResultActions resultActions = mockMvc.perform(post("/admins").params(params).with(user("user").roles("USER", "ADMIN")));

		// then
		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.handler().handlerType(AdminUserRestController.class))
				.andExpect(MockMvcResultMatchers.handler().methodName("registerAdmin"))
				.andExpect(MockMvcResultMatchers.status().isOk())
		;

		resultActions = mockMvc.perform(put("/admins/master/temp-password").with(user("user").roles("USER", "ADMIN")));
	}

}
