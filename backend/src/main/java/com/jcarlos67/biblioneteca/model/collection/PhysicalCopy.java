package com.jcarlos67.biblioneteca.model.collection;

import com.jcarlos67.biblioneteca.model.collection.enums.PhysicalCopyStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "physical_copies")
@Getter
@Setter
public class PhysicalCopy implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "fk_edition")
  private Edition edition;

  private String assetCode;

  @Enumerated(EnumType.STRING)
  private PhysicalCopyStatus status;

  public PhysicalCopy(){
  }

  public PhysicalCopy(Edition edition, PhysicalCopyStatus status) {
    id = UUID.randomUUID();
    this.edition = edition;
    GenerateAssetCode(edition);
    this.status = status;
  }

  private void GenerateAssetCode(Edition edition) {
    Genre[] genres = edition.getBook().getGenreSet().stream()
            .limit(2)
            .toArray(Genre[]::new);

    StringBuilder prefix = new StringBuilder();
    for (int i = 0; i < genres.length; i++) {
      if (genres[i] != null) {
        prefix.append(genres[i].getName().substring(0, 2).toUpperCase());
      }
    }

    String sufix = String.valueOf(this.id);
    int indice = sufix.indexOf('-');
    sufix = sufix.substring(0, indice);

    assetCode = prefix.toString() + sufix;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    PhysicalCopy that = (PhysicalCopy) o;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }
}
