package com.ctoutweb.lalamiam.infra.dto.mapper;

/**
 * Mapper vers DTO
 * @param <T> Objet en entrée
 * @param <U> Objet en sortie
 */
public interface IMapper <T, U> {
  public U map(T objectToMap);
}
