package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.entity.common.ITokenToValidate;
import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.entity.cryptographic.ICryptography.ICryptographySaveResult;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.CRYPTO_ITERATION_COUNT;
import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.CRYPTO_KEY_LENGTH;

@Service
@PropertySource({"classpath:application.properties"})
public class CryptographiceServiceImpl implements ICryptographicService, ICryptoService {
  private final PasswordEncoder passwordEncoder;
  @Value("${crypto.algorithm}")
  private String cryptoAlgo;
  @Value("${crypto.key}")
  private String cryptoKey;
  @Value("${crypto.salt}")
  private String cryptoSalt;
  @Value("${crypto.name}")
  private String cryptoName;
  @Value("${crypto.secret.key.algo}")
  private String cryptoSecretkeyAlgo;


  public CryptographiceServiceImpl(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public ICryptographySaveResult cryptographyTextAndSave(CryptographicType cryptographicType, String plainText, LocalDateTime limitValidity) {
    return null;
  }

  @Override
  public boolean isHashValid(String plainText, String hash) {
    return passwordEncoder.matches(plainText, hash);
  }

  @Override
  public Boolean isHashValid(ITokenToValidate tokenToValidate) {
    return null;
  }

  @Override
  public String hashText(String textToHash) {
    return passwordEncoder.encode(textToHash);
  }

  @Override
  public String encrypt(String text, byte[] iv) {
    try {
      Cipher cipher = Cipher.getInstance(this.cryptoAlgo);
      SecretKey key = this.generateSecretKey();
      cipher.init(Cipher.ENCRYPT_MODE,key, this.generateParameterSpecIv(iv));

      byte[] cipherText = cipher.doFinal(text.getBytes());

      return Base64.getUrlEncoder().encodeToString(cipherText);

    } catch (IllegalBlockSizeException exception) {
      return null;

    } catch (BadPaddingException exception) {
      return null;

    } catch (NoSuchPaddingException exception) {
      return null;

    } catch (NoSuchAlgorithmException exception) {
      return null;

    } catch (InvalidKeySpecException exception) {
      return null;

    } catch (InvalidAlgorithmParameterException exception) {
      return null;

    } catch (InvalidKeyException exception) {
      return null;
    }
  }

  @Override
  public String decrypt(String cipherText, byte[] iv){
    try {
      SecretKey key = this.generateSecretKey();
      Cipher cipher = Cipher.getInstance(cryptoAlgo);
      cipher.init(Cipher.DECRYPT_MODE, key, this.generateParameterSpecIv(iv));

      byte[] plainText = cipher.doFinal(Base64.getUrlDecoder().decode(cipherText));
      return new String(plainText);
    } catch (IllegalBlockSizeException exception) {
      return null;

    } catch (BadPaddingException exception) {
      return null;

    } catch (NoSuchPaddingException exception) {
      return null;

    } catch (NoSuchAlgorithmException exception) {
      return null;

    } catch (InvalidKeySpecException exception) {
      return null;

    } catch (InvalidAlgorithmParameterException exception) {
      return null;

    } catch (InvalidKeyException exception) {
      return null;
    }
  }

  @Override
  public byte[] generateRandomByte() {
    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);
    return iv;
  }

  private SecretKey generateSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory factory = SecretKeyFactory.getInstance(cryptoSecretkeyAlgo);
    KeySpec spec = new PBEKeySpec(cryptoKey.toCharArray(), cryptoSalt.getBytes(), CRYPTO_ITERATION_COUNT, CRYPTO_KEY_LENGTH);
    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
            .getEncoded(), cryptoName);
    return secret;
  }
  private IvParameterSpec generateParameterSpecIv(byte[] bytes) {
    return new IvParameterSpec(bytes);
  }
}
