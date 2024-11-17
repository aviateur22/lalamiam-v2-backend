package com.ctoutweb.lalamiam.core.entity.professionalInscription;

public interface IProfessionalInscription {

  /**
   * Résulta Creation d'un professionel
   */
  public interface ICreatedProfessional {
    Long getProfessionalId();
  }
  /**
   * Résulat dela creation du professionel
   */
  public interface ICreatedProfessionalAccount {
    public Long getAccountId();
  }
}
