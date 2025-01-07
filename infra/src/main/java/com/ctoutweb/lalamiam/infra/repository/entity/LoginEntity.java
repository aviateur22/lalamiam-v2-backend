package com.ctoutweb.lalamiam.infra.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "login")
public class LoginEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "has_to_be_check")
  private boolean hasToBeCheck;

  @Column(name = "is_login_success")
  private boolean isLoginSuccess;

  @CreationTimestamp
  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @Column(name = "login_at")
  private ZonedDateTime loginAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;

  /**
   * Relation
   */
  @ManyToOne
  @JoinColumn(name="user_id", nullable=false)
  private UserEntity user;

  //////////////////////////////////////////////////////////


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean getHasToBeCheck() {
    return hasToBeCheck;
  }

  public void setHasToBeCheck(boolean hasToBeCheck) {
    this.hasToBeCheck = hasToBeCheck;
  }

  public boolean getIsLoginSuccess() {
    return isLoginSuccess;
  }

  public void setIsLoginSuccess(boolean loginSuccess) {
    isLoginSuccess = loginSuccess;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public ZonedDateTime getLoginAt() {
    return loginAt;
  }

  public void setLoginAt(ZonedDateTime loginAt) {
    this.loginAt = loginAt;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LoginEntity that = (LoginEntity) o;
    return hasToBeCheck == that.hasToBeCheck && isLoginSuccess == that.isLoginSuccess && Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects.equals(loginAt, that.loginAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, hasToBeCheck, isLoginSuccess, createdAt, loginAt, updatedAt, user);
  }

  @Override
  public String toString() {
    return "LoginEntity{" +
            "id=" + id +
            ", hasToBeCheck=" + hasToBeCheck +
            ", isLoginSuccess=" + isLoginSuccess +
            ", createdAt=" + createdAt +
            ", loginAt=" + loginAt +
            ", updatedAt=" + updatedAt +
            ", user=" + user +
            '}';
  }
}
