package kr.co.daou.sdev.altong.send.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectInfo {

	private String userId;
	private String password;
	private String locale;
	private String host;
	private int port=0;
	
	public String getUsername() {
		return userId;
	}
}
