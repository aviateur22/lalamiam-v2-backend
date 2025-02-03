package com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.adapter.IClientInscriptionInput;

import java.io.File;
import java.util.List;

public interface IProfessionalInscriptionInput extends IClientInscriptionInput {
    String getLastName();
    String getFirstName();
    String getPhone();
    List<File> getProfessionalInscriptionDocuments();
}
