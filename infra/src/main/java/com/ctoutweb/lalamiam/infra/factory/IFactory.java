package com.ctoutweb.lalamiam.infra.factory;

/**
 * Impléméntatation d'une abstraction
 * @param <T> Type en entrée
 * @param <U> Type en sortie
 */
public interface IFactory <T, U> {
  public U getImpl(T data);
}
