package kr.co.daou.sdev.altong.exception;

public class AltongException extends RuntimeException {

	protected String field;

	public String getField() {
		return field;
	}


	public AltongException(String message) {
		super(message);
	}

	public static class MemberNotFoundException extends AltongException {
		private static final String MESSAGE = "기존 멤버가 존재하지 않습니다.";
		public MemberNotFoundException() {
			super(MESSAGE);
		}
	}

	public static class ProjectNotFoundException extends AltongException {
		private static final String MESSAGE = "프로젝트가 존재하지 않습니다.";
		public ProjectNotFoundException() {
			super(MESSAGE);
		}
	}

	public static class ProjectMemberNotFoundException extends AltongException {
		private static final String MESSAGE = "프로젝트에 등록된 멤버가 존재하지 않습니다.";
		public ProjectMemberNotFoundException() {
			super(MESSAGE);
		}
	}

	public static class ProjectMemberAlreadyExistsException extends AltongException {
		private static final String MESSAGE = "프로젝트에 이미 등록된 멤버입니다.";
		public ProjectMemberAlreadyExistsException() {
			super(MESSAGE);
		}
	}

	public static class ProjectExistException extends AltongException {
		private static final String MESSAGE = "하위 프로젝트가 존재하기 때문에 삭제가 불가능합니다.";
		public ProjectExistException() {
			super(MESSAGE);
		}
	}
	
	public static class AlertMasterNotFoundException extends AltongException {
		private static final String MESSAGE = "발송 마스터가 존재하지 않습니다.";
		public AlertMasterNotFoundException() {
			super(MESSAGE);
		}
	}

	public static class UnauthorizedException extends AltongException {
		private static final String MESSAGE = "권한이 없습니다.";
		public UnauthorizedException() {
			super(MESSAGE);
		}
	}

	public static class InvalidArgumentException extends AltongException {
		public InvalidArgumentException(String message) {
			super(message);
			super.field = field;
		}

		public InvalidArgumentException(String message, String field) {
			super(message);
			super.field = field;
		}
	}

}
