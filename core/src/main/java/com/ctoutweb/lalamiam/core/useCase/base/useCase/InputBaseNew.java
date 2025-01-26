package com.ctoutweb.lalamiam.core.useCase.base.useCase;

public abstract class InputBaseNew<T> {
  protected T inputBoundaryAdapter;

  public InputBaseNew(T inputBoundaryAdapter){
    this.inputBoundaryAdapter = inputBoundaryAdapter;
  }

  /**
   * Renvoie l'implementation de Input Boundary
   * @return T - Implementation de l'absctraction
   */
  protected abstract T getInputBoundaryImplementation();
}
