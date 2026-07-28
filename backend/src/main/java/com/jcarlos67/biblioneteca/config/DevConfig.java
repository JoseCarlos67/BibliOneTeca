package com.jcarlos67.biblioneteca.config;

import com.jcarlos67.biblioneteca.model.collection.*;
import com.jcarlos67.biblioneteca.model.collection.enums.PhysicalCopyStatus;
import com.jcarlos67.biblioneteca.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

@Configuration
public class DevConfig implements CommandLineRunner {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private EditionRepository editionRepository;

  @Autowired
  private PublisherRepository publisherRepository;

  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private PhysicalCopyRepository physicalCopyRepository;

  @Override
  public void run(String... args) throws Exception {
    editionRepository.deleteAll();
    bookRepository.deleteAll();
    authorRepository.deleteAll();
    publisherRepository.deleteAll();
    genreRepository.deleteAll();
    physicalCopyRepository.deleteAll();

    if (bookRepository.count() == 0) {
      // 1. Autor
      Author auth1 = new Author("J.R.R. Tolkien", LocalDate.of(1892, 1, 3), null, "British");
      authorRepository.save(auth1);

      // 2. Gêneros
      Genre g1 = new Genre("Fantasy");
      Genre g2 = new Genre("High Fantasy");
      Genre g3 = new Genre("Adventure");
      Genre g4 = new Genre("Fiction");
      genreRepository.saveAll(Arrays.asList(g1, g2, g3, g4));

      // 3. Livro (Vinculando Autor e Gêneros)
      Book b1 = new Book("The Fellowship of the Ring");
      b1.getAuthorSet().add(auth1);
      b1.getGenreSet().addAll(Arrays.asList(g1, g2, g3)); // Associa Fantasy, High Fantasy e Adventure
      bookRepository.save(b1);

      // 4. Editoras
      Publisher pub1 = new Publisher("Mariner Books", "Mariner Books", "483094208094", "www.marinerbooks.com");
      Publisher pub2 = new Publisher("HarperCollins Brasil", "HarperCollins", "12345678000199", "www.harpercollins.com.br");
      Publisher pub3 = new Publisher("Houghton Mifflin", "Houghton Mifflin Harcourt", "98765432000188", "www.hmhbooks.com");
      Publisher pub4 = new Publisher("George Allen & Unwin", "Allen & Unwin", "11223344000155", "www.allenandunwin.com");

      publisherRepository.saveAll(Arrays.asList(pub1, pub2, pub3, pub4));

      // 5. Edições

      // Edição 1: Brochura em Inglês (Mariner Books, 2012)
      Edition ed1 = new Edition(
              "0547928211", null, 2012, "English", 432, "www.teste.com/ed1",
              "The Fellowship of the Ring is the first volume in J.R.R. Tolkien's epic high fantasy adventure, The Lord of the Rings.\n\n" +
                      "One Ring to rule them all, One Ring to find them, One Ring to bring them all and in the darkness bind them.\n\n" +
                      "In ancient times the Rings of Power were crafted by the Elven-smiths, and Sauron, the Dark Lord, forged the One Ring...",
              b1, pub1
      );

      // Edição 2: Edição Brasileira / Português (HarperCollins Brasil, 2019) - Ideal para testar filtros de idioma (Portuguese)
      Edition ed2 = new Edition(
              "8595084742", null, 2019, "Portuguese", 576, "www.teste.com/ed2",
              "A Sociedade do Anel é o primeiro volume da trilogia O Senhor dos Anéis, de J.R.R. Tolkien.\n\n" +
                      "Em uma aldeia pacata no Condado, o jovem Frodo Bolseiro é encarregado de uma imensa tarefa quando seu idoso primo Bilbo lhe confia o Anel do Poder. Frodo deve deixar seu lar e fazer uma jornada perigosa pela Terra-média até a Montanha da Perdição para destruir o Anel e impedir o Senhor Sombrio Sauron.",
              b1, pub2
      );

      // Edição 3: Edição Comemorativa de 50 Anos (Houghton Mifflin, 2004) - Capa dura com mais páginas
      Edition ed3 = new Edition(
              "0618391002", null, 2004, "English", 448, "www.teste.com/ed3",
              "50th Anniversary Edition. This special commemorative edition features a fully corrected text revised by Christopher Tolkien and dedicated Tolkien scholars, fold-out maps of Middle-earth, and an extensive index.",
              b1, pub3
      );

      // Edição 4: Primeira Edição Histórica (George Allen & Unwin, 1954) - Ideal para testar ordenação por ano e edições antigas
      Edition ed4 = new Edition(
              "0007136595", null, 1954, "English", 423, "www.teste.com/ed4",
              "The original 1954 first edition of The Fellowship of the Ring, published in London. Features the original dust jacket art designed by J.R.R. Tolkien himself.",
              b1, pub4
      );

      // Salvando todas as edições em lote
      editionRepository.saveAll(Arrays.asList(ed1, ed2, ed3, ed4));

      PhysicalCopy pc1 = new PhysicalCopy(ed1, PhysicalCopyStatus.AVAILABLE);
      PhysicalCopy pc2 = new PhysicalCopy(ed1, PhysicalCopyStatus.AVAILABLE);
      PhysicalCopy pc3 = new PhysicalCopy(ed1, PhysicalCopyStatus.AVAILABLE);
      physicalCopyRepository.saveAll(Arrays.asList(pc1, pc2, pc3));



      System.out.println("Banco de dados populado com sucesso! 1 livro com 4 edições e 3 gêneros vinculados.");
    } else {
      System.out.println("O banco de dados já possui registros. Ignorando carga inicial.");
    }
  }
}