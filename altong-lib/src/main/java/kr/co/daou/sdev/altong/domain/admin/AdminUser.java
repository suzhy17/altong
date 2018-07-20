package kr.co.daou.sdev.altong.domain.admin;

import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "at_admin_user")
public class AdminUser implements UserDetails {

	private static final long serialVersionUID = 4357434348832811388L;

	@Id
	@Column(length = 20)
	private String userId;

	@Column(length = 20, nullable = false)
	private String name;

	@Column(length = 100, nullable = false)
	private String email;

	@Column(length = 100, nullable = false)
	private String authority;

	@Embedded
	private PasswordGroup passwordGroup;

	@Column(length = 16)
	private String encodedKey;

	@Column
	private LocalDateTime lastLoginDate = LocalDateTime.now();

	@Column(nullable = false)
	private Integer loginFailCount = 0;

	@Column(length = 12)
	private String mobileNo;

	@Transient
	private Collection<? extends GrantedAuthority> authorities;

	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "at_service_admin_user",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "service_id"),
			foreignKey = @ForeignKey(name = "fk_service_admin_user_user_id"),
			inverseForeignKey = @ForeignKey(name = "fk_service_admin_user_service_id")
	)
	private Set<DaouService> services = new HashSet<>();

	public void addService(DaouService service) {
		if (service == null) {
			return;
		}
		services.add(service);
		service.getAdminUsers().add(this);
	}

	public Integer getServiceCount() {
		if (services == null) {
			return 0;
		}
		return services.size();
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> grantedAuthorities) {
		this.authorities = grantedAuthorities;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public void setPasswordGroup(PasswordGroup passwordGroup) {
		this.passwordGroup = passwordGroup;
	}

	public void resetLoginFailCount() {
		this.loginFailCount = 0;
	}

	public void resetLastLoginDate() {
		this.lastLoginDate = LocalDateTime.now();
	}

	public void addLoginFailCount() {
		this.loginFailCount++;
	}

	public void changeEmail(String email) {
		if (!CommonUtil.isEmail(email)) {
			throw new AltongException.InvalidArgumentException("이메일 주소가 유효하지 않습니다.", "email");
			// throw new UnityAlertException("이메일 주소가 유효하지 않습니다.");
		}
		this.email = email;
	}

	public void changeMobileNo(String mobileNo) {
		if (!CommonUtil.isMobileNo(mobileNo)) {
			throw new AltongException.InvalidArgumentException("휴대폰 번호가 유효하지 않습니다.", "mobileNo");
		}
		this.mobileNo = mobileNo;
	}

	public void resetEncodedKey() {
		this.encodedKey = null;
	}

	public String getSalt() {
		return this.userId;
	}

	@Override
	public String getPassword() {
		return this.passwordGroup.getPassword();
	}

	@Override
	public String getUsername() {
		return this.userId;
	}
	
	public boolean isAdmin() {
		return StringUtils.contains(this.getAuthority(), "ROLE_ADMIN");
	}

	/**
	 * 계정 잠김 상태
	 * @return true: 정상, false: 잠김
	 */
	@Override
	public boolean isAccountNonLocked() {
		return this.loginFailCount < 5;
	}

	/**
	 * 비밀번호 만료 여부
	 * @return true: 정상, false: 만료
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		log.debug("lastLoginDate={}", lastLoginDate);
		log.debug("lastLoginDate={}", this.lastLoginDate.isAfter(LocalDateTime.now().minusMonths(3)));
		return this.lastLoginDate.isAfter(LocalDateTime.now().minusMonths(3));
		//return true;
	}

	/**
	 * 계정 만료 여부
	 * @return true: 정상, false: 만료
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
