package kr.co.daou.sdev.altong.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

	public static String getTempPassword() {
		return RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomNumeric(9);
	}

	/**
	 * 반복적인 패스워드인지 체크
	 *
	 * @param password
	 * @return true : 적합, false : 부적합
	 */
	public static boolean isStrengthPassword(final String password) {
		int limit = 2;
		int reptCnt = 0;
		int consCnt = 0;

		char[] passwordChars = password.toCharArray();

		for (int i = 0; i < passwordChars.length; i++) {

			if (passwordChars.length == i + 1) {
				break;
			}

			int currChar = passwordChars[i];
			int nextChar = passwordChars[i + 1];

			// 중복 문자 확인
			if (reptCnt < limit) {
				if (currChar == nextChar) {
					reptCnt++;
				} else {
					reptCnt = 0;
				}
			}

			// 연속문자 확인
			if (consCnt < limit) {
				if (currChar + 1 == nextChar) {
					consCnt++;
				} else {
					consCnt = 0;
				}
			}
		}

		return reptCnt != limit && consCnt != limit;
	}

	/**
	 * 패스워드 복잡도 체크 (숫자,영문자 조합 10자리 이상)
	 *
	 * @param password
	 * @return true : 적합, false : 부적합
	 */
	public static boolean isComplexPassword(final String password) {
		if (StringUtils.isBlank(password)) {
			return false;
		}

		// 숫자,영문자 조합 10자리 이상
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{10,20}$");
		Matcher match = pattern.matcher(password);

		return match.find();
	}

}
