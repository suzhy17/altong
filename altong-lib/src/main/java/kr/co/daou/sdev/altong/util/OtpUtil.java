package kr.co.daou.sdev.altong.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

import lombok.extern.slf4j.Slf4j;

/**
 * <code>OtpUtil.java</code>
 * @Description : OTP 인증 유틸
 * @author : redkarisma
 * @modify : 
 * @since 2016. 4. 7.
 * @version 1.0
 */
@Slf4j
public class OtpUtil {

	/**
	 * @Description : OTP Encoded Key 생성
	 * @author : 이창화
	 * @return
	 */
	public static String createEncodedKey() {

		String sEncodedKey = null;

		try{

			byte[] buffer = new byte[5 + 5 * 5];
			new Random().nextBytes(buffer);
			Base32 codec = new Base32();

			//byte[] secretKey = Arrays.copyOf(buffer, 10); // java 1.5에서는 해당 메소드 사용 불가
			byte[] secretKey = new byte[10];
			System.arraycopy(buffer, 0, secretKey, 0, 10);

			byte[] bEncodedKey = codec.encode(secretKey);
			sEncodedKey = new String(bEncodedKey);

		} catch(Exception e) {
			log.error("OTP 계정 생성 실패 : "+e);
		}

		return sEncodedKey;
	}

	/**
	 * @Description : OTP QR코드 이미지 URL 가져오기
	 * @author : 이창화
	 * @param sAdminId
	 * @param sEncodedKey
	 * @param sHostUrl
	 * @return
	 */
	public static String getQRBarcodeURL(String sAdminId, String sEncodedKey, String sHostUrl) {

		String sRtnUrl = null;
		
		try {

			String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://totp/%s@%s?secret=%s";
			sRtnUrl = String.format(format, sAdminId, sHostUrl, sEncodedKey);
			
		} catch(Exception e){ 
			log.error("OTP QR코드 가져오기 실패 : " + e);
		}

		return sRtnUrl;
	}

	/**
	 * @Description : OTP 2차 로그인
	 * @author : 이창화
	 * @param sAdminId
	 * @param sEncodedKey
	 * @param lOtpNumber
	 * @param past
	 * @return
	 */
	public static boolean loginOtp(String sAdminId, String sEncodedKey, long lOtpNumber, int past) {

		long l = new Date().getTime();
		long ll = l / 30000;

		boolean bOtpSucc = false;

		try {

			if(sEncodedKey != null){
				bOtpSucc = chkOtpCode(sEncodedKey, lOtpNumber, ll, past);
			}

		} catch (Exception e) {
			log.error("OTP EncodedKey 가져오기 실패  : ", e);
		}

		return bOtpSucc;
	}

	/**
	 * @Description : OTP 체크 알고리즘
	 * @author : 이창화
	 * @param sEncodedKey
	 * @param lOtpNumber
	 * @param t
	 * @param past
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	private static boolean chkOtpCode(String sEncodedKey, long lOtpNumber, long t, int past) throws NoSuchAlgorithmException, InvalidKeyException {

		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(sEncodedKey);

		for (int i = -past; i <= past; ++i) {
			long hash = verifyCode(decodedKey, t + i);

			if (hash == lOtpNumber) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Description : OTP 코드 검증
	 * @author : 이창화
	 * @param key
	 * @param t
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	private static int verifyCode(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {

		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}

		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);

		int offset = hash[20 - 1] & 0xF;

		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			truncatedHash |= (hash[offset + i] & 0xFF);
		}

		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;

		return (int) truncatedHash;
	}
}
