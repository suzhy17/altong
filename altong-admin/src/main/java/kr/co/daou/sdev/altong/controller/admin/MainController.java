package kr.co.daou.sdev.altong.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

	@GetMapping(value = {"/", "/home"})
	public String home(Model model) {

		model.addAttribute("greeting", "World");

		return "home";
	}
}