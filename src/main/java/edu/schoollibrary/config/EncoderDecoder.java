package edu.schoollibrary.config;

import edu.schoollibrary.service.NumberCodec;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.util.Base64;

@Component
public class EncoderDecoder implements NumberCodec {

  private static final String SECRET_KEY = "MySecretKey12345";
  private Cipher encryptCipher;
  private Cipher decryptCipher;

  @PostConstruct
  public void init() throws Exception {
    SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
    encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    encryptCipher.init(Cipher.ENCRYPT_MODE, key);
    decryptCipher.init(Cipher.DECRYPT_MODE, key);
  }

  @Override
  public String encode(long number) {
    try {
      byte[] numberBytes = ByteBuffer.allocate(Long.BYTES).putLong(number).array();
      byte[] encrypted = encryptCipher.doFinal(numberBytes);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(encrypted);
    } catch (Exception e) {
      throw new RuntimeException("Failed to encode number", e);
    }
  }

  @Override
  public long decode(String encoded) {
    try {
      byte[] encrypted = Base64.getUrlDecoder().decode(encoded);
      byte[] decrypted = decryptCipher.doFinal(encrypted);
      ByteBuffer buffer = ByteBuffer.wrap(decrypted);
      return buffer.getLong();
    } catch (Exception e) {
      throw new RuntimeException("Failed to decode string", e);
    }
  }
}
