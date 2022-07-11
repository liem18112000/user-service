package com.liem.userservice.entity;

import com.application.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liem.userservice.entity.listener.RoleEntityListener;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Role entity.
 */
@Table(name = "role")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
@EntityListeners(RoleEntityListener.class)
public class RoleEntity extends BaseEntity<Long> implements Serializable, Cloneable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = -7184608465361987722L;

  /**
   * The users.
   */
  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  @Exclude
  private Set<UserEntity> users;

  /**
   * The resources.
   */
  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  @Exclude
  private Set<ResourceEntity> resources;

  /**
   * Clone role entity.
   *
   * @return the role entity
   */
  @Override
  public RoleEntity clone() {
    try {
      return (RoleEntity) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
