package kr.co.daou.sdev.altong.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.daou.sdev.altong.domain.alert.AlertMaster;
import kr.co.daou.sdev.altong.enumeration.AlertStatusType;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.dto.alert.AlertMasterDto;
import kr.co.daou.sdev.altong.result.JsonResult;
import kr.co.daou.sdev.altong.result.JsonResultType;
import kr.co.daou.sdev.altong.service.AlertMasterService;
import kr.co.daou.sdev.altong.service.ProjectService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@Api
public class ApiController {

	@Autowired
	private AlertMasterService alertMasterService;
	
	@Autowired
	private ProjectService projectService;


	@ApiOperation(value = "발송신청",produces = "application/json", notes = "발송신청을 하는 기능",response=JsonResult.class)
	@RequestMapping(value = "/api/alert", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public JsonResult send(@RequestParam String projectId ,@RequestParam String message) {
		// 프로젝트 타입 가져오기 
		Project project = projectService.getProjectByProjectId(projectId);

		if (project == null) {
			return new JsonResult(JsonResultType.FAIL, "유효한 프로젝트 ID가 아닙니다.");
		}
		
		int projectNo = project.getProjectNo();
		log.info("getResendType : "+ project.getResendType());
		log.info("getsendType : "+ project.getSendType());
		if (project.getSendType().equals(SendType.SMS) || project.getResendType().equals(SendType.SMS) ) {
			log.info("message : "+ message.length());
		}
		log.info("message : "+ message.length());
			
			
		log.info("발송신청  input : {}",projectId);
		
		Map<String, Object> resObj = new HashMap<>();
		resObj.put("projectNo", projectNo);
		resObj.put("message", message);
		
		AlertMasterDto alertMasterDto = new AlertMasterDto();
		alertMasterDto.setProjectNo(projectNo);
		alertMasterDto.setMessage(message);
		alertMasterDto.setAlertStatus(AlertStatusType.READY);
		alertMasterService.saveMaster(alertMasterDto);
		
		return new JsonResult(resObj);
	}
	

	@ApiOperation(value = "발송조회상세",produces = "application/json", notes = "발송조회상세 기능",response=JsonResult.class)
	@RequestMapping(value = "/api/alertDetail", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public JsonResult detail(@RequestParam Integer projectNo ,@RequestParam Integer alertNo) {

		log.info("발송조회상세 input : {}",projectNo);
		
		Map<String, Object> resObj = new HashMap<>();
		resObj.put("projectNo", projectNo);
		resObj.put("alertNo", alertNo);
		AlertMaster alertMaster = alertMasterService.getAlertMaster(alertNo);
		resObj.put("status", alertMaster.getAlertStatus());
		
		return new JsonResult(resObj);
	}
	
	/*
	@ApiOperation(value = "발송조회리스트",produces = "application/json", notes = "발송조회리스트 기능",response=JsonResult.class)
	@RequestMapping(value = "/api/alerts", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public JsonResult alertList(@RequestParam Integer projectNo ,@RequestParam String perPage,@RequestParam String page
			,@PageableDefault(sort = {"alertNo"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable
			) {

		log.info("발송조회리스트 input : {}",projectNo);
		
		Map<String, Object> resObj = new HashMap<>();
		resObj.put("projectNo", projectNo);
		resObj.put("page", page);
		
		
		Page<AlertMaster> list = alertMasterService.getAlertMasters(pageable);
		resObj.put("list", list);
		
		return new JsonResult(resObj);
	}
	*/
	@ApiOperation(value = "프로젝트 리스트",produces = "application/json", notes = "발송조회리스트 기능",response=JsonResult.class)
	@RequestMapping(value = "/api/projects", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public JsonResult projectList(@RequestParam String serviceId ,@PageableDefault(sort = {"projectNo"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable
			) {

		log.info("프로젝트 리스트  Start !! ");
		Page<Project> pageResult = projectService.getProjects(serviceId, pageable);

		return new JsonResult().addResObj("list", pageResult);
	}	
	
}
