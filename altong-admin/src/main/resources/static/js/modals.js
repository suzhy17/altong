/**
 * 패스워드 변경 모달
 */
var passwordModalModule = (function () {
	var $modals = $('#modals');
	var settings = {
		buttons: {
			$modalOpen: $('#passwordMod')
		}
	};

	var bind = function () {
		settings.buttons.$modalOpen.click(function (event) {
			event.preventDefault();

			$('#modals').load('/admins/password #passwordFormModal', function () {
				$('#passwordFormModal').modal('show');
			});
		});

		$modals.on('submit', '#passwordForm', function () {
			var newPassword = $('#newPassword').val(),
				newPasswordRe = $('#newPasswordRe').val();

			if (newPassword !== newPasswordRe) {
				alert('새 비밀번호가 일치하지 않습니다.');
				return false;
			}

			$.ajax({
				method: 'PUT',
				url: '/admins/password',
				data: $(this).serialize()
			}).done(function () {
				alert('비밀번호가 변경되었습니다. 다시 로그인 해주세요.');
				location.href = '/login';
			});
			return false;
		});
	};

	return {
		init: function () {
			bind();
		}
	};
})();

/**
 * 어드민 유저 등록/변경 모달
 */
var userInfoModalModule = (function () {
	var $modals = $('#modals');
	var settings = {
		buttons: {
			$adminAddModalOpen: $('#adminAdd'),
			$adminModModalOpen: $('button[name="adminMod"]'),
			$userInfoModModalOpen: $('#userInfoMod')
		}
	};

	var bind = function () {
		settings.buttons.$adminAddModalOpen.click(function (event) {
			event.preventDefault();

			$('#modals').load('/admins?write #adminFormModal', function () {
				$('#adminFormModal').modal('show');
			});
		});

		settings.buttons.$adminModModalOpen.click(function () {
			var userId = $(this).val();
			$modals.load('/admins/' + userId + ' #adminFormModal', function () {
				$('#adminFormModal').modal('show');
			});
		});

		settings.buttons.$userInfoModModalOpen.click(function (event) {
			event.preventDefault();

			$modals.load('/admins/user-info #adminFormModal', function () {
				$('#adminFormModal').modal('show');
			});
		});

		$modals.on('submit', '#adminForm', function () {
			var modifyYn = $('#modifyYn').val();

			// 생성
			if (modifyYn === 'N') {
				$.ajax({
					method: 'POST',
					url: '/admins',
					data: $(this).serialize()
				}).done(function () {
					alert('생성되었습니다.');
					$('#adminFormModal').modal('hide');
					location.reload();
				});
			}
			// 수정
			else {
				$.ajax({
					method: 'PUT',
					url: '/admins/' + $('#userId').val(),
					data: {
						mobileNo: $('#mobileNo').val(),
						email: $('#email').val()
					}
				}).done(function () {
					alert('변경되었습니다.');
					$('#adminFormModal').modal('hide');
				});
			}

			return false;
		});
	};

	return {
		init: function () {
			bind();
		}
	};
})();

/**
 * 프로젝트 등록/변경 모달
 */
var projectModalModule = (function () {
	var $modals = $('#modals');
	var settings = {
		buttons: {
			$projectAddModalOpen: $('#projectAdd'),
			$projectModModalOpen: $('button[name="projectMod"]')
		}
	};

	var bind = function () {

		settings.buttons.$projectAddModalOpen.click(function () {
			$modals.load('/projects?write #projectFormModal', function () {
				$('#projectFormModal').modal('show');
			});
		});

		settings.buttons.$projectModModalOpen.click(function () {
			var projectNo = $(this).val();
			$modals.load('/projects/' + projectNo + '?modify #projectFormModal', function () {
				$('#projectFormModal').modal('show');
			});
		});

		$modals.on('submit', '#projectForm', function () {
			var modifyYn = $('#modifyYn').val();

			// 생성
			if (modifyYn === 'N') {
				$.ajax({
					method: 'POST',
					url: '/projects',
					data: $(this).serialize()
				}).done(function () {
					alert('생성되었습니다.');
					location.reload();
				});
			}
			// 수정
			else {
				$.ajax({
					method: 'PUT',
					url: '/projects/' + $('#projectNo').val(),
					data: $(this).serialize()
				}).done(function () {
					alert('변경되었습니다.');
					location.reload();
				});
			}

			return false;
		});
	};

	return {
		init: function () {
			bind();
		}
	};
})();

/**
 * 멤버 등록/변경 모달
 */
var memberModalModule = (function () {
	var $modals = $('#modals');
	var settings = {
		buttons: {
			$memberAddModalOpen: $('#memberAdd'),
			$memberModModalOpen: $('button[name="memberMod"]')
		},
		inputs: {
			$smartPhoneType: $('input[name="smartPhone.smartPhoneType"]'),
			$pushNo: $('input[name="smartPhone.pushNo"]')
		}
	};

	var formControl = function () {
		$modals.on('change', 'input[name="smartPhone.smartPhoneType"]', function () {
			var smartPhoneType = $(this).val(),
				$pushNo = $('input[name="smartPhone.pushNo"]');
			if (smartPhoneType === 'ANDROID' || smartPhoneType === 'IPHONE') {
				$pushNo.prop("disabled", false);
			} else {
				$pushNo.prop("disabled", true);
			}
		});
	};

	var bind = function () {

		settings.buttons.$memberAddModalOpen.click(function () {
			$modals.load('/members?write #memberModal', function () {
				$('#memberModal').modal('show');
			});
		});

		settings.buttons.$memberModModalOpen.click(function () {
			var memberNo = $(this).val();
			$modals.load('/members/' + memberNo + '?modify #memberModal', function () {
				$('#memberModal').modal('show');
			});
		});

		$modals.on('submit', '#memberForm', function () {
			var modifyYn = $('#modifyYn').val();

			// 생성
			if (modifyYn === 'N') {
				$.ajax({
					method: 'POST',
					url: '/members',
					data: $(this).serialize()
				}).done(function () {
					alert('생성되었습니다.');
					location.reload();
				});
			}
			// 수정
			else {
				$.ajax({
					method: 'PUT',
					url: '/members?modify',
					data: $(this).serialize()
				}).done(function () {
					alert('변경되었습니다.');
					location.reload();
				});
			}

			return false;
		});
	};

	return {
		init: function () {
			formControl();
			bind();
		}
	};
})();

/**
 * 프로젝트-멤버 선택 모달
 */
var projectMemberModalModule = (function () {
	var $modals = $('#modals');
	var settings = {
		buttons: {
			$projectMemberModalOpen: $('#projectMemberModalOpen')
		}
	};

	var bind = function () {
		settings.buttons.$projectMemberModalOpen.click(function () {
			var projectNo = $(this).val();
			// \u0026 == &amp;
			$modals.load('/members?projectNo=' + projectNo + '\u0026modal #membersModal', function () {
				$('#membersModal').modal('show');
			});
		});

		$modals.on('click', '#closeMembersModal', function () {
			location.reload();
		});

		$modals.on('click', 'button[name="projectMemberAdd"]', function () {
			var projectNo = $('#projectNo').val(),
				memberNo = $(this).data('memberNo');

			$.ajax({
				method: 'POST',
				url: '/projects/' + projectNo + '/members/' + memberNo,
				data: {
					projectNo: projectNo,
					memberNo: memberNo
				}
			}).done(function () {
				alert('추가되었습니다.');
//				location.reload();
			});

			return false;
		});
	};

	return {
		init: function () {
			bind();
		}
	};
})();


/**
 * 운영자-서비스 선택 모달
 */
var adminServicesModalModule = (function () {
	var $modals = $('#modals');
	var settings = {
		buttons: {
			$adminServicesModalOpen: $('button[name="adminServicesModalOpen"]')
		}
	};

	var bind = function () {
		settings.buttons.$adminServicesModalOpen.click(function () {
			var userId = $(this).val();
			$modals.load('/admins/' + userId + '/services #adminServicesModal', function () {
				$('#adminServicesModal').modal('show');

				// 카테고리 설정 드래그 앤 드롭 영역 설정
				$('#sortable-used, #sortable-unused').sortable({
					items: 'li:not(.ui-state-disabled)',
					connectWith: '.connectedSortable'
				}).disableSelection();
			});
		});

		$modals.on('click', '#closeAdminServicesModal', function () {
			location.reload();
		});

		$modals.on('click', '#adminServicesMod', function () {
			if (!confirm('설정을 저장하시겠습니까?')) {
				return;
			}

			var $usedList = $('#sortable-used li:not(.ui-state-disabled)'),
				userId = $(this).val();

			var serviceIds = [];
			$usedList.each(function (index) {
				serviceIds[index] = $(this).data('serviceId');
			});

			$.ajax({
				method: 'PUT',
				url: '/admins/' + userId + '/services',
				async: false,
				data: {
					serviceIds: serviceIds
				}
			}).done(function (data) {
				alert('변경된 내용이 저장되었습니다.');
				location.reload();
			});
		});
	};

	return {
		init: function () {
			bind();
		}
	};
})();

/**
 * 프로젝트별 알림 발송
 */
var projectAlertModalModule = (function () {
	var $modals = $('#modals');
	var settings = {
		buttons: {
			$modalOpen: $('#alertModalOpen')
		}
	};

	var bind = function () {
		settings.buttons.$modalOpen.click(function (event) {
			event.preventDefault();

			var projectNo = $(this).val();

			$('#modals').load('/alerts/send?projectNo=' + projectNo + ' #alertFormModal', function () {
				$('#alertFormModal').modal('show');
			});
		});

		$modals.on('submit', '#alertForm', function () {
			if (!confirm('프로젝트 멤버에게 테스트 메세지를 발송하시겠습니까?')) {
				return false;
			}

			$.ajax({
				method: 'POST',
				url: '/alerts/send',
				data: $(this).serialize()
			}).done(function () {
				alert("발송되었습니다.");
				location.reload();
			});

			return false;
		});
	};

	return {
		init: function () {
			bind();
		}
	};
})();

/**
 * 멤버별 알림 발송
 */
var memberAlertModalModule = (function () {
	var $modals = $('#modals');
	var settings = {
		buttons: {
			$modalOpen: $('button[name="memberAlertModalOpen"]')
		}
	};

	var bind = function () {
		settings.buttons.$modalOpen.click(function (event) {
			event.preventDefault();

			var memberNo = $(this).val();

			$('#modals').load('/alerts/send/' + memberNo + ' #memberAlertFormModal', function () {
				$('#memberAlertFormModal').modal('show');
			});
		});

		$modals.on('submit', '#memberAlertForm', function () {
			if (!confirm('해당 멤버에게 테스트 메세지를 발송하시겠습니까?')) {
				return false;
			}

			var memberNo = $('input[name="memberNo"]').val();

			$.ajax({
				method: 'POST',
				url: '/alerts/send/' + memberNo,
				data: $(this).serialize()
			}).done(function () {
				alert("발송되었습니다.");
				location.reload();
			});

			return false;
		});
	};

	return {
		init: function () {
			bind();
		}
	};
})();

$(document).ready(function () {
	passwordModalModule.init();
	userInfoModalModule.init();
	projectModalModule.init();
	memberModalModule.init();
	projectMemberModalModule.init();
	adminServicesModalModule.init();
	projectAlertModalModule.init();
	memberAlertModalModule.init();
});
