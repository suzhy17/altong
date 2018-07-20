package kr.co.daou.sdev.altong.service;

import kr.co.daou.sdev.altong.domain.alert.AlertResult;
import kr.co.daou.sdev.altong.dto.alert.AlertResultDto;

public interface AlertResultService {
	AlertResult getAlertResult(Integer resultNo);

	void saveResult(AlertResultDto alertResultDto);
	
	void removeResult(Integer AlertResult);
}
