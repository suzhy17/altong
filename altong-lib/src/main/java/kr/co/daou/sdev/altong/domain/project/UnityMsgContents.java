package kr.co.daou.sdev.altong.domain.project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UnityMsgContents {

	private String body;
	private String level;
	private String projectName;
	private String senndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	private List<String> targets = new ArrayList<>();
	
	public void addTarget(String target) {
		targets.add(target);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[FROM]: ").append("통합알림 시스템").append("\n")
		  .append("[LEVEL]: ").append(level).append("\n")
		  .append("[TITLE]: ").append(projectName).append("\n")
		  .append("[DATE]: ").append(senndTime).append("\n")
		  .append("[MESSAGE]: ").append(body).append("\n");
		return sb.toString();
	}
}
