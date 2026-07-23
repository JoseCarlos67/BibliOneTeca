package com.jcarlos67.biblioneteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table (name = "genres")
@Getter
@Setter
public class Genre implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  private UUID id;

  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "genreSet")
  private Set<Book> bookSet = new HashSet<>();

  public Genre(){
  }

  public Genre(String name) {
    id = null;
    this.name = name;
  }
}
