package kr.co.daou.sdev.altong.service;

import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.domain.project.Project;
import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.send.UnityMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 알림 발송 서비스
 * <p>
 * 프로젝트, 멤버 단위로 실시간 발송한다.
 * @author suzhy
 * @since 2017. 11. 28.
 */
@Slf4j
@Service
public class AlertService {

	@Autowired
	private MsgSendService msgSendService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ProjectService projectService;

	/**
	 * 프로젝트에 속한 모든 멤버에게 알림 발송
	 * @param projectId 프로젝트 ID
	 * @param message 메세지
	 * @param sendType 전송 타입
	 */
	public void sendAlert(String projectId, String message, SendType sendType) {

		Project project = projectService.getProjectByProjectId(projectId);
		if (project.getMemberCount() == 0) {
			throw new AltongException.ProjectMemberNotFoundException();
		}

		UnityMsgContents contents = new UnityMsgContents();
		contents.setLevel("INFO");
		contents.setBody(message);
		contents.setProjectName(project.getProjectName());

		project.getMembers().forEach(member -> contents.addTarget(member.getTarget(sendType)));

		UnityMsg unityMsg = msgSendService.createUnityMsg(sendType);
		unityMsg.send(contents);

	}

	/**
	 * 개별 멤버에게 알림 발송
	 * @param memberNo 멤버 번호
	 * @param message 메세지
	 * @param sendType 전송 타입
	 */
	public void sendAlertToMember(Integer memberNo, String message, SendType sendType) {

		Member member = memberService.getMember(memberNo);

		UnityMsgContents contents = new UnityMsgContents();
		contents.setLevel("INFO");
		contents.setBody(message);
		contents.setProjectName("개별 발송");
		contents.addTarget(member.getTarget(sendType));

		UnityMsg unityMsg = msgSendService.createUnityMsg(sendType);
		unityMsg.send(contents);
	}
}
