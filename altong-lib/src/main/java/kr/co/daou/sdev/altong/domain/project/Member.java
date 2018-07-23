package kr.co.daou.sdev.altong.domain.project;

import kr.co.daou.sdev.altong.enumeration.SendType;
import kr.co.daou.sdev.altong.exception.AltongException;
import kr.co.daou.sdev.altong.util.CommonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 멤버 (알림 수신 대상)
 * 
 * <p>도메인 요구사항
 * <ol>
 *   <li>staffNo, memberName은 필수
 *   <li>email, mobileNo, smartPhone 중 하나는 반드시 있어야 함
 *   <li>email 존재시 유효성 검증
 *   <li>memberNo는 변경, 수동입력 불가
 * </ol>
 * @author suzhy
 * @since 2017.11.16
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "at_member", indexes = {
		@Index(unique = true, name = "ux1_member_staff_no", columnList = "staffNo")
})
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer memberNo;

	@Column(nullable = false)
	private Integer staffNo;

	@Column(length = 50, nullable = false)
	private String memberName;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private Boolean memberStatus = Boolean.TRUE;

	@Column(length = 100)
	private String email;

	@Embedded
	private SmartPhone smartPhone;

	@Column(length = 12)
	private String mobileNo;

	@Column(length = 300)
	private String remarks;
	
	@ManyToMany(mappedBy = "members", cascade = CascadeType.MERGE)
	private Set<Project> projects = new HashSet<>();

	public Member(Integer staffNo, String memberName, String email, SmartPhone smartPhone, String mobileNo, String remarks) {
		if (staffNo == null) {
			throw new AltongException.InvalidArgumentException("사번은 필수값입니다.", "staffNo");
		}

		if (StringUtils.isBlank(memberName)) {
			throw new AltongException.InvalidArgumentException("이름은 필수값입니다.", "memberName");
		}
		
		validateDevice();
				
		this.staffNo = staffNo;
		this.memberName = memberName;
		this.email = email;
		this.smartPhone = smartPhone;
		this.mobileNo = mobileNo;
		this.remarks = remarks;
		this.memberStatus = Boolean.TRUE;
	}

	public Member(Integer memberNo, Integer staffNo, String memberName, String email, SmartPhone smartPhone, String mobileNo, String remarks) {
		if (staffNo == null) {
			throw new AltongException.InvalidArgumentException("사번은 필수값입니다.", "staffNo");
		}

		if (StringUtils.isBlank(memberName)) {
			throw new AltongException.InvalidArgumentException("이름은 필수값입니다.", "memberName");
		}

		validateDevice();

		this.memberNo = memberNo;
		this.staffNo = staffNo;
		this.memberName = memberName;
		this.email = email;
		this.smartPhone = smartPhone;
		this.mobileNo = mobileNo;
		this.remarks = remarks;
		this.memberStatus = Boolean.TRUE;
	}
	
	public void addProject(Project project) {
		if (project == null) {
			return;
		}
		projects.add(project);
		project.getMembers().add(this);
	}

	public void changeMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void changeEmail(String email) {
		if (StringUtils.isBlank(email)) {
			validateDevice();
		}
		
		if (!CommonUtil.isEmail(email)) {
			throw new AltongException.InvalidArgumentException("이메일 형식이 올바르지 않습니다.", "email");
		}
		
		this.email = email;
	}

	public void changeStaffNo(Integer staffNo) {
		this.staffNo = staffNo;
	}

	public void changeMobileNo(String mobileNo) {
		if (!CommonUtil.isMobileNo(mobileNo)) {
			throw new AltongException.InvalidArgumentException("휴대폰번호 형식이 올바르지 않습니다.", "mobileNo");
		}
		this.mobileNo = mobileNo;
	}
	
	public void changeSmartPhone(SmartPhone smartPhone) {
		this.smartPhone = smartPhone;
	}

	public void changeRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void changeMemberStatus(Boolean memberStatus) {
		this.memberStatus = memberStatus;
	}
	
	private void validateDevice() {
		if (StringUtils.isBlank(email) && StringUtils.isBlank(mobileNo) && smartPhone == null) {
			throw new AltongException.InvalidArgumentException("휴대폰번호, 이메일, 스마트폰 중 반드시 하나는 있어야 합니다.");
		}
	}

	/**
	 * 모든 프로젝트에서 빠진다. (멤버 삭제시 사용)
	 */
	public void removeAllProject() {
		for (Project project : projects) {
			project.getMembers().remove(this);
		}
		this.projects = null;
	}

	/**
	 * 발송타입으로 대상 키를 문자열로 get
	 * @param sendType
	 * @return 푸시키/휴대폰번호/이메일/사번 등
	 */
	public String getTarget(SendType sendType) {
		switch (sendType) {
			case PUSH:
				return this.getSmartPhone().getPushNo();
			case SMS:
				break;
			case EMAIL:
				break;
			case DAOU_OFFICE:
				return String.valueOf(this.getStaffNo());
		}

		return null;
	}

	@Override
	public String toString() {
		return "Member{" +
				"memberNo=" + memberNo +
				", staffNo=" + staffNo +
				", memberName='" + memberName + '\'' +
				", memberStatus=" + memberStatus +
				", email='" + email + '\'' +
				", mobileNo='" + mobileNo + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}
