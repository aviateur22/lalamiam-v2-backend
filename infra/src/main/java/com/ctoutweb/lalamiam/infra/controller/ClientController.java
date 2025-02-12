package com.ctoutweb.lalamiam.infra.controller;

import com.ctoutweb.lalamiam.infra.dto.client.ClientRegisterAsProfessionalDto;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;
import com.ctoutweb.lalamiam.infra.model.impl.MessageResponseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {
  @PostMapping("/register-as-professional")
  public ResponseEntity<IMessageResponse> clientRegisterAsProfessional(ClientRegisterAsProfessionalDto dto) {
    IMessageResponse messageResponse = new MessageResponseImpl("Bonjour");
    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }
}
