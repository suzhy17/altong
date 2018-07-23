package kr.co.daou.sdev.altong.domain.project;

import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.exception.AltongException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 프로젝트 (발송 그룹)
 *
 * <p>도메인 요구사항
 * <ol>
 *   <li>모든 값 필수
 *   <li>sendType는 SMS 불가
 *   <li>resendType는 sendType와 같을 수 없음
 *   <li>regUserId는 운영자가 삭제될 수도 있으므로 관계 설정 안함
 *   <li>smartPhoneType, pushNo는 반드시 한쌍으로 있어야 함
 * </ol>
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "at_project", indexes = {
		@Index(unique = true, name = "ux1_project_project_id", columnList = "projectId")
})
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer projectNo;

	@Column(nullable = false, length = 100)
	private String projectId;

	@Column(nullable = false, length = 100)
	private String projectName;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private Boolean projectStatus = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id", nullable = false, foreignKey = @ForeignKey(name = "fk_project_service_id"))
	private DaouService daouService;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private SendType sendType;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private SendType resendType;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private Boolean sendLimitYn;

	@Column(nullable = false, length = 20)
	private String regUserId;

	@Column(nullable = false)
	private LocalDateTime regDatetime = LocalDateTime.now();

	@Column(nullable = false)
	private LocalDateTime modDatetime = LocalDateTime.now();

	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "at_project_member",
			joinColumns = @JoinColumn(name = "project_no"),
			inverseJoinColumns = @JoinColumn(name = "member_no"),
			foreignKey = @ForeignKey(name = "fk_project_member_project_no"),
			inverseForeignKey = @ForeignKey(name = "fk_project_member_member_no")
	)
	private Set<Member> members = new HashSet<>();

	public Project(DaouService daouService, String projectId, String projectName, SendType sendType, SendType resendType, Boolean sendLimitYn, String regUserId) {
		this.daouService = daouService;
		this.projectId = projectId;
		this.changeProjectName(projectName);
		this.changeSendType(sendType);
		this.changeResendType(resendType);
		this.changeSendLimitYn(sendLimitYn);
		this.changeProjectStatus(Boolean.FALSE);
		this.regUserId = regUserId;
	}

	public void addMember(Member member) {
		if (member == null) {
			return;
		}
		members.add(member);
		member.getProjects().add(this);
	}

	public Integer getMemberCount() {
		if (members == null) {
			return 0;
		}
		return members.size();
	}

	public void changeProjectName(String projectName) {
		if (StringUtils.isBlank(projectName)) {
			throw new AltongException.InvalidArgumentException("프로젝트명은 필수 값입니다.", "projectName");
		}
		this.projectName = projectName;
	}

	public void changeSendType(SendType sendType) {
		if (sendType == null) {
			throw new AltongException.InvalidArgumentException("1차 발송 타입은 필수 값입니다.", "sendType");
		}

		if (sendType == SendType.SMS) {
			throw new AltongException.InvalidArgumentException("1차 발송 타입은 SMS/LMS가 될수 없습니다.", "sendType");
		}
		this.sendType = sendType;
	}

	public void changeResendType(SendType resendType) {
		if (resendType == null) {
			throw new AltongException.InvalidArgumentException("2차 발송 타입은 필수 값입니다.", "resendType");
		}

		if (resendType == this.sendType) {
			throw new AltongException.InvalidArgumentException("2차 발송 타입은 1차 발송 타입과 같을 수 없습니다.", "resendType");
		}
		this.resendType = resendType;
	}

	public void changeModDatetime() {
		this.modDatetime = LocalDateTime.now();
	}

	public void changeProjectStatus(Boolean projectStatus) {
		if (projectStatus == null) {
			throw new AltongException.InvalidArgumentException("프로젝트 상태 값이 올바르지 않습니다.", "projectStatus");
		}
		this.projectStatus = projectStatus;
	}

	public void changeSendLimitYn(Boolean sendLimitYn) {
		if (sendLimitYn == null) {
			throw new AltongException.InvalidArgumentException("발송 제한 여부 값이 올바르지 않습니다.", "sendLimitYn");
		}
		this.sendLimitYn = sendLimitYn;
	}

	@Override
	public String toString() {
		return "Project{" +
				"projectNo=" + projectNo +
				", projectId='" + projectId + '\'' +
				", projectName='" + projectName + '\'' +
				", projectStatus=" + projectStatus +
				", daouService=" + daouService +
				", sendType=" + sendType +
				", resendType=" + resendType +
				", sendLimitYn=" + sendLimitYn +
				", regUserId='" + regUserId + '\'' +
				", regDatetime=" + regDatetime +
				", modDatetime=" + modDatetime +
				", members=" + members.size() +
				'}';
	}
}
