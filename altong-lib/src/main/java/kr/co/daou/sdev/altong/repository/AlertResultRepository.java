package kr.co.daou.sdev.altong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.daou.sdev.altong.domain.alert.AlertResult;

public interface AlertResultRepository extends JpaRepository<AlertResult, Integer> {
	List<AlertResult> findBySendStatusOrderByAlertMaster(Boolean sendStatus);
	
	//int saveByAlertNoIn(List<AlertMaster> alertMaster);
}
