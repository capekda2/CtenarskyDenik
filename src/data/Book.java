package data;

/**
 * Class for attributes of a book
 */
public class Book {

    private String title = "";
    private String authors = "";
    private String publisher= "";
    private String publishedDate = "";
    private String description = "";
    private String pageCount = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }


    /**
     * Empty constructor
     */
    public Book(){
    }

    /**
     * Creates a book instance with setting most of its attributes.
     * @param title
     * @param authors
     * @param publisher
     * @param publishedDate
     * @param description
     * @param pageCount
     */
    public Book(String title, String authors, String publisher, String publishedDate, String description, String pageCount) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
    }
}
