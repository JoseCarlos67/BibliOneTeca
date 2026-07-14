package com.jcarlos67.biblioneteca.config;

import com.jcarlos67.biblioneteca.model.Book;
import com.jcarlos67.biblioneteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DevConfig implements CommandLineRunner {
  @Autowired
  private BookRepository bookRepository;

  @Override
  public void run(String... args) throws Exception {
    if(bookRepository.count() == 0) {
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
