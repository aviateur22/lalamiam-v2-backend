package com.ctoutweb.lalamiam.infra.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name")
  private String usrerName;

  private String email;

  private String password;

  @CreationTimestamp
  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;

  /**
   * Relation
   */
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private DelayLoginEntity delayLogin;
  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<DocumentEntity> professionalDocuments;
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  private EmployeeEntity employeeInformation;
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  private ProfessionalEntity professionalInformation;
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  private UserAccountEntity userAccountInformation;
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  private EmployeeAccountEntity employeeAccountInformation;
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  private ProfessionalAccountEntity professionalAccountInformation;
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  private JwtEntity jwt;
  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<RoleUserEntity> userRoles;
  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<TokenEntity> userTokens;

  //////////////////////////////////////////////////////////////


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsrerName() {
    return usrerName;
  }

  public void setUsrerName(String usrerName) {
    this.usrerName = usrerName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public DelayLoginEntity getDelayLogin() {
    return delayLogin;
  }

  public void setDelayLogin(DelayLoginEntity delayLogin) {
    this.delayLogin = delayLogin;
  }

  public List<DocumentEntity> getProfessionalDocuments() {
    return professionalDocuments;
  }

  public void setProfessionalDocuments(List<DocumentEntity> professionalDocuments) {
    this.professionalDocuments = professionalDocuments;
  }

  public EmployeeEntity getEmployeeInformation() {
    return employeeInformation;
  }

  public void setEmployeeInformation(EmployeeEntity employeeInformation) {
    this.employeeInformation = employeeInformation;
  }

  public ProfessionalEntity getProfessionalInformation() {
    return professionalInformation;
  }

  public void setProfessionalInformation(ProfessionalEntity professionalInformation) {
    this.professionalInformation = professionalInformation;
  }

  public UserAccountEntity getUserAccountInformation() {
    return userAccountInformation;
  }

  public void setUserAccountInformation(UserAccountEntity userAccountInformation) {
    this.userAccountInformation = userAccountInformation;
  }

  public EmployeeAccountEntity getEmployeeAccountInformation() {
    return employeeAccountInformation;
  }

  public void setEmployeeAccountInformation(EmployeeAccountEntity employeeAccountInformation) {
    this.employeeAccountInformation = employeeAccountInformation;
  }

  public ProfessionalAccountEntity getProfessionalAccountInformation() {
    return professionalAccountInformation;
  }

  public void setProfessionalAccountInformation(ProfessionalAccountEntity professionalAccountInformation) {
    this.professionalAccountInformation = professionalAccountInformation;
  }

  public JwtEntity getJwt() {
    return jwt;
  }

  public void setJwt(JwtEntity jwt) {
    this.jwt = jwt;
  }

  public List<RoleUserEntity> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(List<RoleUserEntity> userRoles) {
    this.userRoles = userRoles;
  }

  public List<TokenEntity> getToken() {
    return userTokens;
  }

  public void setToken(List<TokenEntity> userTokens) {
    this.userTokens = userTokens;
  }
}
