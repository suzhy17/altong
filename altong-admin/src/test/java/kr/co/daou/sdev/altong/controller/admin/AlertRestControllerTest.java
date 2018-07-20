package kr.co.daou.sdev.altong.controller.admin;

import kr.co.daou.sdev.altong.WebApplication;
import kr.co.daou.sdev.altong.domain.ProjectDtoMock;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;
import kr.co.daou.sdev.altong.repository.ProjectRepository;
import kr.co.daou.sdev.altong.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AlertRestControllerTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();

		ProjectDto projectDto = ProjectDtoMock.getMockProjectDtos();
		log.info("projectDto.getDaouService()={}", projectDto.getDaouService());	
		
		projectService.saveProjectAll(projectDto);
	}

	@Test
	public void test_sendAlert() throws Exception {

		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("projectId", "PHOTO_API");
		params.add("message" , "테스트 메시지 전송합니다.");
		params.add("sendType", "DAOU_OFFICE");

		// when
		ResultActions resultActions = mockMvc.perform(post("/alerts/send").params(params).with(user("master").roles("USER", "ADMIN")));

		// then
		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
		;
	}

}
