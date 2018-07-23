package kr.co.daou.sdev.altong.mapper;

import kr.co.daou.sdev.altong.LibApplication;
import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.enumeration.SmartPhoneType;
import kr.co.daou.sdev.altong.dto.project.MemberDto;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;
import kr.co.daou.sdev.altong.dto.project.SmartPhoneDto;
import kr.co.daou.sdev.altong.mock.domain.ProjectDtoMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@TestPropertySource(locations="classpath:config-lib-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibApplication.class)
@ActiveProfiles("test")
public class CommonMapperTest {

	@Autowired
	CommonModelMapper modelMapper;

	// 단순한 DTO -> Domain 변환
	@Test
	public void test_map_simple() throws Exception {

		// given
		MemberDto memberDto = new MemberDto();
		memberDto.setEmail("aaa@aaa.com");
		memberDto.setMemberName("한영수");
		memberDto.setMemberStatus(Boolean.TRUE);
		memberDto.setMobileNo("01092089887");

		// when
		Member member = modelMapper.map(memberDto, Member.class);

		log.info("member={}", member);

		// then
		Assert.assertEquals(memberDto.getEmail(), member.getEmail());
		Assert.assertEquals(memberDto.getMemberName(), member.getMemberName());
		Assert.assertEquals(memberDto.getMemberStatus(), member.getMemberStatus());
	}

	// 하위 객체가 포함된 DTO -> Domain 변환
	@Test
	public void test_map_inObject() throws Exception {

		// given
		MemberDto memberDto = new MemberDto();
		memberDto.setEmail("aaa@aaa.com");
		memberDto.setMemberName("한영수");
		memberDto.setMemberStatus(Boolean.TRUE);
		memberDto.setMobileNo("01092089887");

		SmartPhoneDto smartPhoneDto = new SmartPhoneDto();
		smartPhoneDto.setPushNo("pushpuishsdfafasfsa");
		smartPhoneDto.setSmartPhoneType(SmartPhoneType.IPHONE);

		memberDto.setSmartPhone(smartPhoneDto);

		// when
		Member member = modelMapper.map(memberDto, Member.class);

		log.info("memberDto={}", memberDto);
		log.info("member={}", member);
		log.info("member.getSmartPhone()={}", member.getSmartPhone());

		// then
		Assert.assertEquals(memberDto.getSmartPhone().getPushNo(), member.getSmartPhone().getPushNo());
	}

	// 하위 리스트가 포함된 DTO -> Domain 변환
	@Test
	public void test_map_inList() throws Exception {
		ProjectDto projectDto1 = new ProjectDto();
		projectDto1.setProjectId("EMS_API");
		projectDto1.setProjectName("포토API");

		ProjectDto projectDto2 = new ProjectDto();
		projectDto2.setProjectId("CALLMIX_API");
		projectDto2.setProjectName("콜믹스API");

		Set<ProjectDto> projectDtos = new HashSet<>();
		projectDtos.add(projectDto1);
		projectDtos.add(projectDto2);

		// given
		MemberDto memberDto = new MemberDto();
		memberDto.setEmail("aaa@aaa.com");
		memberDto.setMemberName("한영수");
		memberDto.setMemberStatus(Boolean.TRUE);
		memberDto.setMobileNo("01092089887");

		memberDto.setProjects(projectDtos);

		// when
		Member member = modelMapper.map(memberDto, Member.class);

		log.info("memberDto={}", memberDto);
		log.info("member={}", member);
		log.info("member.getProjects()={}", member.getProjects());

		// then
		for (ProjectDto p : memberDto.getProjects()) {
			Assert.assertEquals(p.getProjectId(), p.getProjectId());
		}
	}

	// 하위 리스트가 포함된 Domain -> DTO 변환
	@Test
	public void test_map_toDomain_inList() throws Exception {
		Set<MemberDto> memberDtos = ProjectDtoMock.getMockMemberDtos();

		memberDtos.forEach(m -> {
			// when
			Member member = modelMapper.map(m, Member.class);

			log.info("memberDto={}", m);
			log.info("member={}", member);

			// then
			Assert.assertEquals(m.getMemberName(), member.getMemberName());
		});
	}

}
