package kr.co.daou.sdev.altong.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.service.AdminUserService;
import kr.co.daou.sdev.altong.service.DaouServiceService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdminUserController {

	@Autowired
	private Provider<AdminUser> adminUserProvider;
	
	@Autowired
	private AdminUserService adminUserService;

	@Autowired
	private DaouServiceService daouServiceService;

	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login() {
		return "login";
	}


	/**
	 * 운영자 리스트
	 * @param model model
	 * @param pageable pageable
	 * @return view
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/admins")
	public String getAdmins(Model model, @PageableDefault(sort = "lastLoginDate", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<AdminUser> pageResult = adminUserService.getAllAdminUsers(pageable);

		model.addAttribute("pageResult", pageResult);

		return "admin/admins";
	}

	/**
	 * 운영자 등록 폼
	 * @return view
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/admins", params = "write")
	public String adminWriteForm() {
		return "admin/admin-form-modal";
	}

	/**
	 * 운영자 수정 폼
	 * @param model model
	 * @return view
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/admins/{userId}")
	public String adminModifyForm(Model model, @PathVariable("userId") String userId) {
		model.addAttribute("adminUser", adminUserService.loadUserByUsername(userId));
		return "admin/admin-form-modal";
	}

	/**
	 * 나의 비밀번호 변경 폼
	 * @return view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/admins/password")
	public String passwordForm() {
		return "admin/password-form-modal";
	}

	/**
	 * 나의 정보 변경
	 * @param model model
	 * @return view
	 */
	@Secured("ROLE_USER")
	@GetMapping(value = "/admins/user-info")
	public String userInfoForm(Model model) {
		AdminUser adminUser = adminUserProvider.get();

		model.addAttribute("adminUser", adminUserService.loadUserByUsername(adminUser.getUserId()));
		return "admin/admin-form-modal";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/admins/{userId}/services")
	public String adminUserServicesModal(Model model, @PathVariable String userId) {
		
		Set<DaouService> allServices = daouServiceService.getAllDaouServices();
		AdminUser adminUser = adminUserService.getAdminUser(userId);

		log.debug("allServices.size()={}", allServices.size());

		List<DaouService> unusedServices = new ArrayList<>();

		boolean isUnused = true;
		for (DaouService service : allServices) {
			for (DaouService adminService : adminUser.getServices()) {
				if (service.equals(adminService)) {
					isUnused = false;
					break;
				}
			}
			if (isUnused) {
				unusedServices.add(service);
			}
			isUnused = true;
		}

		model.addAttribute("allServices", unusedServices);
		model.addAttribute("adminUser", adminUser);

		return "/admin/admin-services-modal";
	}

}