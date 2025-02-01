package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.time.ZonedDateTime;
import java.util.Base64;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.CRYPTO_ITERATION_COUNT;
import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.CRYPTO_KEY_LENGTH;

@Service
@PropertySource({"classpath:application.properties"})
public class CryptographiceServiceImpl implements ICryptoService {
  private static final Logger LOGGER = LogManager.getLogger();
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
  public boolean isHashValid(String plainText, String hash) {
    return passwordEncoder.matches(plainText, hash);
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

      return Base64.getUrlEncoder().withoutPadding().encodeToString(cipherText);

    } catch (IllegalBlockSizeException exception) {
      return null;

    } catch (BadPaddingException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - encrypt - BadPaddingException] - Erreur pour chiffrement pour: %s", text));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (NoSuchPaddingException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - encrypt - NoSuchPaddingException] - Erreur pour chiffrement pour: %s", text));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (NoSuchAlgorithmException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - encrypt - NoSuchAlgorithmException] - Erreur pour chiffrement pour: %s", text));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (InvalidKeySpecException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - encrypt - InvalidKeySpecException] - Erreur pour chiffrement pour: %s", text));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (InvalidAlgorithmParameterException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - encrypt - InvalidAlgorithmParameterException] - Erreur pour chiffrement pour: %s", text));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (InvalidKeyException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - encrypt - InvalidKeyException] - Erreur pour chiffrement pour: %s", text));
      LOGGER.error(()->String.format("Exception: %s", exception));
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
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - decrypt - BadPaddingException] - Erreur de déchiffrement pour: %s", cipherText));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (NoSuchPaddingException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - decrypt - NoSuchPaddingException] - Erreur de déchiffrement pour: %s", cipherText));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (NoSuchAlgorithmException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - decrypt - NoSuchAlgorithmException] - Erreur de déchiffrement pour: %s", cipherText));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (InvalidKeySpecException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - decrypt - InvalidKeySpecException] - Erreur de déchiffrement pour: %s", cipherText));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (InvalidAlgorithmParameterException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - decrypt - BadPaddingException] - Erreur de déchiffrement pour: %s", cipherText));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;

    } catch (InvalidKeyException exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - decrypt - InvalidAlgorithmParameterException] - Erreur de déchiffrement pour: %s", cipherText));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;
    } catch (Exception exception) {
      LOGGER.error(()->String.format("[CryptographiceServiceImpl - decrypt - Exception] - Erreur de déchiffrement pour: %s", cipherText));
      LOGGER.error(()->String.format("Exception: %s", exception));
      return null;
    }
  }

  @Override
  public byte[] generateRandomByte() {
    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);
    return iv;
  }

  @Override
  public byte[] getByteArrayFromBase64(String base64Text) throws IllegalArgumentException {
    try{
      return Base64.getDecoder().decode(base64Text.getBytes());
    } catch (Exception exception) {
      LOGGER.error(()->String.format("Impossible de convertir %s en byte[]", base64Text));
      return null;
    }
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
