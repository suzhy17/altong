package kr.co.daou.sdev.altong.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;

public interface ProjectService {

	Page<Project> getProjects(String serviceId, Pageable pageable);

	Page<Project> getProjectsByServiceIn(List<DaouService> services, Pageable pageable);

	Project getProject(Integer projectNo);

	Project getProjectByProjectId(String projectId);

	void saveProjectAll(ProjectDto project);

	void saveProject(ProjectDto projectDto, String regUserId);

	void modifyProject(ProjectDto projectDto);
	
	void modifyProjectStatus(Integer projectNo, Boolean projectStatus);

	void registerProjectMember(Integer projectNo, Integer memberNo);

	void removeProjectMember(Integer projectNo, Integer memberNo);
}
