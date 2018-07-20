package kr.co.daou.sdev.altong.send;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.daou.sdev.altong.AltongConst;
import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;
import kr.co.daou.sdev.altong.send.model.ConnectInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * kr.co.daou.sdev.altong.send
 * DaouOfficeUnityMsgImpl.java
 * 다우오피스용 메시지 sender
 * </pre>
 *
 *	@author  : yoonsm
 *  @Date    :2017. 11. 13.
 *  @Version : 0.1
 */
@Slf4j
public class DaouOfficeUnityMsgImpl extends AbstractUnityMsg {
	
	private String doUserId = null;
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper mapper = new ObjectMapper();
	
	public DaouOfficeUnityMsgImpl(ConnectInfo connectInfo) {
		super.connectInfo = connectInfo;
	}
	
	@Override
	public String send(UnityMsgContents contents) {
		
		super.contents = contents; 
		
		HttpHeaders inHeaders = new HttpHeaders();
		inHeaders.add("Cookie", goSessionCookieValue);
		inHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> param= new LinkedMultiValueMap<String, String>();
		param.add("userSeq", doUserId);
		//param.add("targetEmpNo", contents.get);
		param.add("resource", "legacy_system");
		param.add("message", contents.toString());
		
		if(contents.getTargets().isEmpty()) {
			return AltongConst.RESULT.SEND_FAIL.getValue();
		} else {
			// 발송대상을 격납한다.
			for(String target : contents.getTargets()) {
				param.add("targetEmpNo", target);	
			}
		}
		
		HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(param,inHeaders);
		ResponseEntity<String> userResponse = restTemplate.exchange(connectInfo.getHost()+AltongConst.API_PUBSUB
				,HttpMethod.POST,requestEntity,String.class);
		
		JsonNode node;
		try {
			node = mapper.readTree(userResponse.getBody());
			if(StringUtils.equals(node.get("code").textValue(),"200")){
				return AltongConst.RESULT.SUCCESS.getValue();
			}
		} catch (IOException e) {
			log.error("DaouOffice IOException");
			return AltongConst.RESULT.SEND_FAIL.getValue();
		}
		return AltongConst.RESULT.SUCCESS.getValue();
	}

	@Override
	public boolean isConnect() {
		//초기 세션을 취득한다.
		if(goSessionCookieValue == null) getSession();
		
		// 발송 유저 정보를 취득한다.( 세션키로 )
		doUserId = getUserId();
		
		// 세션이 만료 되었거나 세션키가 틀릴경우 한번 더 세션을 취득한다.
		if(doUserId == null) {
			getSession();
			doUserId = getUserId();
		}
		log.info("다우오피스 발송유저 정보 : {}",doUserId);
		
		// 발송유저 정보를 취득했다면 true / 통신에러 또는 인증이 틀릴경우는 null 이므로 false
		if(StringUtils.isNotEmpty(doUserId)) {
			return true;
		} 
		
		return false;
	}

	/**
	 * <pre>
	 * 처리내용 : 로그인 ( 세션이 없거나 갱신되어서 틀릴경우 )
	 * </pre>
	 * @Method Name : login
	 * @param doUser
	 */
	private void getSession() {
		log.debug("다우오피스 접속대상 : {}",connectInfo.getHost());
		log.debug("다우오피스 접속유저 : {}",connectInfo.getUsername());
		HttpEntity<ConnectInfo> request = new HttpEntity<ConnectInfo>(connectInfo);
		ResponseEntity<String> response = restTemplate.exchange(connectInfo.getHost()+AltongConst.API_LOGIN, HttpMethod.POST, request, String.class);
		goSessionCookieValue = response.getHeaders().get("Set-Cookie").get(2).split(";")[0].trim()+";";
		log.debug("goSessionCookieValue:{}",goSessionCookieValue);
	}
	
	/**
	 * <pre>
	 * 처리내용 : 유저정보를 취득한다. (헤더에 세션키 설정)
	 * </pre>
	 * @Method Name : getUserId
	 * @return
	 */
	private String getUserId() {
		String retUserId = null;
		
		try {
			// 발송자 정보를 취득한다.
			HttpHeaders inHeaders = new HttpHeaders();
			inHeaders.add("Cookie", goSessionCookieValue);
			inHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<String> requestEntity = new HttpEntity<>(null,inHeaders);
			ResponseEntity<String> userResponse = restTemplate.exchange(connectInfo.getHost()+AltongConst.API_SESSION
					,HttpMethod.GET,requestEntity,String.class);
			
			JsonNode node = mapper.readTree(userResponse.getBody());
			if(StringUtils.equals(node.get("code").textValue(),"200")){
				return  node.path("data").get("repId").toString();
			} 
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException");
		} catch (IOException e) {
			log.error("IOException");
		}
		return retUserId;
	}

	@Override
	public void setContents(UnityMsgContents contents) {
		super.contents = contents;
	}

	@Override
	public UnityMsgContents getContents() {
		return contents;
	}
}
