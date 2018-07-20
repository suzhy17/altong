package kr.co.daou.sdev.altong.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.dto.project.DaouServiceDto;

public interface DaouServiceService {

	Set<DaouService> getAllDaouServices();

	List<DaouService> getMyDaouServices(AdminUser adminUser);

	Page<DaouService> getDaouServices(Pageable pageable);

	DaouService getDaouService(String serviceId);

	void saveDaouService(DaouServiceDto daouServiceDto);

	void updateDaouService(DaouServiceDto daouServiceDto);

	void updateDaouServiceStatus(String serviceId, Boolean serviceStatus);

	void deleteDaouService(String serviceId);
}
