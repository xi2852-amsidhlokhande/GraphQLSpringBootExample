package com.amsidh.mvc.service.datafetcher;

import com.amsidh.mvc.model.Book;
import com.amsidh.mvc.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookByPublisherDataFetcher implements DataFetcher<Book> {
    private final BookRepository bookRepository;

    @Override
    public Book get(DataFetchingEnvironment environment) throws Exception {
        String publisher = environment.getArgument("publisher");
        //return bookRepository.findByPublisherIgnoreCase(publisher).orElseThrow(() -> new RuntimeException(String.format("No Book found with publisher %s", publisher)));
        return bookRepository.findByPublisher(publisher).orElseThrow(() -> new RuntimeException(String.format("No Book found with publisher %s", publisher)));
    }
}
