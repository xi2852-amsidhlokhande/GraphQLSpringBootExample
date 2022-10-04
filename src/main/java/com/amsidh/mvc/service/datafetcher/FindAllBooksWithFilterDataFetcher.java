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
public class FindAllBooksWithFilterDataFetcher implements DataFetcher<List<Book>> {
    private final BookRepository bookRepository;

    @Override
    public List<Book> get(DataFetchingEnvironment environment) throws Exception {
        String isn = environment.getArgument("isn");
        String title = environment.getArgument("title");
        String publisher = environment.getArgument("publisher");
        return bookRepository.findBookByIsnAndTitleAndPublisherAllIgnoringCase(isn, title, publisher).orElseThrow(() -> new RuntimeException(String.format("No book found with given filter isn %s, title %s and publisher %s", isn, title, publisher)));
    }
}
