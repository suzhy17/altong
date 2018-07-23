package kr.co.daou.sdev.altong.domain.alert;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import kr.co.daou.sdev.altong.enumeration.AlertStatusType;
import kr.co.daou.sdev.altong.domain.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "at_alert_master")
public class AlertMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer alertNo;

	@ManyToOne
	@JoinColumn(name = "projectNo", foreignKey = @ForeignKey(name = "fk_alert_master_project_no"))
	private Project project;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private AlertStatusType alertStatus;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String message;

	@Column(nullable = false)
	private LocalDateTime regDatetime = LocalDateTime.now();

	@OneToMany(mappedBy = "alertMaster")
	private List<AlertResult> alertResults;
}
