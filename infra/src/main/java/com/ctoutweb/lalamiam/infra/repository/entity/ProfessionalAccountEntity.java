package com.ctoutweb.lalamiam.infra.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "professional_account")
public class ProfessionalAccountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_activation_at")
  private ZonedDateTime accountActivationAt;

  @Column(name = "is_account_active")
  private Boolean isAccountActive;

  @CreationTimestamp
  @Column(name = "account_activation_limit_date_at")
  private ZonedDateTime accountActivationLimitDate;

  @CreationTimestamp
  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;

  /**
   * Relation
   */
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity user;

  ////////////////////////////////////


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ZonedDateTime getAccountActivationAt() {
    return accountActivationAt;
  }

  public void setAccountActivationAt(ZonedDateTime accountActivationAt) {
    this.accountActivationAt = accountActivationAt;
  }

  public Boolean getIsAccountActive() {
    return isAccountActive;
  }

  public void setIsAccountActive(Boolean accountActive) {
    isAccountActive = accountActive;
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

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public ZonedDateTime getAccountActivationLimitDate() {
    return accountActivationLimitDate;
  }

  public void setAccountActivationLimitDate(ZonedDateTime accountActivationLimitDate) {
    this.accountActivationLimitDate = accountActivationLimitDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProfessionalAccountEntity that = (ProfessionalAccountEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(accountActivationAt, that.accountActivationAt) && Objects.equals(isAccountActive, that.isAccountActive) && Objects.equals(accountActivationLimitDate, that.accountActivationLimitDate) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountActivationAt, isAccountActive, accountActivationLimitDate, createdAt, updatedAt, user);
  }

  @Override
  public String toString() {
    return "ProfessionalAccountEntity{" +
            "id=" + id +
            ", accountActivationAt=" + accountActivationAt +
            ", isAccountActive=" + isAccountActive +
            ", accountActivationLimitDate=" + accountActivationLimitDate +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            //", user=" + user +
            '}';
  }
}
