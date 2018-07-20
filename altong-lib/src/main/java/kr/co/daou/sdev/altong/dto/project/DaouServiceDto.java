package kr.co.daou.sdev.altong.dto.project;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class DaouServiceDto {
	@Size(min = 5, max = 20, message = "서비스ID는 5자이상 20자 이하의 값만 가능합니다.")
	private String serviceId;

	@Size(min = 5, max = 100, message = "서비스명은 5자이상 100자 이하의 값만 가능합니다.")
	private String serviceName;

	private Boolean serviceStatus;
}
