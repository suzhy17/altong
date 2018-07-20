package kr.co.daou.sdev.altong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.daou.sdev.altong.domain.project.DaouService;

public interface DaouServiceRepository extends JpaRepository<DaouService, String> {
	List<DaouService> findByServiceIdIn(List<String> serviceIds);
}
