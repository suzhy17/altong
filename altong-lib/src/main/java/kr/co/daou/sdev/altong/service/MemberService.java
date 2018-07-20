package kr.co.daou.sdev.altong.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.dto.project.MemberDto;

public interface MemberService {

	Page<MemberDto> getMembers(Pageable pageable);

	List<Member> getAllMembers();

	Member getMember(Integer memberNo);
		
	void saveMember(MemberDto memberDto);

	void modifyMember(MemberDto memberDto);

	void removeMember(Integer memberNo);
}
