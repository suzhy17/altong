package kr.co.daou.sdev.altong.domain.admin;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Embeddable
public class PasswordGroup {

	@Column(length = 128, nullable = false)
	private String password;

	@Column(nullable = false)
	private LocalDateTime passwordChangeDate = LocalDateTime.now();

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private Boolean tempPassword = Boolean.FALSE;

	public PasswordGroup(String password, Boolean tempPassword) {
		this.password = password;
		this.tempPassword = tempPassword;
	}
}
