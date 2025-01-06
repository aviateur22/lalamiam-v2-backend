package com.ctoutweb.lalamiam.infra.dto;

import com.ctoutweb.lalamiam.core.adapter.IResponse;

import java.util.List;

public record LoginResponseDto(String jwt, String email, Long id, List<String> roles, String message) implements IResponse {
  @Override
  public String getResponseMessage() {
    return message;
  }
}
