package kr.co.daou.sdev.altong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.daou.sdev.altong.domain.alert.AlertMaster;
import kr.co.daou.sdev.altong.domain.alert.AlertResult;
import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.dto.alert.AlertResultDto;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.mapper.CommonModelMapper;
import kr.co.daou.sdev.altong.repository.AlertMasterRepository;
import kr.co.daou.sdev.altong.repository.AlertResultRepository;
import kr.co.daou.sdev.altong.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("alertResultService")
public class AlertResultServiceImpl  implements AlertResultService {

	@Autowired
	private CommonModelMapper modelMapper;
	
	@Autowired
	private AlertMasterRepository alertMasterRepository;
	
	@Autowired
	private AlertResultRepository alertResultRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public AlertResult getAlertResult(Integer resultNo) {
		// TODO Auto-generated method stub		
		return alertResultRepository.findOne(resultNo);
	}
	
	@Override
	public void saveResult(AlertResultDto alertResultDto) {
		// TODO Auto-generated method stub		
		
		log.debug("alertResultDto={}", alertResultDto);
		log.debug("saveResult={}", alertResultDto.getAlertNo().toString());
		// AlertMaster 등록 확인
		AlertMaster alertMaster = alertMasterRepository.getOne(alertResultDto.getAlertNo());
		if (alertMaster == null) {
			throw new AltongException.AlertMasterNotFoundException();
		}
		//log.debug("alertMasterNo={}", alertMaster.getAlertNo().toString());
		log.debug("A");
		// Member 등록 확인
		Member member = memberRepository.getOne(alertResultDto.getMemberNo());
		if (member == null) {
			throw new AltongException.MemberNotFoundException();
		}
		//log.debug("memberNo={}", member.getMemberNo().toString());
		log.debug("B");

		AlertResult alertResult = modelMapper.map(alertResultDto, AlertResult.class);		
		alertResult.setMember(member);
		alertResult.setAlertMaster(alertMaster);
		
		alertResultRepository.save(alertResult);
		log.debug("C");
		//log.debug("alertResult={}", alertResult.getResultNo().toString());
	}
	
	@Override
	public void removeResult(Integer alertResult) {
		// TODO Auto-generated method stub
	}

}
