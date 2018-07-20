package kr.co.daou.sdev.altong.controller.admin;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.dto.admin.AdminUserDto;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.mapper.CommonModelMapper;
import kr.co.daou.sdev.altong.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Provider;
import java.util.List;

@Slf4j
@RestController
public class AdminUserRestController {

	@Autowired
	private CommonModelMapper modelMapper;

	@Autowired
	private Provider<AdminUser> adminUserProvider;

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 운영자 등록 처리
	 * @param adminUserDto adminUserDto
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/admins", method = RequestMethod.POST)
	public void registerAdmin(@ModelAttribute AdminUserDto adminUserDto) {
		log.debug("adminUser=[{},{},{},{}]", adminUserDto.getUserId(), adminUserDto.getName(), adminUserDto.getEmail(), adminUserDto.getMobileNo());
		AdminUser adminUser = modelMapper.map(adminUserDto, AdminUser.class);
		adminUserService.createUser(adminUser);
	}

	/**
	 * 운영자 정보 변경 처리
	 * @param userId 사용자ID
	 * @param mobileNo 휴대폰 번호
	 * @param email 이메일
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/admins/{userId}", method = RequestMethod.PUT)
	public void modifyAdmin(
			@PathVariable("userId") String userId,
			@RequestParam String mobileNo,
			@RequestParam String email) {

		AdminUser adminUser = adminUserProvider.get();
		if (!adminUser.isAdmin() && !StringUtils.equals(adminUser.getUserId(), userId)) {
			throw new AltongException.UnauthorizedException();
		}
		
		adminUserService.updateUser(userId, mobileNo, email);
	}

	/**
	 * 운영자 삭제
	 * @param userId userId
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/admins/{userId}", method = RequestMethod.DELETE)
	public void deleteAdmin(@PathVariable("userId") String userId) {
		adminUserService.deleteUser(userId);
	}

	/**
	 * 임시 비밀번호 발급
	 * @param userId userId
	 */
	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/admins/{userId}/temp-password")
	public void resetAdminPassword(@PathVariable("userId") String userId) {
		adminUserService.tempPassword(userId);
	}

	/**
	 * OTP 초기화
	 * @param userId userId
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/admins/{userId}/otp-reset", method = RequestMethod.PUT)
	public void resetAdminOtp(@PathVariable("userId") String userId) {
		adminUserService.otpReset(userId);
	}

	/**
	 * 나의 비밀번호 변경 처리
	 * @param oldPassword 이전 비밀번호
	 * @param newPassword 신규 비밀번호
	 */
	@Secured("ROLE_USER")
	@PutMapping(value = "/admins/password")
	public void changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
		adminUserService.changePassword(oldPassword, newPassword);
	}

	/**
	 * 나의 정보 변경 처리
	 * @param mobileNo 휴대폰 번호
	 * @param email 이메일
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/admins/user-info", method = RequestMethod.PUT)
	public void modifyUserInfo(
			@RequestParam String mobileNo,
			@RequestParam String email) {

		AdminUser adminUser = adminUserProvider.get();

		adminUserService.updateUser(adminUser.getUserId(), mobileNo, email);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/admins/{userId}/services")
	public void adminUserServicesModal(
			@PathVariable String userId,
			@RequestParam(value="serviceIds[]", required = false) List<String> serviceIds) {

		for (String serviceId : serviceIds) {
			log.info("serviceId={}", serviceId);
		}
		
		adminUserService.saveAdminServices(userId, serviceIds);
	}

}