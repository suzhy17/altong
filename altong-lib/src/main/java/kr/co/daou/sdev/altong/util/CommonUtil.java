package kr.co.daou.sdev.altong.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern PHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$");

	/**
	 * 이메일 유효성 체크
	 *
	 * @param email 이메일
	 * @return
	 */
	public static boolean isEmail(final String email) {
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}

	/**
	 * 휴대폰 번호 유효성 체크
	 * @param mobileNo 휴대폰 번호
	 * @return
	 */
	public static boolean isMobileNo(final String mobileNo) {
		Matcher matcher = PHONE_PATTERN.matcher(mobileNo);
		return matcher.matches();
	}

	/**
	 * Unescape
	 *
	 * @param str 문자열
	 * @return
	 */
	public static String unescape(final String str) {
		StringBuilder rtnStr = new StringBuilder();
		char[] arrInp = str.toCharArray();
		int i;
		for (i = 0; i < arrInp.length; i++) {
			if (arrInp[i] == '%') {
				String hex;
				if (arrInp[i + 1] == 'u') { // 유니코드.
					hex = str.substring(i + 2, i + 6);
					i += 5;
				} else { // ascii
					hex = str.substring(i + 1, i + 3);
					i += 2;
				}
				try {
					rtnStr.append(Character.toChars(Integer.parseInt(hex, 16)));
				} catch (NumberFormatException e) {
					rtnStr.append("%");
					i -= (hex.length() > 2 ? 5 : 2);
				}
			} else {
				rtnStr.append(arrInp[i]);
			}
		}

		return rtnStr.toString();
	}

	public static String getDomain(final HttpServletRequest request) {
		return String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
	}


}
