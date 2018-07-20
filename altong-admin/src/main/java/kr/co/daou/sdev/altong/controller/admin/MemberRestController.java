package kr.co.daou.sdev.altong.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.daou.sdev.altong.dto.project.MemberDto;
import kr.co.daou.sdev.altong.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberRestController {

	@Autowired
	private MemberService memberService;

	/**
	 * 멤버 등록 처리
	 *
	 * @param memberDto memberDto
	 */
	@Secured("ROLE_USER")
	@PostMapping(value = "/members")
	public void registerMember(@Valid @ModelAttribute MemberDto memberDto) {
		log.debug("member={}", memberDto);
		log.debug("member.getSmartPhone()={}", memberDto.getSmartPhone());

		memberService.saveMember(memberDto);
	}

	/**
	 * 멤버 정보 변경 처리
	 *
	 * @param memberDto memberDto
	 */
	@Secured("ROLE_USER")
	@PutMapping(value = "/members", params = "modify")
	public void modifyMember(@Valid @ModelAttribute MemberDto memberDto) {

		log.debug("memberDto.getSmartPhone()={}", memberDto.getSmartPhone());

		memberService.modifyMember(memberDto);
	}


	/**
	 * 멤버 삭제 처리
	 *
	 * @param memberNo 멤버 번호
	 */
	@Secured("ROLE_USER")
	@DeleteMapping(value = "/members/{memberNo}")
	public void removeMember(
			@PathVariable("memberNo") Integer memberNo) {

		memberService.removeMember(memberNo);
	}
}