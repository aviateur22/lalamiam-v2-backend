package com.ctoutweb.lalamiam.infra.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "employee_professional")
public class EmployeeProfessionalEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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
  @JoinColumn(name = "employee_id", nullable = false)
  private UserEntity employee;

  @ManyToOne
  @JoinColumn(name = "professional_id", nullable = false)
  private UserEntity professional;

  ///////////////////////////////////////////////////////////////////////////////////////

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public UserEntity getEmployee() {
    return employee;
  }

  public void setEmployee(UserEntity employee) {
    this.employee = employee;
  }

  public UserEntity getProfessional() {
    return professional;
  }

  public void setProfessional(UserEntity professional) {
    this.professional = professional;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EmployeeProfessionalEntity that = (EmployeeProfessionalEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(employee, that.employee) && Objects.equals(professional, that.professional);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, employee, professional);
  }

  @Override
  public String toString() {
    return "EmployeeProfessionalEntity{" +
            "id=" + id +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", employee=" + employee +
            ", professional=" + professional +
            '}';
  }
}
