package kr.co.daou.sdev.altong.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.domain.project.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	Page<Project> findAllByDaouService(DaouService daouService, Pageable pageable);

	Page<Project> findAllByDaouServiceIn(List<DaouService> daouService, Pageable pageable);

	Project findByProjectId(String projectId);

	Integer countByDaouService(DaouService daouService);
}
