;(function() {
	"use strict";

	// ajax fail 공통 처리
	$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
		alert(jqxhr.responseJSON.resMsg);
		//console.log(jqxhr);
	});

	// jQueryUI datepicker 달력
	$('.ui-datepicker-trigger').datepicker({
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd', //형식(2012-03-03)
		autoSize: true, //오토리사이즈(body등 상위태그의 설정에 따른다)
		changeMonth: true, //월변경가능
		changeYear: true, //년변경가능
		showMonthAfterYear: true, //년 뒤에 월 표시
		buttonImageOnly: false, //이미지표시
		buttonText: '달력선택', //버튼 텍스트 표시
		buttonImage: '', //이미지주소
		showOn: 'focus', //엘리먼트와 이미지 동시 사용(both,button)
		showAnim: 'show', // blind, clip, drop, explode, fold, puff, slide, scale, size, pulsate, bounce, highlight, shake, transfer
		yearRange: '2009:2020' //1900년부터 2020년까지
	});

	// jQueryUI datepicker 모달용 달력 (모달에선는 셀렉트박스로 년,월 선택시 창이 닫히는 문제가 있음)
	$('.ui-datepicker-trigger-modal').datepicker({
		monthNames: [ '1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd', //형식(2012-03-03)
		autoSize: true, //오토리사이즈(body등 상위태그의 설정에 따른다)
		showMonthAfterYear: true, //년 뒤에 월 표시
		buttonImageOnly: false, //이미지표시
		buttonText: '달력선택', //버튼 텍스트 표시
		buttonImage: '', //이미지주소
		showOn: 'focus', //엘리먼트와 이미지 동시 사용(both,button)
		showAnim: 'show', // blind, clip, drop, explode, fold, puff, slide, scale, size, pulsate, bounce, highlight, shake, transfer
		yearRange: '2009:2020' //1900년부터 2020년까지
	});
	

	// BlockUI 기본 디자인 설정
//	$.blockUI.defaults.css = {
//			padding: '15px', 
//	        margin: 0, 
//	        width: '30%', 
//	        top: '40%', 
//	        left: '35%', 
//	        textAlign: 'center', 
//	        color: '#fff', 
//	        border: 'none', 
//	        backgroundColor: '#000', 
//	        cursor: 'wait',
//			'-webkit-border-radius': '10px',
//			'-moz-border-radius': '10px',
//			opacity: .5
//		};


})();