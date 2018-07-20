package kr.co.daou.sdev.altong.send;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;

import kr.co.daou.sdev.altong.AltongConst;
import kr.co.daou.sdev.altong.AltongConst.RESULT;
import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;
import kr.co.daou.sdev.altong.send.model.ConnectInfo;
import kr.co.daou.sdev.altong.util.AlioCrypto;
import kr.co.daou.sdev.altong.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsUnityMsgImpl extends AbstractUnityMsg {

	private Socket socket;
	private BufferedReader input = null;
	private DataOutputStream output = null;
	private String msgType = "SD";//default SMS
	private String callback = "15999782";
	
	/**
	 * <pre>
	 * 처리내용 : 소켓 접속한다.
	 * </pre>
	 * @Method Name : connectSocket
	 * @throws IOException
	 * @throws SocketException
	 */
	private void connectSocket() throws IOException,SocketException {

		/* 스트림 생성전 초기화 */
		if (input != null) input.close();
		if (output != null)	output.close();
		if (socket != null) socket.close();
		
		/* 소켓의 타임아웃 설정 */
		socket = new Socket(connectInfo.getHost(), connectInfo.getPort());
		socket.setSoTimeout(AltongConst.SOCKET_TIMEOUT * 1000);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream(), AltongConst.SYSTEM_CHARSET));
		output = new DataOutputStream(socket.getOutputStream());
				
		if (socket.isConnected()){
			log.debug(connectInfo.getHost()+ " 접속 성공");
		} else {
			log.error(connectInfo.getHost()+ " 접속 실패");
		}
	}

	/**
	 * <pre>
	 * 처리내용 : 발송을 끝냈다면 클로즈 처리해준다.
	 * </pre>
	 * @Method Name : close
	 */
	private void close() {
		try {
			if (input != null) {
				input.close();
				input = null;
			}
			if (output != null) {
				output.close();
				output = null;
			}
			if (socket != null) {
				socket.close();
				socket = null;
			}
			log.debug("{} close 처리 완료",connectInfo.getHost());
		} catch (IOException e) {
			log.error("close 처리 에러 connection name : {}" , connectInfo.getHost());
		} 
	}
	
	public SmsUnityMsgImpl(ConnectInfo connectInfo) {
		super.connectInfo = connectInfo;
	}
	
	@Override
	public String send(UnityMsgContents contents) {
		
		super.contents = contents;
		
		// isConnection를 구현하지 않았거나 연속으로 send()를 호출했을 경우
		if(socket == null) {
			RESULT result = (isConnect())? RESULT.SUCCESS:RESULT.CONNECTION_ERROR;
			if(result != RESULT.SUCCESS) return result.getValue();
		}
		
		String recv_str = "";
		// 발송처리
		try {
			// 전송데이타 체크 및 변환
			changeData(contents);
			// 메시지를 스트링응로 변환한다.
			String msg_str = toMsgString(contents);
			// 메시지 타입 SMS/LMS를 설정한다.
			String send_str = getBiz5String(msgType, msg_str);
			log.debug("[SEND_STR]" + send_str);
			
			/* 전송 */
			socketSendData(send_str);
			recv_str = socketReadData();
		} catch (Exception e) {
			return RESULT.SEND_FAIL.getValue();
		} finally {
			// 송신처리 후 클로즈 처리한다.
			close();	
		}

		if ("10".equals(recv_str.substring(0, 2))) {
			return RESULT.SUCCESS.getValue();
		} else if ("11".equals(recv_str)) {
			log.error("Free Sender MID Format error");
		} else if ("12".equals(recv_str)) {
			log.error("Free Sender Data Format error");
		} else if ("13".equals(recv_str)) {
			log.error("Free Sender 잘못된 전화번호");
		} else if ("14".equals(recv_str)) {
			log.error("Free Sender 기타 에러");
		}
		
		return RESULT.SEND_FAIL.getValue();
	}

	@Override
	public boolean isConnect() {
		try {
			connectSocket();
			if(socket.isConnected()) return true;
		} catch (SocketException e) {
			log.error("isConnect method SocketException");
		} catch (IOException e) {
			log.error("isConnect method IDException");
		}
		return false;
	}
	
	private String read(int len) throws IOException {
		String read_str = "";
		char[] cbuf = new char[len];

		if (input.read(cbuf, 0, len) != -1) {
			read_str = new String(cbuf, 0, len);
		}
		return read_str;
	}
	
	private String socketReadData() throws Exception {
		String read_str = "";
		try {
			String head_str = read(8);
			int head_len = toInt(head_str);
			read_str = read(head_len);

			if (read_str.length() < 2) {
				throw new Exception("Too Short Code which is received, CODE=" + read_str);
			}
		} catch (IOException ioex) {
			throw new IOException("Failed to ReadData, " + ioex.getMessage());
		}
		return read_str;
	}
	
	private void write(byte[] message) throws IOException {
		try {
			output.write(message);
			output.flush();
		} catch (IOException ioex) {
			throw new IOException("Failed to Write a socket:" + ioex.getMessage());
		}
	}
	
	private void socketSendData(String data) throws Exception {
		write(data.getBytes(AltongConst.SYSTEM_CHARSET));
	}
	
	private int toInt(String src) {
		if (src == null || "".equals(src))
			return 0;
		else {
			try {
				return Integer.parseInt(src);
			} catch (NumberFormatException nfex) {
				return 0;
			}
		}
	}
	
	private String toMsgString(UnityMsgContents contents) throws Exception{

		StringBuffer sb = new StringBuffer();
		
		// 현재시간을 유닉스 타임으로
		String unixTime = Integer.toString(DateUtil.getTimeStamp());
		// 키정보는 유닉스 타임과 첫번째 전화번호를 사용한다.
		String mid = AlioCrypto.toSHA256String(unixTime+contents.getTargets().get(0));
		
		sb.append("SERVICEID:=").append(connectInfo.getUserId()).append("\n");
		sb.append("MID:=").append(mid).append("\n"); 
		sb.append("PHONE:=").append(String.join(",", contents.getTargets())).append("\n"); 
		sb.append("CALLBACK:=").append(callback).append("\n"); 
		sb.append("SUBJECT:=").append(contents.getProjectName()).append("\n"); 
		sb.append("SENDNAME:=").append("AlertSystem").append("\n");
		sb.append("TARGETNAME:=").append("").append("\n");
		sb.append("MSG:=").append(contents.getBody()).append("\n");
		sb.append("UNIXTIME:=").append(unixTime).append("\n");
		sb.append("RESERVE_FLAG:=").append("0").append("\n"); // 즉시발송
		return sb.toString();
	}
	
	
	private void changeData(UnityMsgContents contents) throws Exception{
		
		String subject = contents.getProjectName();
		if (subject != null && subject.length() > AltongConst.LIMIT_MMS_SUBJECT_SIZE) {
			contents.setProjectName(subject.substring(0, AltongConst.LIMIT_MMS_SUBJECT_SIZE));
		}
		
		contents.setBody(contents.getBody().replace('\0', ' '));
		String msg_body = contents.getBody();
		// 본문이 90바이트보다 클경우 LMS로 작을 경우는 SMS
		msgType = (msg_body.length() > AltongConst.SMS_BODY_SIZE)? "MD":"SD";
		
		log.debug("Msg Type : {}",msgType);
		log.debug("msgbody : {}",msg_body);

		contents.setBody("<<__START__\n" + msg_body + "__END__>>");

		log.debug("changed msg : {}",contents.getBody());
	}
	
	private String getBiz5String(String code, String send_str) {
		DecimalFormat df = new DecimalFormat("00000000");
		int biz5_len = 0;

		try {
			biz5_len = 2 + send_str.getBytes(AltongConst.SYSTEM_CHARSET).length;
		} catch (UnsupportedEncodingException ueex) {
			biz5_len = 2 + send_str.length();
		}

		String biz5_str = df.format(biz5_len) + code + send_str;
		return biz5_str;
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
