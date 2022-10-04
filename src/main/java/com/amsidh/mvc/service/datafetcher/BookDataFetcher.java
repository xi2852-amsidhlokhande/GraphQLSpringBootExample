package com.amsidh.mvc.service.datafetcher;

import com.amsidh.mvc.model.Book;
import com.amsidh.mvc.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookDataFetcher implements DataFetcher<Book> {

    private final BookRepository bookRepository;

    @Override
    public Book get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Book found with isn %s", id)));
    }
}
