package com.thehecklers.sburrestdemo;

import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class SburRestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SburRestDemoApplication.class, args);
    }

}

@RestController
@RequestMapping("/books")
class RestApiDemoController {

    private final BookRepository repository;

    public RestApiDemoController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    Iterable<Book> getBooks() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Book> getBookById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping
    Book postBook(@RequestBody Book book) {
        if (book.getId() == null) {
            book.setId(UUID.randomUUID().toString());
        }
        return repository.save(book);
    }

    @PutMapping("/{id}")
    ResponseEntity<Book> putBook(@PathVariable String id,
                                 @RequestBody Book book) {

        return repository.findById(id)
                .map(existing -> {
                    existing.setTitulo(book.getTitulo());
                    existing.setAutor(book.getAutor());
                    return new ResponseEntity<>(repository.save(existing), HttpStatus.OK);
                })
                .orElseGet(() -> {
                    book.setId(UUID.randomUUID().toString());
                    return new ResponseEntity<>(repository.save(book), HttpStatus.CREATED);
                });
    }

    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable String id) {
        repository.deleteById(id);
    }
}

@Entity
@Table(name = "books")
class Book {

    @Id
    private String id;
    private String titulo;
    private String autor;

    public Book() {
    }

    public Book(String titulo, String autor) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}

interface BookRepository extends JpaRepository<Book, String> {
}