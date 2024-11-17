package entity.adminDisplayProfessionalDocument;

import java.io.File;

public interface IAdminDisplayProfessionalDocument {

  /**
   * Structure des données récupérées pour les documents du professionnel
   */
  interface IProfessionalDocument {
    Long getProfessionalId();
    String getProfessionalEmail();
    String getProfessionalPhone();
    File getProfessionalFileStatus();
  }


}
