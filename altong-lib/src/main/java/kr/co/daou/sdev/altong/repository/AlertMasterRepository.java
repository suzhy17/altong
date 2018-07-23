package kr.co.daou.sdev.altong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.daou.sdev.altong.domain.alert.AlertMaster;
import kr.co.daou.sdev.altong.enumeration.AlertStatusType;

public interface AlertMasterRepository extends JpaRepository<AlertMaster, Integer> {
	List<AlertMaster> findByAlertStatusOrderByAlertNo(AlertStatusType alertStatus);
	
	//int saveByAlertNoIn(List<AlertMaster> alertMaster);
}
