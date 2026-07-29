package com.jcarlos67.biblioneteca.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "publishers")
@Getter
@Setter
public class Publisher implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  private UUID id;

  private String legalName;
  private String tradeName;
  private String cnpj;
  private String siteUrl;

  @JsonIgnore
  @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Edition> editions = new HashSet<>();

  public Publisher() {
  }

  public Publisher(String legalName, String tradeName, String cnpj, String siteUrl) {
    this.id = null;
    this.legalName = legalName;
    this.tradeName = tradeName;
    this.cnpj = cnpj;
    this.siteUrl = siteUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Publisher publisher = (Publisher) o;
    return Objects.equals(getId(), publisher.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }
}
