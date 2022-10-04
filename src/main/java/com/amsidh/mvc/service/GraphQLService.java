package com.amsidh.mvc.service;

import com.amsidh.mvc.model.Book;
import com.amsidh.mvc.repository.BookRepository;
import com.amsidh.mvc.service.datafetcher.AllBooksDataFetcher;
import com.amsidh.mvc.service.datafetcher.BookByPublisherDataFetcher;
import com.amsidh.mvc.service.datafetcher.BookDataFetcher;
import com.amsidh.mvc.service.datafetcher.FindAllBooksWithFilterDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class GraphQLService {

    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL graphQL;

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;
    @Autowired
    private BookByPublisherDataFetcher bookByPublisherDataFetcher;

    @Autowired
    private FindAllBooksWithFilterDataFetcher findAllBooksWithFilterDataFetcher;
    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    private void loadSchema() throws IOException {
        //Load Books into the Repository
        loadDataIntoHSQL();
        //Get the schema
        File schemaFile = resource.getFile();
        //Parse the Schema
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        this.graphQL = GraphQL.newGraphQL(schema).build();


    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher)
                        .dataFetcher("bookByPublisher", bookByPublisherDataFetcher)
                        .dataFetcher("findAllBooks", findAllBooksWithFilterDataFetcher)
                )
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

    private void loadDataIntoHSQL() {
        List<Book> bookList = Arrays.asList(
                Book.builder().isn("1").authors(new String[]{"Ravi", "Komal"}).title("Honey Funny").publishedDate("01-Jan-2020").publisher("Komal Priniting Press").build(),
                Book.builder().isn("2").authors(new String[]{"Amsidh", "Soho"}).title("Spring World").publishedDate("01-Jun-2020").publisher("Yuddo Priniting Press").build(),
                Book.builder().isn("3").authors(new String[]{"Kuriyan", "Dado"}).title("Java of Joy").publishedDate("01-Dec-2020").publisher("Kutty Priniting Press").build(),
                Book.builder().isn("4").authors(new String[]{"Foxon", "Neon"}).title("Sun of World").publishedDate("01-Feb-2020").publisher("Saras Priniting Press").build(),
                Book.builder().isn("5").authors(new String[]{"Yuddo", "Choyang"}).title("Mother Of Earch").publishedDate("01-Aug-2020").publisher("Manas Priniting Press").build()
        );
        bookRepository.saveAll(bookList);
    }
}
