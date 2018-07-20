package kr.co.daou.sdev.altong.service;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.dto.project.DaouServiceDto;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.mapper.CommonModelMapper;
import kr.co.daou.sdev.altong.repository.DaouServiceRepository;
import kr.co.daou.sdev.altong.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service("daouServiceService")
public class DaouServiceServiceImpl implements DaouServiceService {

	@Autowired
	private CommonModelMapper modelMapper;

	@Autowired
	private DaouServiceRepository daouServiceRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	@Transactional(readOnly = true)
	public Set<DaouService> getAllDaouServices() {
		Set<DaouService> allDaouServices = new HashSet<>(daouServiceRepository.findAll());
		return allDaouServices;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DaouService> getMyDaouServices(AdminUser adminUser) {

		Set<DaouService> services;
		// 마스터 운영자
		if (adminUser.isAdmin()) {
			services = new HashSet<>(daouServiceRepository.findAll());
		}
		// 일반 운영자
		else {
			services = adminUser.getServices();
		}
		List<DaouService> myServices = new ArrayList<>(services);
		myServices.sort((s1, s2) -> s1.getServiceId().compareToIgnoreCase(s2.getServiceId()));
		return myServices;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DaouService> getDaouServices(Pageable pageable) {
		return daouServiceRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public DaouService getDaouService(String serviceId) {
		return daouServiceRepository.findOne(serviceId);
	}

	@Override
	@Transactional
	public void saveDaouService(DaouServiceDto daouServiceDto) {
		DaouService daouService = modelMapper.map(daouServiceDto, DaouService.class);
		daouServiceRepository.save(daouService);
	}

	@Override
	@Transactional
	public void updateDaouService(DaouServiceDto daouServiceDto) {
		DaouService daouService = daouServiceRepository.getOne(daouServiceDto.getServiceId());

		daouService.changeServiceName(daouServiceDto.getServiceName());
	}

	@Override
	@Transactional
	public void updateDaouServiceStatus(String serviceId, Boolean serviceStatus) {

		DaouService daouService = daouServiceRepository.getOne(serviceId);

		daouService.changeServiceStatus(serviceStatus);
	}

	@Override
	@Transactional
	public void deleteDaouService(String serviceId) {
		// 서비스에 포함된 활성화된 프로젝트가 있는지 체크 후 삭제
		DaouService daouService = daouServiceRepository.getOne(serviceId);
		Integer projectCount = projectRepository.countByDaouService(daouService);
		if (projectCount > 0) {
			throw new AltongException.ProjectExistException();
		}

		daouServiceRepository.delete(daouService);
	}
}
