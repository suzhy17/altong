package kr.co.daou.sdev.altong.service;

import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.dto.project.ProjectDto;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.mapper.CommonModelMapper;
import kr.co.daou.sdev.altong.repository.DaouServiceRepository;
import kr.co.daou.sdev.altong.repository.MemberRepository;
import kr.co.daou.sdev.altong.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private CommonModelMapper modelMapper;

	@Autowired
	private DaouServiceRepository daouServiceRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<Project> getProjects(String serviceId, Pageable pageable) {
		if (StringUtils.isNotBlank(serviceId)) {
			DaouService service = daouServiceRepository.getOne(serviceId);
			return projectRepository.findAllByDaouService(service, pageable);
		} else {
			return projectRepository.findAll(pageable);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Project> getProjectsByServiceIn(List<DaouService> services, Pageable pageable) {
		if (services == null) {
			return null;
		}

		return projectRepository.findAllByDaouServiceIn(services, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Project getProject(Integer projectNo) {
		return projectRepository.findOne(projectNo);
	}

	@Override
	@Transactional
	public Project getProjectByProjectId(String projectId) {
		return projectRepository.findByProjectId(projectId);
	}

	@Override
	@Transactional
	public void saveProjectAll(ProjectDto projectDto) {
		Project project = modelMapper.map(projectDto, Project.class);

		log.info("project.getDaouService()={}", project.getDaouService());

		daouServiceRepository.save(project.getDaouService());

		memberRepository.save(project.getMembers());

		projectRepository.save(project);
	}

	@Override
	@Transactional
	public void saveProject(ProjectDto projectDto, String regUserId) {
		DaouService daouService = daouServiceRepository.getOne(projectDto.getServiceId());
		Project project = new Project(daouService, projectDto.getProjectId(), projectDto.getProjectName(), projectDto.getSendType(), projectDto.getResendType(), projectDto.getSendLimitYn(), regUserId);
		projectRepository.save(project);
	}


	@Override
	@Transactional
	public void modifyProject(ProjectDto projectDto) {
		Project project = projectRepository.getOne(projectDto.getProjectNo());
		project.changeProjectName(projectDto.getProjectName());
		project.changeSendType(projectDto.getSendType());
		project.changeResendType(projectDto.getResendType());
		project.changeSendLimitYn(projectDto.getSendLimitYn());
		project.changeModDatetime();
	}

	@Override
	@Transactional
	public void modifyProjectStatus(Integer projectNo, Boolean projectStatus) {
		Project project = projectRepository.getOne(projectNo);
		log.debug("project={}", project);
		project.changeProjectStatus(projectStatus);
		project.changeModDatetime();
	}

	@Override
	@Transactional
	public void registerProjectMember(Integer projectNo, Integer memberNo) {
		Project project = projectRepository.getOne(projectNo);
		
		if (project == null) {
			throw new AltongException.ProjectNotFoundException();
		}
		
		Member member = memberRepository.getOne(memberNo);

		if (member == null) {
			throw new AltongException.MemberNotFoundException();
		}

		if (project.getMembers().contains(member)) {
			throw new AltongException.ProjectMemberAlreadyExistsException();
		}

		project.addMember(member);
	}

	@Override
	@Transactional
	public void removeProjectMember(Integer projectNo, Integer memberNo) {
		Project project = projectRepository.getOne(projectNo);

		Member member = memberRepository.getOne(memberNo);

		project.getMembers().remove(member);
	}
}
