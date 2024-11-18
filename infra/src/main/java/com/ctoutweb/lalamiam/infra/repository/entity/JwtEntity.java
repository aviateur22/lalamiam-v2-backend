package com.ctoutweb.lalamiam.infra.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "jwt")
public class JwtEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "jwt_token")
  private String jwtToken;

  @Column(name = "jwt_id")
  private String jwtId;

  @Column(name = "expired_at")
  private ZonedDateTime expiredAt;

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

  ////////////////////////////////////////////////////////////////


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getJwtId() {
    return jwtId;
  }

  public void setJwtId(String jwtId) {
    this.jwtId = jwtId;
  }

  public ZonedDateTime getExpiredAt() {
    return expiredAt;
  }

  public void setExpiredAt(ZonedDateTime expiredAt) {
    this.expiredAt = expiredAt;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JwtEntity jwtEntity = (JwtEntity) o;
    return Objects.equals(id, jwtEntity.id) && Objects.equals(jwtToken, jwtEntity.jwtToken) && Objects.equals(jwtId, jwtEntity.jwtId) && Objects.equals(expiredAt, jwtEntity.expiredAt) && Objects.equals(createdAt, jwtEntity.createdAt) && Objects.equals(updatedAt, jwtEntity.updatedAt) && Objects.equals(user, jwtEntity.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, jwtToken, jwtId, expiredAt, createdAt, updatedAt, user);
  }

  @Override
  public String toString() {
    return "JwtEntity{" +
            "id=" + id +
            ", jwtToken='" + jwtToken + '\'' +
            ", jwtId='" + jwtId + '\'' +
            ", expiredAt=" + expiredAt +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", user=" + user +
            '}';
  }
}
