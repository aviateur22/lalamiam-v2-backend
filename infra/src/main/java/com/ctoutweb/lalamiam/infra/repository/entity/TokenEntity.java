package com.ctoutweb.lalamiam.infra.repository.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "token")
public class TokenEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  @Column(name = "cryptograpy_type")
  private String cryptographyType;

  /**
   * Relation
   */
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity user;

  /////////////////////////////////////////////////////////////

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getCryptographyType() {
    return cryptographyType;
  }

  public void setCryptographyType(String cryptographyType) {
    this.cryptographyType = cryptographyType;
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
    TokenEntity that = (TokenEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(cryptographyType, that.cryptographyType) && Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, token, cryptographyType, user);
  }

  @Override
  public String toString() {
    return "TokenEntity{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", cryptographyType='" + cryptographyType + '\'' +
            ", user=" + user +
            '}';
  }
}
