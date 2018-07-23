package kr.co.daou.sdev.altong.domain;

import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.enumeration.SmartPhoneType;
import kr.co.daou.sdev.altong.dto.project.DaouServiceDto;
import kr.co.daou.sdev.altong.dto.project.MemberDto;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;
import kr.co.daou.sdev.altong.dto.project.SmartPhoneDto;

import java.util.ArrayList;
import java.util.List;

public class ProjectDtoMock {
	
	public static ProjectDto getMockProjectDtos() {
		DaouServiceDto daouServiceDto = new DaouServiceDto();
		daouServiceDto.setServiceId("EMS31232345r");
		daouServiceDto.setServiceName("포토에디터23423432");
		
		ProjectDto projectDto = new ProjectDto();
		projectDto.setDaouService(daouServiceDto);
		projectDto.setProjectId("PHOTO_API1243234");
		projectDto.setProjectName("포토에디터 API3132343");
		projectDto.setSendType(SendType.PUSH);
		projectDto.setResendType(SendType.EMAIL);
		projectDto.setProjectStatus(Boolean.TRUE);
		projectDto.setRegUserId("suzhy");

		return projectDto;
	}

	public static List<MemberDto> getMockMemberDtos() {
		SmartPhoneDto smartPhoneDto1 = new SmartPhoneDto();
		smartPhoneDto1.setSmartPhoneType(SmartPhoneType.ANDROID);
		smartPhoneDto1.setPushNo("654f56sa1fswer23fs");

		MemberDto memberDto1 = new MemberDto();
		memberDto1.setStaffNo(1001);
		memberDto1.setMemberName("한영수");
		memberDto1.setEmail("sssss@daou.co.kr");
		memberDto1.setSmartPhone(smartPhoneDto1);
		memberDto1.setMobileNo("01099996666");
		memberDto1.setRemarks( "메모다");

		SmartPhoneDto smartPhoneDto2 = new SmartPhoneDto();
		smartPhoneDto2.setSmartPhoneType(SmartPhoneType.IPHONE);
		smartPhoneDto2.setPushNo("gcmkey111111111");

		MemberDto memberDto2 = new MemberDto();
		memberDto2.setStaffNo(1002);
		memberDto2.setMemberName("지민근");
		memberDto2.setEmail("aaaaaaa@daou.co.kr");
		memberDto2.setSmartPhone(smartPhoneDto2);
		memberDto2.setMobileNo("01099977776");
		memberDto2.setRemarks( "메모다222222");

		List<MemberDto> memberDtos = new ArrayList<>();
		memberDtos.add(memberDto1);
		memberDtos.add(memberDto2);

		return memberDtos;
	}
}
