package data;

import javax.swing.table.AbstractTableModel;

public class BooksModel extends AbstractTableModel {

    BookList books;
    String [] names = {"Title", "Authors", "Description", "Page count", "Publisher", "Published Date"};

    public BooksModel(BookList books){this.books = books;}

    public String getColumnName(int col) {
        return names[col];
    }

    public int getRowCount() {
        return books.getBooks().size();
    }

    public int getColumnCount() {
        return 6;
    }

    public Object getValueAt(int row, int col) {
        String s = null;


        Book book = books.getBooks().get(row);

        switch (col) {
            case 0 :
                s = book.getTitle();
                break;
            case 1 :
                s = book.getAuthors();
                break;
            case 2 :
                s = book.getDescription();
                break;
            case 3 :
                s = book.getPageCount();
                break;
            case 4 :
                s = book.getPublisher();
                break;
            case 5 :
                s = book.getPublishedDate();
                break;
        }

        return s;
    }

    public void refresh() {
        fireTableDataChanged();
    }
}
