//package com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries;
//
//import com.ctoutweb.lalamiam.core.adapter.IResponse;
//import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryOutputAdapter;
//
//public record BoundaryOutputImpl(String message, Long userId) implements IBoundaryOutputAdapter, IResponse {
//
//  public static BoundaryOutputImpl getBoundaryOutputImpl(String message, Long userId) {
//    return new BoundaryOutputImpl(message, userId);
//  }
//  @Override
//  public String getResponseMessage() {
//    return message;
//  }
//
//  @Override
//  public Long getUserId() {
//    return userId;
//  }
//}
