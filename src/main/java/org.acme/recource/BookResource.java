package org.acme.resource;

import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
//import jdk.internal.loademvn r.BuiltinClassLoader;
import org.acme.model.Book;
import org.acme.services.MongoService;

import java.util.List;

@Path("/books")
public class BookResource {

    @Inject
    MongoService mongoService; // epeidh den einai static
                               // https://stackoverflow.com/questions/942326/calling-static-method-on-a-class

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return mongoService.getBooks();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book createBook(Book book) {
        return mongoService.createBook(book);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("id") String id) {
        mongoService.deleteBook(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book updateBookById(@PathParam("id") String id, JsonObject requestBody)throws Exception {
        String jstring = requestBody.getString("title");
        return mongoService.updateBookById(id,jstring);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book updateBook(@PathParam("id") String id, Book newBook)throws Exception {
        return mongoService.updateBook(id, newBook);
    }
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBookById(@PathParam("id") String id) throws Exception {
        return mongoService.getBookById(id);
    }
    }
