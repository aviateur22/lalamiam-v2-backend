package provider;

import entity.common.ITokenToValidate;
import entity.cryptographic.CryptographicType;
import entity.cryptographic.ICryptography.ICryptographySaveResult;

import java.time.LocalDateTime;

public interface ICryptographicService {
  /**
   * Generation d'un text par cryptographie et sauvegarde en base
   * @param cryptographicType CryptographicType: Hash ou Chiffrement
   * @param plainText String - Text a à cryptographier
   * @param limitValidity LocalDateTime DateLimite de validité
   * @return ICryptographyResult
   */
  public ICryptographySaveResult cryptographyTextAndSave (
          CryptographicType cryptographicType,
          String plainText,
          LocalDateTime limitValidity
          );

  /**
   * Validation d'un token
   * @param tokenToValidate ITokenToValidate
   * @return Boolean
   */
  Boolean isHashValid(ITokenToValidate tokenToValidate);
}
