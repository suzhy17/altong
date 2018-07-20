package kr.co.daou.sdev.altong.dto.project;

import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class MemberDto {

	private Integer memberNo;

	@NotNull(message = "사번은 반드시 입력해야 합니다.")
	@Digits(integer = 4, fraction = 0, message = "사번은 4자 이하로 입력해야 합니다.")
	private Integer staffNo;

	@Size(min = 2, message = "이름은 2자 이상이어야 합니다.")
	private String memberName;

	@Pattern(regexp = "^[0-9]{10,11}$", message = "휴대폰 번호는 숫자로 10자이상 11자 이하로 입력해야 합니다.")
	private String mobileNo;

	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 맞지 않습니다.")
	private String email;

	private SmartPhoneDto smartPhone;

	private String remarks;

	private Boolean memberStatus;

	private Set<ProjectDto> projects;
}
