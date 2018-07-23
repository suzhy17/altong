package kr.co.daou.sdev.altong.dto.alert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kr.co.daou.sdev.altong.enumeration.AlertStatusType;
import lombok.Data;
@Data
public class AlertMasterDto {
	private Integer alertNo;

	@NotNull(message = "ProjectNo는  반드시 입력해야 합니다.")
	private int projectNo;

	@Size(min = 5, max=2000, message = "메시지를 입력하세요")
	private String message;
	
	private AlertStatusType alertStatus;
}
