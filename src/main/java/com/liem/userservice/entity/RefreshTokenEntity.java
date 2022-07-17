package com.liem.userservice.entity;

import com.application.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Access token entity.
 */
@Table(name = "refresh_token")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class RefreshTokenEntity extends BaseEntity<Long> implements Serializable, Cloneable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1567913622186134949L;

  /**
   * The User.
   */
  @JsonIgnore
  @ToString.Exclude
  @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, optional = false)
  private UserEntity user;

  /**
   * The Token.
   */
  @Column(name="token", nullable = false, unique = true, updatable = false)
  private String token;

  /**
   * The Expired at.
   */
  @Column(name="expired_at", nullable = false)
  private Instant expiredAt;

  /**
   * Clone access token entity.
   *
   * @return the access token entity
   */
  @Override
  public RefreshTokenEntity clone() {
    try {
      RefreshTokenEntity clone = (RefreshTokenEntity) super.clone();
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
