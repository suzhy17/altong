package kr.co.daou.sdev.altong.send;

import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;
import kr.co.daou.sdev.altong.send.model.ConnectInfo;

abstract class AbstractUnityMsg implements UnityMsg {

	// 다우오피스에서 사용하는 세션정보
	public static String goSessionCookieValue = null;

	// 접속정보
	protected ConnectInfo connectInfo;
	
	// 컨텐츠
	protected UnityMsgContents contents;
	
	@Override
	public String send() {
		return send(contents);
	}
}
