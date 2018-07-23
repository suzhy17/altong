package kr.co.daou.sdev.altong.dto.project;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import kr.co.daou.sdev.altong.enumeration.SendType;
import lombok.Data;

@Data
public class ProjectDto {
	private Integer projectNo;

	@Size(min = 1, message = "서비스ID는 반드시 선택해야 합니다.")
	private String serviceId;

	@Size(min = 5, max = 100, message = "프로젝트ID는 5자이상 100자 이하의 값만 가능합니다.")
	@Pattern(regexp = "^[_0-9a-zA-Z-]+$", message = "프로젝트ID는 알파벳, 숫자만 가능합니다.")
	private String projectId;

	@Size(min = 5, max = 100, message = "프로젝트명은 5자이상 100자 이하의 값만 가능합니다.")
	private String projectName;

	private Boolean projectStatus;

	@NotNull(message = "1차 발송 타입은 반드시 선택해야 합니다.")
	private SendType sendType;

	@NotNull(message = "2차 발송 타입은 반드시 선택해야 합니다.")
	private SendType resendType;

	@NotNull(message = "발송 제한은 반드시 선택해야 합니다.")
	private Boolean sendLimitYn;

	private String regUserId;

	private LocalDateTime regDatetime;

	private LocalDateTime modDatetime;

	private DaouServiceDto daouService;

	private Set<MemberDto> memberDtos;

	public Integer getMemberCount() {
		if (memberDtos == null) {
			return 0;
		}
		return memberDtos.size();
	}
}
