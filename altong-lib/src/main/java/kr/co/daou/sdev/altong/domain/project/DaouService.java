package kr.co.daou.sdev.altong.domain.project;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;
import kr.co.daou.sdev.altong.exception.AltongException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "at_service")
public class DaouService {
	@Id
	@Column(nullable = false, length = 20)
	private String serviceId;

	@Column(nullable = false, length = 100)
	private String serviceName;

	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private Boolean serviceStatus = Boolean.FALSE;

	@BatchSize(size = 10)
	@ManyToMany(mappedBy = "services", cascade = CascadeType.MERGE)
	private Set<AdminUser> adminUsers = new HashSet<>();
	
	public DaouService(String serviceId, String serviceName) {
		if (StringUtils.isBlank(serviceId)) {
			throw new AltongException.InvalidArgumentException("서비스 ID는 필수 값입니다.", "serviceId");
		}

		if (StringUtils.isBlank(serviceName)) {
			throw new AltongException.InvalidArgumentException("서비스명은 필수 값입니다.", "serviceName");
		}

		this.serviceId = serviceId;
		this.serviceName = serviceName;
	}

	public void changeServiceName(String serviceName) {
		if (StringUtils.isBlank(serviceName)) {
			throw new AltongException.InvalidArgumentException("서비스명은 필수 값입니다.", "serviceName");
		}
		this.serviceName = serviceName;
	}

	public void changeServiceStatus(Boolean serviceStatus) {
		if (serviceStatus == null) {
			throw new AltongException.InvalidArgumentException("서비스 상태 값이 올바르지 않습니다.", "serviceStatus");
		}
		this.serviceStatus = serviceStatus;
	}
	
	public Integer getAdminUserCount() {
		if (adminUsers == null) {
			return 0;
		}
		return adminUsers.size();
	}
}
