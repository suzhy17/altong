package kr.co.daou.sdev.altong.domain.statistics;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import kr.co.daou.sdev.altong.domain.project.DaouService;
import kr.co.daou.sdev.altong.domain.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "at_statistics_daily", indexes = {
		@Index(unique = true, name = "ux1_statistics_daily", columnList = "statisticsDate,serviceId,projectNo")
})
public class StatisticsDaily {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer statisticsNo;

	@Column(nullable = false, length = 8)
	private String statisticsDate;

	@ManyToOne
	@JoinColumn(name = "serviceId", nullable = false, foreignKey = @ForeignKey(name = "fk_statistics_daily_service_id"))
	private DaouService service;

	@ManyToOne
	@JoinColumn(name = "projectNo", nullable = false, foreignKey = @ForeignKey(name = "fk_statistics_daily_project_no"))
	private Project project;

	@Column(nullable = false)
	private Integer successCount;

	@Column(nullable = false)
	private Integer failCount;

	@OneToMany(mappedBy = "statisticsDaily")
	private List<StatisticsDetail> statisticsDetails;
}
