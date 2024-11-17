package provider;

public interface ICaptchaConfiguration {

  /**
   * Récupéeration d'un Token qui est ajouté à la génération d'un capatcha
   * @return String
   */
  String getCaptchaToken();

  /**
   * Séparation entre la token et la réponse su captcha
   * @return
   */
  String getStringSeparator();
}
