package kr.co.daou.sdev.altong.dto.project;

import kr.co.daou.sdev.altong.domain.project.SmartPhoneType;
import lombok.Data;

@Data
public class SmartPhoneDto {
	private SmartPhoneType smartPhoneType;
	private String pushNo;
}
