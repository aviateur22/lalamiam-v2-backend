package com.ctoutweb.lalamiam.core.useCase.professionalInscription.port;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;

import java.io.File;
import java.util.List;

public interface IProfessionalInscriptionInput extends IClientInscriptionInput {
    String getLastName();
    String getFirstName();
    String getPhone();
    List<File> getProfessionalInscriptionDocuments();
}
