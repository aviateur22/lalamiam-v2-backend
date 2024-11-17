package entity.captcha;


public interface ICaptcha {
  /**
   * Données attendues lors de la généreation d'un captcha
   */
  public interface IGeneratedCaptcha {
    /**
     * Titre principal du captcha
     *
     * ex: Calculer le bon résultat
     * ex: Recopier le mot
     * ex: Quelle est cette image
     * ....
     * @return String
     */
    public String getCaptchaTitle();


    /**
     * Id de la réponse en base
     * @return Srting
     */
    Long getResponseId();

    /**
     * Question générée
     * ou
     * Path de l'image à charger
     *
     * ex: pour un captcha type calcul:
     * La question 05 + 02
     *
     * ex: pour un captcha type text
     * La question KjhdLL
     *
     * ex: pour un captcha type Image
     * La question: path/image/a/charger
     *
     * @return String
     */
    String getCaptchaQuestion();

    /**
     * Valide si la question du Captcha doit être transformé en image.
     * Si la valeur est True alors la réponse sera convertie en image
     * Si False alors la question represente le Path de l'image a charger
     * @return boolean
     */
    boolean isQuestionToBeTransformedInImage();
  }

  /**
   * Sauvegarde d'une réponse captcha en base de données
   */
  public interface ISavedResponseId {
    Long getResponseId();
  }

  /**
   * pour les Captchas de type Image
   * Données sur une image captcha de disponible
   */
  public interface IAvailableCaptchaImage {

    /**
     * Réponse attendu
     * @return String
     */
    String getCaptchaResponse();

    /**
     * Path de l'image
     * @return String
     */
    String getImagePath();

  }
}
