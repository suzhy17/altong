package kr.co.daou.sdev.altong.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.daou.sdev.altong.dto.project.MemberDto;
import kr.co.daou.sdev.altong.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	/**
	 * 멤버 리스트 조회
	 *
	 * @return view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/members")
	public String getMembers(
			Model model,
			@PageableDefault(sort = {"staffNo"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable) {

		Page<MemberDto> pageResult = memberService.getMembers(pageable);

		model.addAttribute("pageResult", pageResult);

		return "member/members";
	}

	/**
	 * 멤버 정보 변경 폼
	 *
	 * @return view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/members/{memberNo}", params = "modify")
	public String memberModifyForm(Model model, @PathVariable Integer memberNo) {

		model.addAttribute("member", memberService.getMember(memberNo));

		return "member/member-form-modal";
	}

	/**
	 * 멤버 등록 폼
	 * @param model model
	 * @return
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/members", params = "write")
	public String memberWriteForm(Model model) {
		return "member/member-form-modal";
	}

	/**
	 * 프로젝트-멤버 매핑 모달
	 *
	 * @return view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/members", params = "modal")
	public String membersModal(Model model, Integer projectNo) {

		model.addAttribute("projectNo", projectNo);
		model.addAttribute("members", memberService.getAllMembers());

		return "member/members-modal";
	}

}