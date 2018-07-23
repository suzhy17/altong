package kr.co.daou.sdev.altong.domain.alert;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.enumeration.SendType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "at_alert_result")
public class AlertResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long resultNo;

	@ManyToOne
	@JoinColumn(name = "member_no", foreignKey = @ForeignKey(name = "fk_alert_result_member_no"))
	private Member member;

	@ManyToOne
	@JoinColumn(name = "alert_no", foreignKey = @ForeignKey(name = "fk_alert_result_alert_no"))
	private AlertMaster alertMaster;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private SendType sendType;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private Boolean sendStatus = Boolean.TRUE;

	@Column(nullable = false)
	private LocalDateTime sendDatetime = LocalDateTime.now();

}
