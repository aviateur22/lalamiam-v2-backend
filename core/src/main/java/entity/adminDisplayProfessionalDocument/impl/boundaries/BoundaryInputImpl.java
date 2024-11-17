package entity.adminDisplayProfessionalDocument.impl.boundaries;

import adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryInputAdapter;

public record BoundaryInputImpl(IBoundaryInputAdapter inputBoundaryAdapter) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInput(IBoundaryInputAdapter inputBoundaryAdapter) {
    return new BoundaryInputImpl(inputBoundaryAdapter);
  }

  @Override
  public Long getProfessionalId() {
    return inputBoundaryAdapter.getProfessionalId();
  }
}
