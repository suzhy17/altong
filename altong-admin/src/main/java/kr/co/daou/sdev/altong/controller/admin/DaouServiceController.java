package kr.co.daou.sdev.altong.controller.admin;

import java.util.Set;

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

import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.service.DaouServiceService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DaouServiceController {

	@Autowired
	private DaouServiceService daouServiceService;

	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/services")
	public String getServices(Model model,
			@PageableDefault(size = 10, sort = {"serviceId"}, direction = Sort.Direction.ASC) Pageable pageable) {

		Page<DaouService> pageResult = daouServiceService.getDaouServices(pageable);

		model.addAttribute("pageResult", pageResult);

		return "service/services";
	}

	/**
	 * 서비스 등록 폼
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/services", params = "write", method = RequestMethod.GET)
	public String serviceWriteForm() {
		return "service/service-form";
	}

	/**
	 * 서비스 변경 폼
	 * @param model model
	 * @param model serviceId
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/services/{serviceId}")
	public String serviceModifyForm(Model model, @PathVariable("serviceId") String serviceId) {
		model.addAttribute("service", daouServiceService.getDaouService(serviceId));
		return "service/service-form";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/services/{serviceId}/admins/{adminId}")
	public String getServices(Model model) {

		Set<DaouService> daouServices = daouServiceService.getAllDaouServices();

		model.addAttribute("daouServices", daouServices);

		return "service/services";
	}


}