package kr.co.daou.sdev.altong;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AltongConst {

	public final static String API_LOGIN = "/api/login";
	public final static String API_SESSION = "/api/user/session";
	public final static String API_PUBSUB = "/api/chat/pubsubs/external";
	
	// socket time out ( second )
	public final static int SOCKET_TIMEOUT = 3;
	public static final String SYSTEM_CHARSET = "EUC-KR"; // 시스템 캐릭터셋
	
	public static int LIMIT_MMS_TXT_SIZE = 2000; // 최대 MMS 텍스트 크기. 단위 Byte
	public static int LIMIT_MMS_IMG_SIZE = 60; // 기본 MMS 이미지 크기 제한. 단위 KB
	public static int MAX_LIMIT_MMS_IMG_SIZE = 300; // 최대 MMS 이미지 크기 제한 단위 KB 
	public static int LIMIT_MMS_SUBJECT_SIZE= 64;//최대 LMS/MMS SUBJECT크기, 단위 byte

	public static int SMS_BODY_SIZE = 90; //SMS발송 MESSAGE BODY SIZE설정값
	public static int SEND_SLEEP_TIME = 100; 
	public static int THREAD_SLEEP=5;
	
	public static final int MSG_TYPE_SMS = 0;
	public static final int MSG_TYPE_WAP = 1;
	public static final int MSG_TYPE_FAX = 2;
	public static final int MSG_TYPE_PHONE = 3;
	public static final int MSG_TYPE_LMS = 4;
	
	@Getter
	@AllArgsConstructor
	public enum RESULT{
		SUCCESS("01"),
		CONNECTION_ERROR("90"),
		SEND_FAIL("99");
		private String value;
	}
}
