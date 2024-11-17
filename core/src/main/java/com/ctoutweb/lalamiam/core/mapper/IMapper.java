package com.ctoutweb.lalamiam.core.mapper;

public interface IMapper<T, U> {
  public U map(T data);
}
