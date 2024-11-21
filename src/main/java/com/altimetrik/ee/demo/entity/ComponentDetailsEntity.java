package com.altimetrik.ee.demo.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "component_details")
public class ComponentDetailsEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "component_name")
  private String componentName;

  @Column(name = "component_identifier")
  private String componentIdentifier;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false, updatable = false)
  private Date createdDate;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_date")
  private Date updatedDate;

  public ComponentDetailsEntity() {
    super();
  }

  public ComponentDetailsEntity(String componentName, String componentIdentifier) {
    super();
    this.componentName = componentName;
    this.componentIdentifier = componentIdentifier;
  }
}
