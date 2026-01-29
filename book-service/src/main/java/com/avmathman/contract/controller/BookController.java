package com.avmathman.contract.controller;

import com.avmathman.contract.dtos.BookDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(new BookDto(id, "Clean Code", "Robert Martin"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto dto) {
        return new ResponseEntity<>(new BookDto(10L, dto.getTitle(), dto.getAuthor()), HttpStatus.CREATED);
    }
}
