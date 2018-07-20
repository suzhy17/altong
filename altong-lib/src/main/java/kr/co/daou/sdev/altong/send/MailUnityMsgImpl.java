package kr.co.daou.sdev.altong.send;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import kr.co.daou.sdev.altong.AltongConst;
import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;
import kr.co.daou.sdev.altong.send.model.ConnectInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailUnityMsgImpl extends AbstractUnityMsg {
	
	JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	
	public MailUnityMsgImpl(ConnectInfo connectInfo) {
		super.connectInfo = connectInfo;
	}
	
	@Override
	public String send(UnityMsgContents contents) {
		
		super.contents = contents;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			
			//발신정보
			helper.setFrom(connectInfo.getHost(), "통합알림 시스템");
			//제목
			if(StringUtils.isNotBlank(contents.getProjectName())){
				helper.setSubject(contents.getProjectName());
			}
			//본문
			helper.setText(contents.getBody(),true);
			//수신정보
			helper.setTo(contents.getTargets().toArray(new String[contents.getTargets().size()]));

			//첨부파일
//			List<String> attachList = contents.getAttachList();
//			if(attachList!=null && !attachList.isEmpty()){
//				for(String fileUrl : attachList){
//					FileSystemResource file = new FileSystemResource(new File(fileUrl));
//					helper.addAttachment(file.getFilename(), file);
//				}
//			}
			
			javaMailSender.send(mimeMessage);
			return AltongConst.RESULT.SUCCESS.getValue();
			
		} catch (MessagingException e) {
			e.printStackTrace();
			return AltongConst.RESULT.SEND_FAIL.getValue();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return AltongConst.RESULT.SEND_FAIL.getValue();
		}
	}

	@Override
	public boolean isConnect() {

		javaMailSender.setHost(connectInfo.getHost());
		javaMailSender.setDefaultEncoding("UTF-8");
		javaMailSender.setPort(connectInfo.getPort());
		javaMailSender.setUsername(connectInfo.getUserId());
		log.info("Mail connection host :{}",connectInfo.getHost());
		
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
