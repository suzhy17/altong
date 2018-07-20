package kr.co.daou.sdev.altong.controller.admin;

import javax.inject.Provider;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;
import kr.co.daou.sdev.altong.service.ProjectService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProjectRestController {

	@Autowired
	private Provider<AdminUser> adminUserProvider;

	@Autowired
	private ProjectService projectService;

	/**
	 * 프로젝트 등록 처리
	 * @param projectDto projectDto
	 */
	@Secured("ROLE_USER")
	@PostMapping(value = "/projects")
	public void registerProject(@Valid @ModelAttribute ProjectDto projectDto) {

		AdminUser adminUser = adminUserProvider.get();

		projectService.saveProject(projectDto, adminUser.getUserId());
	}

	/**
	 * 프로젝트 정보 변경 처리
	 *
	 * @param projectDto projectDto
	 */
	@Secured("ROLE_USER")
	@PutMapping(value = "/projects/{projectNo}")
	public void modifyProject(@Valid @ModelAttribute ProjectDto projectDto) {

		projectService.modifyProject(projectDto);
	}

	/**
	 * 프로젝트 상태 변경 처리
	 * @param projectNo 프로젝트 ID
	 * @param projectStatus 프로젝트 상태
	 */
	@Secured("ROLE_USER")
	@PutMapping(value = "/projects/{projectNo}/status")
	public void modifyProjectStatus(
			@PathVariable Integer projectNo,
			@RequestParam Boolean projectStatus) {

		projectService.modifyProjectStatus(projectNo, projectStatus);
	}

	/**
	 * 프로젝트 삭제
	 *
	 * @param projectNo 멤버 번호
	 */
	@Secured("ROLE_USER")
	@DeleteMapping(value = "/projects/{projectNo}")
	public void removeProject(
			@PathVariable Integer projectNo) {

		//projectService.removeProject(projectNo);
	}

	/**
	 * 프로젝트-멤버 매핑 처리
	 *
	 * @param projectNo 프로젝트 번호
	 * @param memberNo 멤버 번호
	 */
	@Secured("ROLE_USER")
	@PostMapping(value = "/projects/{projectNo}/members/{memberNo}")
	public void registerProjectMember(
			@PathVariable Integer projectNo,
			@PathVariable Integer memberNo) {

		projectService.registerProjectMember(projectNo, memberNo);
	}

	/**
	 * 프로젝트-멤버 매핑 해제
	 *
	 * @param projectNo 프로젝트 번호
	 * @param memberNo 멤버 번호
	 */
	@Secured("ROLE_USER")
	@DeleteMapping(value = "/projects/{projectNo}/members/{memberNo}")
	public void removeProjectMember(
			@PathVariable Integer projectNo,
			@PathVariable Integer memberNo) {

		projectService.removeProjectMember(projectNo, memberNo);
	}

}