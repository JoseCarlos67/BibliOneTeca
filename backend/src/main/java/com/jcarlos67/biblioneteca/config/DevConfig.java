package com.jcarlos67.biblioneteca.config;

import com.jcarlos67.biblioneteca.model.Author;
import com.jcarlos67.biblioneteca.model.Book;
import com.jcarlos67.biblioneteca.repository.AuthorRepository;
import com.jcarlos67.biblioneteca.repository.BookRepository;
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

  @Override
  public void run(String... args) throws Exception {
    bookRepository.deleteAll();
    authorRepository.deleteAll();

    if(bookRepository.count() == 0) {
      Author auth1 = new Author("J.R.R. Tolkien", LocalDate.of(1892, 1, 3), null, "British");
      Author auth2 = new Author("George R.R. Martin", LocalDate.of(1948, 9, 20), null,  "American");
      Author auth3 = new Author("Stephen King", LocalDate.of(1947, 9, 21), null, "American");

      authorRepository.saveAll(Arrays.asList(auth1, auth2, auth3));

      Book b1 = new Book("The Fellowship of the Ring");
      Book b2 = new Book("A Game of Thrones");
      Book b3 = new Book("The Shining");

      bookRepository.saveAll(Arrays.asList(b1, b2, b3));

      System.out.println("Banco de dados populado com sucesso!");
    } else {
      System.out.println("O banco de dados já possui registros. Ignorando carga inicial.");
    }
  }
}
