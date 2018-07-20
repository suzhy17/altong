package kr.co.daou.sdev.altong.dto.alert;

import javax.validation.constraints.NotNull;

import kr.co.daou.sdev.altong.domain.project.SendType;
import lombok.Data;
@Data
public class AlertResultDto {
	private Long resultNo;
	
	private Boolean sendStatus;
	
	private SendType sendType;
	
	@NotNull(message = "alertNo는  반드시 입력해야 합니다.")
	private Integer alertNo;
	
	@NotNull(message = "memberNo는  반드시 입력해야 합니다.")
	private Integer memberNo;
}
