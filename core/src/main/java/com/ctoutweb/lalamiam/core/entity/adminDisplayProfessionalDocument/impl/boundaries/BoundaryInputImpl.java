package com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.impl.boundaries;

import com.ctoutweb.lalamiam.core.adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryInputAdapter;

public record BoundaryInputImpl(IBoundaryInputAdapter inputBoundaryAdapter) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInput(IBoundaryInputAdapter inputBoundaryAdapter) {
    return new BoundaryInputImpl(inputBoundaryAdapter);
  }

  @Override
  public Long getProfessionalId() {
    return inputBoundaryAdapter.getProfessionalId();
  }
}
