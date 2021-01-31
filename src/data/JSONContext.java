package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Serves for saving and loading data from JSON file.
 */
public class JSONContext {

    private static FileWriter file;
    private String path = "src/data/bookdata.json";

    /**
     * Constructor
     */
    public JSONContext() {
    }

    /**
     * Saves a list of books to predefined location in path attribute.
     * @param books = collections of books in form of List interface
     */
    public void saveBooks(List<Book> books){

        JSONObject parentObject = new JSONObject();
        JSONArray booksJSON = new JSONArray();
        parentObject.put("books", booksJSON);

        for(Book book: books){
            JSONObject obj = new JSONObject();
            booksJSON.add(obj);
            obj.put("title", book.getTitle());
            obj.put("publisher", book.getPublisher());
            obj.put("description", book.getDescription());
            obj.put("authors", book.getAuthors());
            obj.put("pageCount", book.getPageCount());
            obj.put("publishedDate", book.getPublishedDate());
        }

        try{
            file = new FileWriter(path);
            file.write(parentObject.toJSONString());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                file.flush();
                file.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * Returns a collection of books from JSON file.
     * @return
     * @throws IOException
     */
    public List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();

        JSONParser parse = new JSONParser();

        try(FileReader reader = new FileReader(path)){
            JSONObject obj = (JSONObject) parse.parse(reader);
            JSONArray booksJSON = (JSONArray) obj.get("books");

            for(var bookJSON:booksJSON){
                JSONObject bookObj = (JSONObject) bookJSON;
                Book bookRecord = new Book();
                bookRecord.setPageCount(bookObj.get("pageCount").toString());
                bookRecord.setTitle(bookObj.get("title").toString());
                bookRecord.setAuthors(bookObj.get("authors").toString());
                bookRecord.setDescription(bookObj.get("description").toString());
                bookRecord.setPublishedDate(bookObj.get("publishedDate").toString());
                bookRecord.setPublisher(bookObj.get("publisher").toString());

                books.add(bookRecord);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return books;
    }
}
