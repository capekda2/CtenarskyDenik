package data;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class for aggregating books and working with their persistence.
 */
public class BookList {

    private List<Book> books;
    private JSONContext con;
    private BooksAPIContext apiCon;

    public int getTotalPages() {
        totalPages = 0;
        for(Book b:books){
           totalPages += Integer.parseInt(b.getPageCount());
        }
        return totalPages;
    }

    private int totalPages;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Constructor
     */
    public BookList() {
        this.con = new JSONContext();
        this.apiCon = new BooksAPIContext();
        this.books = new ArrayList<>();
    }

    public BookList(List<Book> books) {
        this.books = books;
    }


    /**
     * Loads the books from JSON
     */
    public void getBooksFromJSON(){

        try {
            books = con.loadBooks();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Saves the structure to JSON format.
     */
    public void saveBooksToJSON(){
        con.saveBooks(books);
    }

    public void set(Book book, int position) {
        books.set(position, book);
    }

    public void remove(int position) {
        books.remove(position);
    }

    /**
     * Sorts the books collection by the number of their pages.
     */
    public void sortByPages(){
        books.sort(Comparator.comparing(Book::getPageCount));
    }
}
