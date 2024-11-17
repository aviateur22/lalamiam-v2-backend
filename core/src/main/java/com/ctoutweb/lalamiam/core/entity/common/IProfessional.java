package com.ctoutweb.lalamiam.core.entity.common;

import java.time.LocalDateTime;

/**
 * Informations sur un professionel
 */
public interface IProfessional {
  /**
   * Id du professionel
   * @return
   */
  public Long getProfessionalId();

  /**
   * Id du professionel si actif comme client
   * @return
   */
  public Long getClientId();

  public boolean isProfessionalAccountConfirmed();

  public boolean isProfessionalAccountValidateByAdmin();

  public LocalDateTime professionalAccountActivationAt();
}
