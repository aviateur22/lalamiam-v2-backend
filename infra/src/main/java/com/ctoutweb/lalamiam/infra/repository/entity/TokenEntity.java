package com.ctoutweb.lalamiam.infra.repository.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "token")
public class TokenEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cryptography_text")
  private String cryptographyText;

  @Column(name = "cryptography_type")
  private String cryptographyType;

  @Column(name = "iv_key")
  private String ivKey;


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

  public String getCryptographyText() {
    return cryptographyText;
  }

  public void setCryptographyText(String cryptographyText) {
    this.cryptographyText = cryptographyText;
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

  public String getIvKey() {
    return ivKey;
  }

  public void setIvKey(String ivKey) {
    this.ivKey = ivKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TokenEntity that = (TokenEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(cryptographyText, that.cryptographyText) && Objects.equals(cryptographyType, that.cryptographyType) && Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cryptographyText, cryptographyType, user);
  }

  @Override
  public String toString() {
    return "TokenEntity{" +
            "id=" + id +
            ", token='" + cryptographyText + '\'' +
            ", cryptographyType='" + cryptographyType + '\'' +
            ", user=" + user +
            '}';
  }
}
