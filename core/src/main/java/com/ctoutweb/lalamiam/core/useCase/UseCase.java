package com.ctoutweb.lalamiam.core.useCase;

public interface UseCase <T extends UseCase.Input, U extends UseCase.Output>{
  public U execute(T input);

  public interface Input{}
  public interface Output{}
}
