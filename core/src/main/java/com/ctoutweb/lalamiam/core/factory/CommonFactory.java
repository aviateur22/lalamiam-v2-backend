package com.ctoutweb.lalamiam.core.factory;

import com.ctoutweb.lalamiam.core.entity.common.IProfessional;
import com.ctoutweb.lalamiam.core.entity.common.impl.ProfessionalImpl;

public class CommonFactory {
  public static ProfessionalImpl getProfessionalImpl(IProfessional professional) {
    return new ProfessionalImpl(professional);
  }
}
