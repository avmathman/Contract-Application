package com.avmathman.contract.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private Long id;
    @NonNull
    private String title;
    private String author;
}
