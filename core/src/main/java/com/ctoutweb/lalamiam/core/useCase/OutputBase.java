package com.ctoutweb.lalamiam.core.useCase;

/**
 * Renvoie L'implementation d'une Implementation vers une abstraction pour la sortie du UseCase
 * @param <T> Class Type BoundaryOutput: Impléméntation des données à renvoyées en fin de useCase.
 * @param <U> Class Type BoundaryOutputAdapter: Abstraction de utilisée à la sortie du UseCase.
 */
public abstract class OutputBase<T extends U, U> {
  private final T outputBoundary;

  public OutputBase(T outputBoundary) {
    this.outputBoundary = outputBoundary;
  }

  public U getOutputBoundaryAdapter() {
    return outputBoundary;
  }

}
