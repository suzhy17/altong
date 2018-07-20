+function ($) {
	'use strict';

	/**
	 * 리스트에서 상태 변경 버튼을 play-pause 로 변동 
	 */
	$.fn.playToggle = function() {
		var $buttons = $(this);
		$buttons.each(function () {
			var $button = $(this),
				status = $button.data('status');

			$button.addClass(status ? 'btn-success' : 'btn-danger');

			var icon = '<i class="' + (status ? 'fa fa-play' : 'fa fa-pause') + '"></i>';
			$button.html(icon);
		});
		
		$buttons.bind({
			mouseover: function () {
				var $button = $(this),
					$icon = $button.find('i.fa'),
					status = $button.data('status');
				$button.removeClass('btn-success').removeClass('btn-danger');
				$icon.removeClass('fa-play').removeClass('fa-pause');
				if (status) {
					$button.addClass('btn-danger');
					$icon.addClass('fa-pause');
				} else {
					$button.addClass('btn-success');
					$icon.addClass('fa-play');
				}
			},
			mouseleave: function () {
				var $button = $(this),
					$icon = $button.find('i.fa'),
					status = $button.data('status');
				$button.removeClass('btn-success').removeClass('btn-danger');
				$icon.removeClass('fa-play').removeClass('fa-pause');
				if (!status) {
					$button.addClass('btn-danger');
					$icon.addClass('fa-pause');
				} else {
					$button.addClass('btn-success');
					$icon.addClass('fa-play');
				}
			}
		});
	};

	// Play 토글버튼 활성
	$('[data-toggle="play-btn"]').playToggle();

	// Bootstrap 툴팁 활성
	$('[data-toggle="tooltip"]').tooltip();

}(jQuery);
