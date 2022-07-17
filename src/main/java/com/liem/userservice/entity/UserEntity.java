package com.liem.userservice.entity;

import com.application.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liem.userservice.entity.listener.UserEntityListener;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * The type User entity.
 */
@Table(name = "user")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
@EntityListeners(UserEntityListener.class)
public class UserEntity extends BaseEntity<Long> implements Serializable, Cloneable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 8105292227558025776L;

  /**
   * The Username.
   */
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  /**
   * The Password.
   */
  @Column(name = "password", nullable = false, unique = true)
  private String password;

  /**
   * The Locked.
   */
  @Column(name = "locked", nullable = false)
  private boolean locked;

  /**
   * The Expired.
   */
  @Column(name = "expired", nullable = false)
  private boolean expired;

  /**
   * The Roles.
   */
  @ManyToMany
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Exclude
  private Set<RoleEntity> roles = new HashSet<>();

  /**
   * Gets granted authorities.
   *
   * @return the granted authorities
   */
  @JsonIgnore
  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    return Optional.ofNullable(this.getRoles())
        .map(roles -> roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toSet()))
        .orElse(Set.of());
  }

  /**
   * Clone asset entity.
   *
   * @return the asset entity
   */
  @Override
  public UserEntity clone() {
    try {
      return (UserEntity) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
