package com.ctoutweb.lalamiam.core.useCase;

/**
 * UseCase Input
 * @param <T> Class Type - BoundaryInputAdapter: Abstraction utilisé pour l'entrée du UseCase
 * @param <U> - BoundaryInput: Implementation du BoundaryInputAdapter utilisé en début de UseCase
 */
public abstract class InputBase<T, U> {

  protected T inputBoundaryAdapter;

  public InputBase(T inputBoundaryAdapter){
    this.inputBoundaryAdapter = inputBoundaryAdapter;
  }

  protected abstract U getImplementation(T inputBoundaryAdapter);

  /**
   * Renvoie l'implémentation du inputBoundaryAdapter
   * @return U - l'impléméntation des données à l'entrée du UseCase
   */
  public U getBoundaryInput() {
    return getImplementation(inputBoundaryAdapter);
  }

}
