package com.ctoutweb.lalamiam.infra.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "document")
public class DocumentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "document_path")
  private String documentPath;

  @CreationTimestamp
  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;

  /**
   * Relation
   */
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  /////////////////////////////////////////////////////////////////////////////////////////////////


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDocumentPath() {
    return documentPath;
  }

  public void setDocumentPath(String documentPath) {
    this.documentPath = documentPath;
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
    DocumentEntity that = (DocumentEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(documentPath, that.documentPath) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, documentPath, createdAt, updatedAt, user);
  }

  @Override
  public String toString() {
    return "DocumentEntity{" +
            "id=" + id +
            ", documentPath='" + documentPath + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", user=" + user +
            '}';
  }
}
