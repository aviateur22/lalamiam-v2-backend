package com.ctoutweb.lalamiam.core.provider;

import com.ctoutweb.lalamiam.core.entity.common.IProfessional;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument.IProfessionalDocument;

import java.util.Optional;

public interface IAdminDisplayProfessionalDocumentRepository {
  Optional<IProfessional> findProfessionalById(Long professionalId);

  Optional<IProfessionalDocument> findProfessionalInscriptionDocument(Long professionalId);
}
