package com.ctoutweb.lalamiam.infra.dto;

import com.ctoutweb.lalamiam.core.adapter.IResponse;

public record LoginResponseDto(String email, Long id, String message) implements IResponse {
  @Override
  public String getResponseMessage() {
    return message;
  }
}
