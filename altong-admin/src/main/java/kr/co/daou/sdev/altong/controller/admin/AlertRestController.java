package kr.co.daou.sdev.altong.controller.admin;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.domain.project.SendType;
import kr.co.daou.sdev.altong.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Provider;

@Slf4j
@RestController
public class AlertRestController {

	@Autowired
	private Provider<AdminUser> adminUserProvider;

	@Autowired
	private AlertService alertService;

	/**
	 * 알림 테스트 발송
	 *
	 * @param projectId serviceId
	 * @param message   메시지
	 */
	@Secured("ROLE_USER")
	@PostMapping(value = "/alerts/send")
	public void sendAlert(@RequestParam String projectId,
						  @RequestParam String message,
						  @RequestParam SendType sendType) {

		AdminUser adminUser = adminUserProvider.get();

		alertService.sendAlert(projectId, message, sendType);
	}

	@Secured("ROLE_USER")
	@PostMapping(value = "/alerts/send/{memberNo}")
	public void sendAlertToMember(@PathVariable Integer memberNo,
						  @RequestParam String message,
						  @RequestParam SendType sendType) {

		alertService.sendAlertToMember(memberNo, message, sendType);
	}

}