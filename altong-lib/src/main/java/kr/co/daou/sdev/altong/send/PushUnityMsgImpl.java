package kr.co.daou.sdev.altong.send;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.daou.sdev.altong.AltongConst;
import kr.co.daou.sdev.altong.AltongConst.RESULT;
import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;
import kr.co.daou.sdev.altong.send.model.ConnectInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushUnityMsgImpl extends AbstractUnityMsg {
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper mapper = new ObjectMapper();
	
	private String fcmApiKey;
	private String pushUrl;

	public PushUnityMsgImpl(ConnectInfo connectInfo) {
		super.connectInfo = connectInfo;
	}
	
	@Override
	public String send(UnityMsgContents contents) {
		
		super.contents = contents;
		String result = AltongConst.RESULT.SUCCESS.getValue();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Authorization", "key=" + fcmApiKey);

		if(StringUtils.isBlank(contents.getProjectName())){
			return AltongConst.RESULT.SEND_FAIL.getValue();
		}
		if(StringUtils.isBlank(contents.getBody())){
			return AltongConst.RESULT.SEND_FAIL.getValue();
		}
		if(contents.getTargets().isEmpty()) {
			return AltongConst.RESULT.SEND_FAIL.getValue();
		}
		
		int succCnt = 0;

		try {
			Map<String, String> pushInfo = new HashMap<>();
			pushInfo.put("title", contents.getProjectName());
			pushInfo.put("message", contents.getBody());

			for (String target : contents.getTargets()) {
				Map<String, Object> main = new HashMap<>();
				main.put("data", pushInfo);
				main.put("to", target);
				String jsonString = mapper.writeValueAsString(main);
				HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
				ResponseEntity<String> pushResult = restTemplate.exchange(pushUrl, HttpMethod.POST, requestEntity,String.class);
				
				JsonNode node = mapper.readTree(pushResult.getBody());
				if(StringUtils.equals(node.get("success").toString(),"1")){
					succCnt++;
				}
			}
			
	        if( succCnt != contents.getTargets().size() ) 
	        	result = AltongConst.RESULT.SEND_FAIL.getValue();

		} catch (JsonProcessingException e) {
			log.error(e.toString());
			return RESULT.SEND_FAIL.getValue();
		} catch (IOException e) {
			log.error(e.toString());
			return RESULT.SEND_FAIL.getValue();
		}

		return result;
	}

	@Override
	public boolean isConnect() {
		fcmApiKey = connectInfo.getUserId();
		if( StringUtils.isEmpty(fcmApiKey) )
			return false;
		
		pushUrl = connectInfo.getHost();
		if( StringUtils.isEmpty(pushUrl) )
			return false;
		
//		log.debug("PUSH URL : {}", pushUrl);
//		log.debug("FCM KEY : {}", fcmApiKey);
		
		return true;
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
