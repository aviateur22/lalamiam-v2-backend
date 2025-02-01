package com.ctoutweb.lalamiam.infra.controller;

import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.useCase.AdminDisplayProfessionalToActivateListUseCase;
import com.ctoutweb.lalamiam.infra.dto.admin.ActivateProfessionalDto;
import com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional.ProfessionalToActivateResumeDto;
import com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional.ProfessionalToActivateResumeListDto;
import com.ctoutweb.lalamiam.infra.service.IAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

  private final IAdminService adminService;

  public AdminController(IAdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/professionals-to-activate")
  public ResponseEntity<ProfessionalToActivateResumeListDto> getProfessionalToActivate() {

    List<ProfessionalToActivateResumeDto> professionals = adminService.findProfessionalsToActivates();

    return new ResponseEntity<>(new ProfessionalToActivateResumeListDto(professionals), HttpStatus.OK);
  }

  @PostMapping("/activate-professional-account")
  public ResponseEntity<String> activateProfessionalAccount(ActivateProfessionalDto dto) {
    return null;
  }
}
