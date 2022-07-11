package com.liem.userservice.entity;

import com.application.common.entity.BaseEntity;
import com.liem.userservice.entity.enums.ResourceRoleType;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Resource entity.
 */
@Table(name = "resource")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class ResourceEntity extends BaseEntity<Long> implements Serializable, Cloneable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = -187673971298024290L;

  /**
   * The Path pattern.
   */
  @Column(name = "path_pattern", nullable = false,
      unique = true, columnDefinition = "VARCHAR(255)")
  private String pathPattern;

  /**
   * The Resource role type.
   */
  @Column(name = "role_type", nullable = false, columnDefinition = "VARCHAR(10)")
  @Enumerated(EnumType.STRING)
  private ResourceRoleType resourceRoleType = ResourceRoleType.ANY;

  /**
   * The Roles.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "resources_roles",
      joinColumns = @JoinColumn(name = "resource_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Exclude
  private Set<RoleEntity> roles;

  /**
   * Clone resource entity.
   *
   * @return the resource entity
   */
  @Override
  public ResourceEntity clone() {
    try {
      return (ResourceEntity) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
