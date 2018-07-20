package kr.co.daou.sdev.altong.controller.admin;

import kr.co.daou.sdev.altong.dto.project.DaouServiceDto;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.service.DaouServiceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class DaouServiceRestController {

	@Autowired
	private DaouServiceService daouServiceService;

	/**
	 * 서비스 등록 처리
	 * @param daouServiceDto 서비스 DTO
	 */
	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/services")
	public void registerService(@Valid @ModelAttribute DaouServiceDto daouServiceDto) {
		daouServiceService.saveDaouService(daouServiceDto);
	}

	/**
	 * 서비스 정보 변경 처리
	 * @param daouServiceDto 서비스 DTO
	 */
	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/services/{serviceId}")
	public void modifyService(@Valid @ModelAttribute DaouServiceDto daouServiceDto) {

		daouServiceService.updateDaouService(daouServiceDto);
	}

	/**
	 * 서비스 상태 변경 처리
	 * @param serviceId 서비스 ID
	 * @param serviceStatus 서비스 상태
	 */
	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/services/{serviceId}/status")
	public void modifyService(
			@PathVariable("serviceId") String serviceId,
			@RequestParam Boolean serviceStatus) {

		if (StringUtils.isBlank(serviceId)) {
			throw new AltongException.InvalidArgumentException("서비스 ID는 필수 값입니다.", "serviceId");
		}

		if (serviceStatus == null) {
			throw new AltongException.InvalidArgumentException("서비스 상태값은 필수 값입니다.", "serviceStatus");
		}

		daouServiceService.updateDaouServiceStatus(serviceId, serviceStatus);
	}

	/**
	 * 서비스 삭제 처리
	 * @param serviceId 서비스 ID
	 */
	@Secured("ROLE_ADMIN")
	@DeleteMapping(value = "/services/{serviceId}")
	public void removeService(@PathVariable("serviceId") String serviceId) {
		if (StringUtils.isBlank(serviceId)) {
			throw new AltongException.InvalidArgumentException("서비스 ID는 필수 값입니다.", "serviceId");
		}

		daouServiceService.deleteDaouService(serviceId);
	}
}