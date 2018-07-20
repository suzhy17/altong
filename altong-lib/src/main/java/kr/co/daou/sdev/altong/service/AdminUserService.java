package kr.co.daou.sdev.altong.service;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.domain.admin.PasswordGroup;
import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.repository.AdminUserRepository;
import kr.co.daou.sdev.altong.repository.DaouServiceRepository;
import kr.co.daou.sdev.altong.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AdminUserService implements UserDetailsManager {

	@Autowired
	private AdminUserRepository adminUserRepository;

	@Autowired
	private DaouServiceRepository daouServiceRepository;

	@Autowired(required = false)
	private SaltSource saltSource;

	@Autowired(required = false)
	private ShaPasswordEncoder passwordEncoder;

	@Transactional
	public AdminUser getAdminUser(String userId) {
		log.debug("userId={}", userId);

		AdminUser adminUser = adminUserRepository.findOne(userId);

		log.debug("adminUser={}", adminUser);

		return adminUser;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AdminUser loadUserByUsername(String userName) {
		log.debug("userName={}", userName);

		AdminUser adminUser = adminUserRepository.findOne(userName);

		log.debug("adminUser={}", adminUser);

		if (adminUser == null) {
			//return null;
			//throw new InternalAuthenticationServiceException("사용자가 없거나 비밀번호가 틀렸습니다.");
			throw new UsernameNotFoundException("사용자가 없거나 비밀번호가 틀렸습니다.");
			// throw new UnityAlertException("사용자가 없거나 비밀번호가 틀렸습니다.");
		}

		List<GrantedAuthority> createAuthorityList = AuthorityUtils.createAuthorityList(adminUser.getAuthority().split(","));
		adminUser.setAuthorities(createAuthorityList);

		return adminUser;
	}

	@Override
	@Transactional
	public void changePassword(String oldPassword, String newPassword) {

		if (StringUtils.isBlank(oldPassword)) {
			throw new AltongException.InvalidArgumentException("기존 비밀번호가 없습니다.");
		}

		if (StringUtils.isBlank(newPassword)) {
			throw new AltongException.InvalidArgumentException("새 비밀번호가 없습니다.");
		}

		if (StringUtils.equals(oldPassword, newPassword)) {
			throw new AltongException.InvalidArgumentException("기존 비밀번호와 동일합니다.");
		}

		if (!PasswordUtil.isComplexPassword(newPassword)) {
			throw new AltongException.InvalidArgumentException("비밀번호는 숫자/영문자 조합으로 10자리 이상으로만 사용 가능합니다.");
		}

		if (!PasswordUtil.isStrengthPassword(newPassword)) {
			throw new AltongException.InvalidArgumentException("비밀번호는 동일한 숫자/영문자, 연속된 숫자/영문자를 3개 초과하여 사용할 수 없습니다.");
		}

		AdminUser adminUser = this.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		String encodeOldPassword = passwordEncoder.encodePassword(oldPassword, saltSource.getSalt(adminUser));

		if (!StringUtils.equals(adminUser.getPasswordGroup().getPassword(), encodeOldPassword)) {
			throw new AltongException.InvalidArgumentException("기존 비밀번호가 틀렸습니다.");
		}

		PasswordGroup passwordGroup = new PasswordGroup(
				passwordEncoder.encodePassword(newPassword, saltSource.getSalt(adminUser)),
				Boolean.FALSE
		);

		adminUser.setPasswordGroup(passwordGroup);

		SecurityContextHolder.clearContext();
	}

	@Override
	@Transactional
	public void createUser(UserDetails userDetails) {

		String tempPassword = PasswordUtil.getTempPassword();

		AdminUser adminUser = (AdminUser) userDetails;
		adminUser.setAuthority("ROLE_USER");

		PasswordGroup passwordGroup = new PasswordGroup(
				passwordEncoder.encodePassword(tempPassword, saltSource.getSalt(adminUser)),
				Boolean.TRUE
		);

		adminUser.setPasswordGroup(passwordGroup);
		log.debug("password={}", passwordGroup.getPassword());

		adminUserRepository.save(adminUser);

		String msg = "통합알림시스템 관리자 사이트 임시 패스워드가 발급되었습니다. [" + tempPassword + "]";
		log.debug(msg);
	}

	@Override
	public void deleteUser(String userId) {
		adminUserRepository.delete(userId);
	}

	@Override
	public void updateUser(UserDetails userDetails) {
		adminUserRepository.save((AdminUser) userDetails);
	}

	@Transactional
	public void updateUser(String userId, String mobileNo, String email) {
		AdminUser adminUser = this.loadUserByUsername(userId);
		adminUser.changeMobileNo(mobileNo);
		adminUser.changeEmail(email);
	}
	
	/**
	 * 임시 패스워드 발급
	 * @param userId 사용자ID
	 */
	@Transactional
	public void tempPassword(String userId) {
		String tempPassword = PasswordUtil.getTempPassword();

		AdminUser adminUser = this.loadUserByUsername(userId);
		log.debug("old={}", adminUser.getPassword());

		PasswordGroup passwordGroup = new PasswordGroup(
				passwordEncoder.encodePassword(tempPassword, saltSource.getSalt(adminUser)),
				Boolean.TRUE
		);
		log.debug("new={}", passwordGroup.getPassword());

		adminUser.setPasswordGroup(passwordGroup);
		adminUser.resetLoginFailCount();

		adminUserRepository.save(adminUser);

		String msg = "이모티콘 통합관리 임시 패스워드가 발급되었습니다. [" + tempPassword + "]";
		log.debug(msg);
	}
	
	/**
	 * OTP 초기화
	 * @param userId 사용자ID
	 */
	@Transactional
	public void otpReset(String userId) {
		AdminUser adminUser = this.loadUserByUsername(userId);
		adminUser.resetEncodedKey();

		// TODO 이메일로 otp code 발송
}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean userExists(String userId) {
		return adminUserRepository.exists(userId);
	}

	/**
	 * 모든 관리자 리스트
	 * @param pageable pageable
	 * @return 모든 관리자 리스트
	 */
	@Transactional(readOnly = true)
	public Page<AdminUser> getAllAdminUsers(Pageable pageable) {
		return adminUserRepository.findAll(pageable);
	}

	/**
	 * 관리 서비스 지정 (운영자 기준) 
	 * @param userId 사용자ID
	 * @param serviceIds 관리 서비스의 ID 리스트
	 */
	@Transactional
	public void saveAdminServices(String userId, List<String> serviceIds) {
		AdminUser adminUser = this.getAdminUser(userId);
		adminUser.getServices().clear();

		List<DaouService> daouServices = daouServiceRepository.findByServiceIdIn(serviceIds);

		for (DaouService daouService : daouServices) {
			adminUser.addService(daouService);
		}
	}

	/**
	 * 로그인 실패 건수 업데이트
	 * @param userId 사용자ID
	 */
	@Transactional
	public void addLoginFailCount(String userId) {
		AdminUser adminUser = this.getAdminUser(userId);

		if (adminUser == null) {
			log.info("사용자ID가 없음 [userId={}]", userId);
			return;
		}

		adminUser.addLoginFailCount();
		log.info("로그인 실패 건수 업데이트 [userId={}, loginFailCount={}]", userId, adminUser.getLoginFailCount());
	}
}
