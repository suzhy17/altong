package kr.co.daou.sdev.altong.domain.statistics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kr.co.daou.sdev.altong.enumeration.SendType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "at_statistics_detail", indexes = {
		@Index(unique = true, name = "ux1_statistics_detail", columnList = "statisticsNo,sendType")
})
public class StatisticsDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer statisticsDetailNo;

	@ManyToOne
	@JoinColumn(name = "statisticsNo", nullable = false, foreignKey = @ForeignKey(name = "fk_statistics_detail_statistics_no"))
	private StatisticsDaily statisticsDaily;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private SendType sendType;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private Boolean sendStatus = Boolean.TRUE;

	@Column(nullable = false)
	private Integer successCount;

	@Column(nullable = false)
	private Integer failCount;

}
