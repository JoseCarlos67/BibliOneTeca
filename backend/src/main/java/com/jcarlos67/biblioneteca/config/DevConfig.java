package com.jcarlos67.biblioneteca.config;

import com.jcarlos67.biblioneteca.model.Author;
import com.jcarlos67.biblioneteca.model.Book;
import com.jcarlos67.biblioneteca.model.Edition;
import com.jcarlos67.biblioneteca.model.Publisher;
import com.jcarlos67.biblioneteca.repository.AuthorRepository;
import com.jcarlos67.biblioneteca.repository.BookRepository;
import com.jcarlos67.biblioneteca.repository.EditionRepository;
import com.jcarlos67.biblioneteca.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

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

  @Override
  public void run(String... args) throws Exception {
    bookRepository.deleteAll();
    authorRepository.deleteAll();

    if(bookRepository.count() == 0) {
      Author auth1 = new Author("J.R.R. Tolkien", LocalDate.of(1892, 1, 3), null, "British");

      authorRepository.save(auth1);

      Book b1 = new Book("The Fellowship of the Ring");
      b1.getAuthorSet().add(auth1);

      bookRepository.save(b1);

      Publisher pub1 = new Publisher("Mariner Books", "Mariner Books", "483094208094", "www.teste.com.br");
      publisherRepository.save(pub1);

      Edition ed1 = new Edition("0547928211", null, 2012, "English", 432, "www.teste.com", "The Fellowship of the Ring is the first volume in J.R.R. Tolkien's epic high fantasy adventure, The Lord of the Rings.\n" +
              "\n" +
              "One Ring to rule them all, One Ring to find them, One Ring to bring them all and in the darkness bind them.\n" +
              "\n" +
              "In ancient times the Rings of Power were crafted by the Elven-smiths, and Sauron, the Dark Lord, forged the One Ring, filling it with his own power so that he could rule all others. But the One Ring was taken from him, and though he sought it throughout Middle-earth, it remained lost to him. After many ages it fell into the hands of Bilbo Baggins, as told in The Hobbit. In a sleepy village in the Shire, young Frodo Baggins finds himself faced with an immense task in this classic tale of good vs evil, as his elderly cousin Bilbo entrusts the Ring to his care. Frodo must leave his home and make a perilous journey across Middle-earth to the Cracks of Doom, there to destroy the Ring and foil the Dark Lord in his evil purpose.\n" +
              "\n" +
              "\"A unique, wholly realized other world, evoked from deep in the well of Time, massively detailed, absorbingly entertaining, profound in meaning.\"―The New York Times", b1, pub1);

      editionRepository.save(ed1);

      System.out.println("Banco de dados populado com sucesso!");
    } else {
      System.out.println("O banco de dados já possui registros. Ignorando carga inicial.");
    }
  }
}
