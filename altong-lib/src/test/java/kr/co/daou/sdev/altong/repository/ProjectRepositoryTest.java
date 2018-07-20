package kr.co.daou.sdev.altong.repository;

import kr.co.daou.sdev.altong.LibApplication;
import kr.co.daou.sdev.altong.dto.project.MemberDto;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;
import kr.co.daou.sdev.altong.mock.domain.ProjectDtoMock;
import kr.co.daou.sdev.altong.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@Slf4j
@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibApplication.class)
@ActiveProfiles("test")
public class ProjectRepositoryTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProjectService projectService;

	@Before
	public void setup() {
		ProjectDto projectDto = ProjectDtoMock.getMockProjectDtos();

		Set<MemberDto> memberDtos = ProjectDtoMock.getMockMemberDtos();

		projectDto.setMemberDtos(memberDtos);

		projectService.saveProjectAll(projectDto);
	}


	@Test
	public void test_findAll() {
		// given

		// when
//		Set<Project> projects = projectRepository.findAll();
//		log.info("== projects.size()={}", projects.size());
//		for (Project project : projects) {
//			log.info("project={}, {}, {}", project.getProjectNo(), project.getProjectName(), project.getProjectStatus());
//			Set<Member> members = project.getMembers();
//			log.info("  members.size()={}", members.size());
//			for (Member member : members) {
//				log.info("  member={}, {}, {}, {}", member.getMemberNo(), member.getStaffNo(), member.getMemberName(), member.getMemberStatus());
//			}
//		}

		// then
		//assertEquals(projects.size(), 1);
	}

	@After
	public void destroy() {
		projectRepository.deleteAll();
		memberRepository.deleteAll();
	}

}
