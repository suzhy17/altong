package kr.co.daou.sdev.altong.send;

import kr.co.daou.sdev.altong.domain.project.UnityMsgContents;

public interface UnityMsg {
	
	/**
	 * <pre>
	 * 처리내용 : 메시지를 바로 발송한다. 
	 * 발송결과를 전달한다.
	 * </pre>
	 * @Method Name : send
	 * @return
	 */
	String send(UnityMsgContents contents);
	
	/**
	 * <pre>
	 * 처리내용 : 연결상태를 확인한다.
	 * </pre>
	 * @Method Name : isConnect
	 * @return
	 */
	boolean isConnect();
	
	/**
	 * <pre>
	 * 처리내용 : 발송메시지를 저장한다.
	 * </pre>
	 * @Method Name : setContents
	 * @param contents
	 */
	void setContents(UnityMsgContents contents);
	
	/**
	 * <pre>
	 * 처리내용 : 발송메시지 객체를 취득한다.
	 * </pre>
	 * @Method Name : getContents
	 * @return
	 */
	UnityMsgContents getContents();
	
	
	/**
	 * <pre>
	 * 처리내용 : 저장된 컨텐츠를 발송한다.
	 * </pre>
	 * @Method Name : send
	 * @return
	 */
	String send();
	
}
