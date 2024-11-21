package com.ctoutweb.lalamiam.infra.repository.impl;

import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument;
import com.ctoutweb.lalamiam.core.entity.common.IProfessional;
import com.ctoutweb.lalamiam.core.provider.IAdminDisplayProfessionalDocumentRepository;
import com.ctoutweb.lalamiam.infra.repository.IDocumentRepository;
import com.ctoutweb.lalamiam.infra.repository.IProfessionalRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AdminDisplayProfessionalDocumentRepositoryImpl implements IAdminDisplayProfessionalDocumentRepository {

  private final IDocumentRepository documentRepository;
  private final IProfessionalRepository professionalRepository;

  public AdminDisplayProfessionalDocumentRepositoryImpl(IDocumentRepository documentRepository, IProfessionalRepository professionalRepository) {
    this.documentRepository = documentRepository;
    this.professionalRepository = professionalRepository;
  }

  @Override
  public Optional<IProfessional> findProfessionalById(Long professionalId) {
    return Optional.empty();
  }

  @Override
  public Optional<IAdminDisplayProfessionalDocument.IProfessionalDocument> findProfessionalInscriptionDocument(Long professionalId) {
    return Optional.empty();
  }
}
