package factory;

import entity.common.IProfessional;
import entity.common.impl.ProfessionalImpl;

public class CommonFactory {
  public static ProfessionalImpl getProfessionalImpl(IProfessional professional) {
    return new ProfessionalImpl(professional);
  }
}
