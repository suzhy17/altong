package kr.co.daou.sdev.altong.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.daou.sdev.altong.domain.alert.AlertMaster;
import kr.co.daou.sdev.altong.domain.alert.AlertStatusType;
import kr.co.daou.sdev.altong.dto.alert.AlertMasterDto;

public interface AlertMasterService {
	Page<AlertMaster> getAlertMasters(Pageable pageable);	
	AlertMaster getAlertMaster(Integer alertNo);
		
	void saveMaster(AlertMasterDto alertMasterDto);

	void removeMaster(Integer AlertMaster);
	
	/**
	 * <pre>
	 * 처리내용 : 마스터의 상태가 발송대기인 것을 패치한다. ( 최대 500개 )
	 * </pre>
	 * @Method Name : getAlertFirstMasterList
	 * @return
	 */
	List<AlertMaster> getAlertFirstMasterList();
	
	/**
	 * <pre>
	 * 처리내용 : 마스터의 상태가 2차 발송 대상인 것을 패치한다. ( 최대 500개 )
	 * </pre>
	 * @Method Name : getAlertSecondMasterList
	 * @return
	 */
	List<AlertMaster> getAlertSecondMasterList();
	
	/**
	 * <pre>
	 * 처리내용 :
	 * </pre>
	 * @Method Name : changeMasterStatus
	 * @param alertNo
	 * @param alertStatusType
	 */
	void changeMasterStatus(Integer alertNo,AlertStatusType alertStatusType);
	
}
