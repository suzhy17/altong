package kr.co.daou.sdev.altong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.daou.sdev.altong.domain.alert.AlertMaster;
import kr.co.daou.sdev.altong.domain.alert.AlertStatusType;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.dto.alert.AlertMasterDto;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.mapper.CommonModelMapper;
import kr.co.daou.sdev.altong.repository.AlertMasterRepository;
import kr.co.daou.sdev.altong.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("alertMasterService")
public class AlertMasterServiceImpl  implements AlertMasterService {

	@Autowired
	private CommonModelMapper modelMapper;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private AlertMasterRepository alertMasterRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Page<AlertMaster> getAlertMasters(Pageable pageable) {		
		return alertMasterRepository.findAll(pageable);
	}
	
	@Override
	public AlertMaster getAlertMaster(Integer alertNo) {
		// TODO Auto-generated method stub		
		return alertMasterRepository.findOne(alertNo);
	}

	@Override
	public void saveMaster(AlertMasterDto alertMasterDto) {
		// TODO Auto-generated method stub				
		Project project = projectRepository.getOne(alertMasterDto.getProjectNo());
		
		if (project == null) {
			throw new AltongException.ProjectNotFoundException();
		}
		
		log.debug("alertMasterDto={}", alertMasterDto);
		AlertMaster alertMaster = modelMapper.map(alertMasterDto, AlertMaster.class);		
		log.debug("alertMaster={}", alertMaster);
		alertMaster.setProject(project);
		
		alertMasterRepository.save(alertMaster);
			
	}
	
	@Override
	public void removeMaster(Integer alertMaster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AlertMaster> getAlertFirstMasterList() {
		return alertMasterRepository.findByAlertStatusOrderByAlertNo(AlertStatusType.READY);
	}

	@Override
	public List<AlertMaster> getAlertSecondMasterList() {
		return alertMasterRepository.findByAlertStatusOrderByAlertNo(AlertStatusType.REREADY);
	}

	@Override
	@Transactional
	public void changeMasterStatus(Integer alertNo,AlertStatusType alertStatusType) {
		AlertMaster alertMaster = alertMasterRepository.getOne(alertNo);
		alertMaster.setAlertStatus(alertStatusType);
	}
	
	

}
