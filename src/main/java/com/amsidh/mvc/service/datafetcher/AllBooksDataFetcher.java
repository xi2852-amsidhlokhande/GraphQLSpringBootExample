package com.amsidh.mvc.service.datafetcher;

import com.amsidh.mvc.model.Book;
import com.amsidh.mvc.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {

    private final BookRepository bookRepository;

    @Override
    public List<Book> get(DataFetchingEnvironment environment) throws Exception {
        return bookRepository.findAll();
    }
}
