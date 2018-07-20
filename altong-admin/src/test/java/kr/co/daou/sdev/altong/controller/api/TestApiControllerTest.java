package kr.co.daou.sdev.altong.controller.api;

import kr.co.daou.sdev.altong.WebApplication;
import kr.co.daou.sdev.altong.service.ProjectService;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestApiControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;

	@Autowired
	private ProjectService projectService;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	/**
	 * 일반적인 JSON 결과 확인 테스트
	 * @throws Exception Exception
	 */
	@Test
	public void test_test() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/test"));

		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.handler().handlerType(TestApiController.class))
				.andExpect(MockMvcResultMatchers.handler().methodName("test"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		;
	}

	/**
	 * 500에러 테스트
	 * <ol>
	 *   <li>RuntimeException 발생시 resCd=ERROR
	 *   <li>응답코드는 500
	 * </ol>
	 * @throws Exception Exception
	 */
	@Test
	public void test_test500Error() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/test500Error"));

		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.handler().handlerType(TestApiController.class))
				.andExpect(MockMvcResultMatchers.handler().methodName("test500Error"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		;
	}

	/**
	 * 실패 테스트 (정상시)
	 * @throws Exception Exception
	 */
	@Test
	public void test_fail_success() throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("isSuccess", "true");

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/fail").params(params));

		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.handler().handlerType(TestApiController.class))
				.andExpect(MockMvcResultMatchers.handler().methodName("testFail"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		;
	}

	/**
	 * 실패 테스트 (실패시)
	 * @throws Exception Exception
	 */
	@Test
	public void test_fail_fail() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/fail"));

		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.handler().handlerType(TestApiController.class))
				.andExpect(MockMvcResultMatchers.handler().methodName("testFail"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.resCd", Matchers.is("FAIL")))
		;
	}
}
