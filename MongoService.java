package org.acme.services;

import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.config.MongoConfig;
import org.acme.model.Book;
import org.bson.types.ObjectId;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.conversions.Bson;
@ApplicationScoped
public class MongoService {
    @Inject
    MongoClient mongoClient;

    @Inject
    MongoConfig mongoConfig;

    public List<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        for (Book book : getBookCollection().find()) {
            list.add(book);
        }
        return list;
    }

    public Book createBook(Book book) {
        getBookCollection().insertOne(book);
        return book;
    }

    public Book getBookById(String id) throws Exception {
        List<Book> list = new ArrayList<>();
        for (Book book : getBookCollection().find(Filters.eq("id", id))) {
            list.add(book);
        }
        if(list.size() != 0)//error handling
            return list.get(0);
        else
            throw new Exception("No book found with id "+ id);
    }
    public void deleteBook(String id) {
        getBookCollection().deleteOne(Filters.eq("id", id));
    }

    public Book updateBookById(String id, String jstring) throws Exception{
        getBookCollection().updateOne(Filters.eq("id", id),Updates.set("title", jstring));
        List<Book> list = new ArrayList<>();
        for (Book book : getBookCollection().find(Filters.eq("id", id))) {
            list.add(book);
        }
        if(list.size() != 0)//error handling
            return list.get(0);
        else
            throw new Exception("No book found with id "+ id);
    }

    public Book updateBook(String id, Book newBook) throws Exception{
        Bson filter = Filters.eq("id", id);
        Bson update = Updates.combine(
                Updates.set("title", newBook.getTitle()),
                Updates.set("author", newBook.getAuthor()),
                Updates.set("type", newBook.getType())
        );
        getBookCollection().updateOne(filter, update);
        List<Book> list = new ArrayList<>();
        for (Book book : getBookCollection().find(Filters.eq("id", id))) {
            list.add(book);
        }
        if(list.size() != 0)//error handling
            return list.get(0);
        else
            throw new Exception("No book found with id "+ id);
    }

    private MongoCollection<Book> getBookCollection() {
        return mongoClient
                .getDatabase(mongoConfig.getDatabase())
                .getCollection(mongoConfig.getCollection(), Book.class);
    }
}
