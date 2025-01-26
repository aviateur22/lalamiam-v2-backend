package com.ctoutweb.lalamiam.core.useCase.base.useCase;

public abstract class OutputBaseNew <T> {
  private final T outputBoundary;

  public OutputBaseNew(T outputBoundary) {
    this.outputBoundary = outputBoundary;
  }

  public T getOutputBoundary() {
    return outputBoundary;
  }
}
