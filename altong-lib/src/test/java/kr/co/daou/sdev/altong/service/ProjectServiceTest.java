package kr.co.daou.sdev.altong.service;

import kr.co.daou.sdev.altong.LibApplication;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.dto.project.MemberDto;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;
import kr.co.daou.sdev.altong.mapper.CommonModelMapper;
import kr.co.daou.sdev.altong.mock.domain.ProjectDtoMock;
import kr.co.daou.sdev.altong.repository.MemberRepository;
import kr.co.daou.sdev.altong.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@Slf4j
@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibApplication.class)
@ActiveProfiles("test")
public class ProjectServiceTest {

	@Autowired
	private CommonModelMapper modelMapper;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private MemberRepository memberRepository;


	@Autowired
	private ProjectService projectService;

	@Autowired
	private MemberService memberService;

	@Before
	public void setup() {
		ProjectDto projectDto = ProjectDtoMock.getMockProjectDtos();

		projectService.saveProjectAll(projectDto);

		Set<MemberDto> memberDtos = ProjectDtoMock.getMockMemberDtos();

		for (MemberDto memberDto : memberDtos) {
			memberService.saveMember(memberDto);
		}
	}

	@Test
	public void test_findAll() {
		log.info("##################################################");

		// given

		// when
		Page<Project> projects = projectService.getProjects(null, new PageRequest(0, 100));
		log.info("projects.getTotalElements()={}", projects.getTotalElements());
		projects.forEach(System.out::println);
		
		// then
		assertEquals(projects.getTotalElements(), 1);
	}
	
	@After
	public void destroy() {
		projectRepository.deleteAll();
		memberRepository.deleteAll();
	}

}
