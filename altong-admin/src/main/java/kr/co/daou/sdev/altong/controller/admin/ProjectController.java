package kr.co.daou.sdev.altong.controller.admin;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.service.DaouServiceService;
import kr.co.daou.sdev.altong.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Provider;
import java.util.List;

@Slf4j
@Controller
public class ProjectController {
	
	@Autowired
	private Provider<AdminUser> adminUserProvider;

	@Autowired
	private DaouServiceService daouServiceService;

	@Autowired
	private ProjectService projectService;

	/**
	 * 프로젝트 리스트 조회
	 *
	 * @return view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/projects")
	public String getProjects(
			Model model,
			@RequestParam(required = false) String serviceId,
			@PageableDefault(sort = {"projectNo"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable) {

		AdminUser adminUser = adminUserProvider.get();

		List<DaouService> services = daouServiceService.getMyDaouServices(adminUser);
		Page<Project> pageResult = null;

		// 마스터 운영자
		if (adminUser.isAdmin()) {
			pageResult = projectService.getProjects(serviceId, pageable);
		}
		// 일반 운영자
		else {
			if (StringUtils.isBlank(serviceId)) {
				pageResult = projectService.getProjectsByServiceIn(services, pageable);
			} else {
				if (this.isMyService(serviceId, services)) {
					pageResult = projectService.getProjects(serviceId, pageable);
				}
			}
		}

		log.debug("pageResult.getContent()={}", pageResult.getContent());
		log.debug("pageResult.getTotalPages()={}", pageResult.getTotalPages());


		model.addAttribute("selectedServiceId", serviceId);
		model.addAttribute("services", services);
		model.addAttribute("pageResult", pageResult);

		return "project/projects";
	}
	
	private boolean isMyService(String serviceId, List<DaouService> services) {
		for (DaouService myService : services) {
			if (StringUtils.equals(myService.getServiceId(), serviceId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 프로젝트 등록 폼
	 * @param model model
	 * @return
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/projects", params = "write")
	public String projectWriteForm(Model model) {
		AdminUser adminUser = adminUserProvider.get();

		List<DaouService> myServices = daouServiceService.getMyDaouServices(adminUser);

		model.addAttribute("services", myServices);

		return "project/project-form-modal";
	}

	/**
	 * 프로젝트 변경 폼
	 * @param model model
	 * @return
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/projects/{projectNo}", params = "modify")
	public String projectModifyForm(Model model, @PathVariable("projectNo") Integer projectNo) {
		AdminUser adminUser = adminUserProvider.get();

		List<DaouService> services = daouServiceService.getMyDaouServices(adminUser);

		model.addAttribute("services", services);
		model.addAttribute("sendTypes", SendType.values());
		model.addAttribute("project", projectService.getProject(projectNo));
		return "project/project-form-modal";
	}

	/**
	 * 프로젝트 상세
	 * @param model model
	 * @return
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/projects/{projectNo}")
	public String projectDetail(Model model, @PathVariable("projectNo") Integer projectNo) {
		model.addAttribute("project", projectService.getProject(projectNo));
		return "project/project";
	}

}