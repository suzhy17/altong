package kr.co.daou.sdev.altong.dto.admin;

import java.time.LocalDateTime;
import java.util.List;

import kr.co.daou.sdev.altong.dto.project.DaouServiceDto;
import lombok.Data;

@Data
public class AdminUserDto {

	private String userId;
	private String name;
	private String email;
	private String mobileNo;
	private String authority;
	private String encodedKey;
	private LocalDateTime lastLoginDate;
	private Integer loginFailCount = 0;
	private List<DaouServiceDto> services;
}
