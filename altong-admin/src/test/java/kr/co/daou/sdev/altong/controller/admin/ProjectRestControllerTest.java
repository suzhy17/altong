//package kr.co.daou.sdev.altong.controller.admin;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import kr.co.daou.sdev.altong.WebApplication;
//import kr.co.daou.sdev.altong.domain.ProjectDtoMock;
//import kr.co.daou.sdev.altong.domain.project.Project;
//import kr.co.daou.sdev.altong.dto.project.ProjectDto;
//import kr.co.daou.sdev.altong.repository.ProjectRepository;
//import kr.co.daou.sdev.altong.service.ProjectService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@Slf4j
//@TestPropertySource(locations="classpath:config-lib-test.properties")
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = WebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//public class ProjectRestControllerTest {
//
//	@Autowired
//	private ProjectRepository projectRepository;
//
//	@Autowired
//	private ProjectService projectService;
//
//	@Autowired
//	private WebApplicationContext wac;
//
//	private MockMvc mockMvc;
//
//	@Before
//	public void setUp() throws Exception {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
//
//		ProjectDto projectDto = ProjectDtoMock.getMockProjectDtos();
//		log.info("projectDto.getDaouService()={}", projectDto.getDaouService());
//
//		projectService.saveProjectAll(projectDto);
//	}
//
//	/**
//	 * 일반적인 정상 응답(200) 확인 테스트
//	 *
//	 * @throws Exception Exception
//	 */
//	@Test
//	public void test_getProjects() throws Exception {
//
//		// when
//		ResultActions resultActions = mockMvc.perform(get("/projects").with(user("user").roles("USER", "ADMIN")));
//
//		// then
//		resultActions
//				.andDo(MockMvcResultHandlers.print())
//				.andExpect(MockMvcResultMatchers.handler().handlerType(ProjectController.class))
//				.andExpect(MockMvcResultMatchers.handler().methodName("getProjects"))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//		;
//	}
//
//	//@Test
//	public void test_registerProject() throws Exception {
//
//		// given
//		Project project = projectRepository.findOne(1);
//
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//		String requestJson = ow.writeValueAsString(project);
//		System.out.println("requestJson=" + requestJson);
//
//		// when
//		ResultActions resultActions = mockMvc.perform(post("/projects")
//				.contentType(APPLICATION_JSON_UTF8).content(requestJson));
//
//		// then
//		resultActions
//				.andDo(MockMvcResultHandlers.print())
//				.andExpect(MockMvcResultMatchers.status().isOk())
//		;
//	}
//}
