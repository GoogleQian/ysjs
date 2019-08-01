package com.he.water.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data, String appSecret) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(MD5Util.MD5Encode(appSecret, "UTF-8").toLowerCase().getBytes(), ALGORITHM));
        return new String(Base64.getEncoder().encode(cipher.doFinal(data.getBytes())));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data, String appSecret) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(MD5Util.MD5Encode(appSecret, "UTF-8").toLowerCase().getBytes(), ALGORITHM));
        return new String(cipher.doFinal(Base64.getDecoder().decode(base64Data)));
    }

    public static void main(String[] args) throws Exception {
        //String A 为测试字符串，是微信返回过来的退款通知字符串
//        String A = "eoX7PwG3ZLBjp8HaYelNblufSwMjriS4HH9IA0C2EMd3WQcFlHcYwgTALB8j/IhfU0Snu8U737PbTFZcdBXH5/lhT/TS6zn3OFugIPA6lyfpGf2aMO1DeO63cOGs/l0E/Qgz1/ZM5FGT0CKTAzYCKMUfBviprzMthH4f4Bgbcxc00Ysmw39m61LH2mzrqDxbPhPiApRm44CQLHunM+eB1BSDrVenK1VRobAs5lKh3TbGuIi9iKoHjmkMIYYeGSTDl7oI126/zLN2EhKItKWPV+ZBKfkNO/fxe6Q8XjUpbB9fJuQQXUSnecFRJkGe95V9GBoYYnoUDtpwNzooptyxLbmHxB/1j3qqip47VDuBgcopvA0V1z9W7QGnuQ5/etelo6zqXvRfBtBWelWTHEqczFFauLfmsjnvxtuOO2ruJ7cpiMtF17rbm5LcLE63Gw3xBds1e6ym4Iel5yWUKl7p3Cgm3hogGBqNWhmih4+oTuDKegO1IyhDO44fb9ZREMkfivuvPO+k4Fgka1FuJ5N1Cy9FrNrLcRhsqy08qKUfwKEVAebH1Kca9bsCb4dL1q62gRMEcD3CNGliTKohLTR7NXzLnF8sP96ZTXNSkGnlMCNojSYx/AF+NqjcoCkpEHWqctkWKH95UtbcDNqjbQOyXnI9gqH161JIKlUEzaknPA8CG+5P0rHNdgHVDFAy3rNVohssUykzJ/imNNaZBdL7zsoEbsYaP8iGGdr8QwfCv3Q8tDw5+VDu/wixtRvRfIp9JIWTMYEw1kEb0GMMNbdA82tQEnKeXA8JimgDbtEZI2mths6dIIcplNMt3S3eUJu9mKTaAyBWRbtv/js+p7TleTHsLtSlTEUa/B16aec5TEsfquORlZXRSZGKUx8IgQCGMK3T/06LFUcOudgKbWzpzp0FzEWVFljF2ecCFCyqxHb+CzSQw1W9VYrSsmcPiRLpp7wbXnc8SosacMeyVvW7nsAqp3eYkHZsYdopWbKUYh1R5URc96EWZA1o7DNE/+4PJOZyFHoAwe+MOy0DcmR0f5DmMf7yoGQZ2lQoUC1EK6Q=";
//        String B = new String(AESUtil.decryptData(A));
//        System.out.println(B);
    }

}