package kr.co.daou.sdev.altong.mock.domain;

import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.enumeration.SmartPhoneType;
import kr.co.daou.sdev.altong.dto.project.DaouServiceDto;
import kr.co.daou.sdev.altong.dto.project.MemberDto;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;
import kr.co.daou.sdev.altong.dto.project.SmartPhoneDto;

import java.util.HashSet;
import java.util.Set;

public class ProjectDtoMock {

	public static ProjectDto getMockProjectDtos() {
		DaouServiceDto daouServiceDto = new DaouServiceDto();
		daouServiceDto.setServiceId("EMS31232345r2gfds32");
		daouServiceDto.setServiceName("포토에디터234234323232");

		ProjectDto projectDto = new ProjectDto();
		projectDto.setDaouService(daouServiceDto);
		projectDto.setProjectId("PHOTO_API12432344555dfgf");
		projectDto.setProjectName("포토에디터 API313234366565");
		projectDto.setSendType(SendType.PUSH);
		projectDto.setResendType(SendType.EMAIL);
		projectDto.setProjectStatus(Boolean.TRUE);
		projectDto.setRegUserId("suzhy");

		return projectDto;
	}

	public static Set<MemberDto> getMockMemberDtos() {
		SmartPhoneDto smartPhoneDto1 = new SmartPhoneDto();
		smartPhoneDto1.setSmartPhoneType(SmartPhoneType.ANDROID);
		smartPhoneDto1.setPushNo("654f56sa1fswer233423fs");

		MemberDto memberDto1 = new MemberDto();
		memberDto1.setStaffNo(3075);
		memberDto1.setMemberName("한영수2222");
		memberDto1.setEmail("sssss2222@daou.co.kr");
		memberDto1.setSmartPhone(smartPhoneDto1);
		memberDto1.setMobileNo("01099996646");
		memberDto1.setRemarks( "메모다");

		SmartPhoneDto smartPhoneDto2 = new SmartPhoneDto();
		smartPhoneDto2.setSmartPhoneType(SmartPhoneType.IPHONE);
		smartPhoneDto2.setPushNo("gcmkey1143111111");

		MemberDto memberDto2 = new MemberDto();
		memberDto2.setStaffNo(3074);
		memberDto2.setMemberName("지민근22");
		memberDto2.setEmail("aaaaaa222a@daou.co.kr");
		memberDto2.setSmartPhone(smartPhoneDto2);
		memberDto2.setMobileNo("01099974376");
		memberDto2.setRemarks( "메모다24342");

		Set<MemberDto> memberDtos = new HashSet<>();
		memberDtos.add(memberDto1);
		memberDtos.add(memberDto2);

		return memberDtos;
	}
}
