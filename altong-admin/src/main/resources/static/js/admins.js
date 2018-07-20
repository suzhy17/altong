$(document).ready(function() {
	"use strict";

	$('button[name="passwordReset"]').click(function () {
		var userId = $(this).val();

		if (!confirm(userId + '님 계정 비밀번호를 임시 비밀번호로 변경하겠습니까?')) {
			return;
		}

		$.ajax({
			method: 'PUT',
			url: '/admins/' + userId + '/temp-password'
		}).done(function () {
			alert("변경되었습니다.");
			location.reload();
		});
	});


	$('button[name="delete"]').click(function() {
		var userid = $(this).val();

		if (!confirm(userid + '님 계정을 삭제하겠습니까?')) {
			return;
		}

		$.ajax({
			method: 'DELETE',
			url: '/admins/'+userid
		}).done(function() {
			alert("삭제되었습니다.");
			location.reload();
		});
	});

	$('button[name="otpReset"]').click(function() {
		var userid = $(this).val();

		if (!confirm(userid + '님 계정의 OTP 코드를 초기화하겠습니까?')) {
			return;
		}

		$.ajax({
			method: 'PUT',
			url: '/admins/' + userid + '/otp-reset'
		}).done(function() {
			alert("초기화되었습니다.");
			location.reload();
		});
	});
});