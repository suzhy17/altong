package kr.co.daou.sdev.altong.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import kr.co.daou.sdev.altong.WebApplication;
import kr.co.daou.sdev.altong.domain.ProjectDtoMock;
import kr.co.daou.sdev.altong.dto.project.MemberDto;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MemberRestControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

//	@Test
	public void test_registerMember() throws Exception {

		// given
		List<MemberDto> members = ProjectDtoMock.getMockMemberDtos();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(members.get(1));
		System.out.println("requestJson=" + requestJson);

		// when
		ResultActions resultActions = mockMvc.perform(post("/members")
				.contentType(APPLICATION_JSON_UTF8).content(requestJson).with(user("user").roles("USER", "ADMIN")));

		// then
		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
		;

		resultActions = mockMvc.perform(get("/members/1?modify=1").with(user("user").roles("USER", "ADMIN")));

		// then
		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
		;
	}

	@Test
	public void test_registerMember2() throws Exception {

		// given
		MemberDto memberDto = ProjectDtoMock.getMockMemberDtos().get(0);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("memberName", memberDto.getMemberName());
		params.add("staffNo" , String.valueOf(memberDto.getStaffNo()));
		params.add("mobileNo", memberDto.getMobileNo());
		params.add("email", memberDto.getEmail());
		params.add("smartPhone.smartPhoneType", memberDto.getSmartPhone().getSmartPhoneType().name());
		params.add("smartPhone.pushNo", memberDto.getSmartPhone().getPushNo());
		params.add("remarks", memberDto.getRemarks());

		// when
		ResultActions resultActions = mockMvc.perform(post("/members")
				.contentType(APPLICATION_JSON_UTF8).params(params).with(user("user").roles("USER", "ADMIN")));

		// then
		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
		;

		resultActions = mockMvc.perform(get("/members/1?modify=1").with(user("user").roles("USER", "ADMIN")));

		// then
		resultActions
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
		;
	}
}
