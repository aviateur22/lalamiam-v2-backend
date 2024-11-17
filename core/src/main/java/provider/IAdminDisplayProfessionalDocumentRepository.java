package provider;

import entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument.IProfessionalDocument;
import entity.common.IProfessional;

import java.util.Optional;

public interface IAdminDisplayProfessionalDocumentRepository {
  Optional<IProfessional> findProfessionalById(Long professionalId);

  Optional<IProfessionalDocument> findProfessionalInscriptionDocument(Long professionalId);
}
