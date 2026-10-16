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
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  @Column(nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_edition")
  private Edition edition;

  @Column(nullable = false, unique = true)
  private String assetCode;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PhysicalCopyStatus status;

  public PhysicalCopy(){
  }

  public PhysicalCopy(Edition edition) {
    this.id = UUID.randomUUID();
    this.edition = edition;
    this.status = PhysicalCopyStatus.AVAILABLE;
    this.generateAssetCode();
  }

  private void generateAssetCode() {
    if (this.assetCode == null && this.edition != null && this.id != null) {
      Genre[] genres = edition.getBook().getGenreSet().stream()
              .limit(2)
              .toArray(Genre[]::new);

      StringBuilder prefix = new StringBuilder();
      for (Genre genre : genres) {
        if (genre != null) {
          prefix.append(genre.getName().substring(0, 2).toUpperCase());
        }
      }

      String sufix = String.valueOf(this.id);
      int indice = sufix.indexOf('-');
      if (indice > 0) {
        sufix = sufix.substring(0, indice);
      }

      this.assetCode = prefix.toString() + sufix;
    }
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
