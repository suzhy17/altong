package kr.co.daou.sdev.altong.controller.admin;

import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.service.DaouServiceService;
import kr.co.daou.sdev.altong.service.MemberService;
import kr.co.daou.sdev.altong.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AlertController {

	@Autowired
	private DaouServiceService daouServiceService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MemberService memberService;

	/**
	 * 알림 내역 조회
	 *
	 * @return view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/alerts")
	public String getAlerts(Model model,
							  @PageableDefault(sort = {"projectNo"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

		model.addAttribute("pageResult", null);

		return "alert/alerts";
	}

	/**
	 * 프로젝트별 알림 테스트 발송 모달
	 *
	 * @return modal view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/alerts/send")
	public String projectAlertForm(
			@RequestParam Integer projectNo,
			Model model) {

		Project project = projectService.getProject(projectNo);

		model.addAttribute("project", project);

		return "alert/project-alert-form-modal";
	}

	/**
	 * 멤버별 알림 테스트 발송 모달
	 *
	 * @return modal view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/alerts/send/{memberNo}")
	public String memberAlertForm(
			@PathVariable Integer memberNo,
			Model model) {

		Member member = memberService.getMember(memberNo);

		model.addAttribute("member", member);

		return "alert/member-alert-form-modal";
	}

}