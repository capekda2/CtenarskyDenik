package gui;

import data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookSearchDialog extends JDialog implements ActionListener {
    JLabel lblTitle = new JLabel("Book title: ");
    JTextField tfQuery = new JTextField("",20);
    JButton btnSearch = new JButton("Search");
    JButton btnBack = new JButton("Back");
    BookList books;
    BooksModel booksModel;

    public BookSearchDialog(JFrame owner, BookList books, BooksModel booksModel){
        super(owner, "Search for books", false);
        this.books = books;
        this.booksModel = booksModel;

        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        getContentPane().setLayout(new FlowLayout());

        getContentPane().add(lblTitle);
        getContentPane().add(tfQuery);
        getContentPane().add(btnSearch);
        getContentPane().add(btnBack);

        btnSearch.addActionListener(this);
        btnBack.addActionListener(this);

        pack();
        setLocation(200,200);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnBack){
            setVisible(false);
        }
        else if(e.getSource() == btnSearch ){
            if(tfQuery.getText() != ""){
                BooksAPIContext bac = new BooksAPIContext();
                Book bookRecord = bac.getFirstBook(tfQuery.getText());

                if(bookRecord == null){
                    JOptionPane.showMessageDialog(null, "No books found.");
                }else
                {
                    int result = JOptionPane.showConfirmDialog(null, "Book with title: "+ bookRecord.getTitle() + " found. Do you wish to add it to your journal?");
                    if(result == 0){
                        books.getBooks().add(bookRecord);
                        books.saveBooksToJSON();
                        this.setVisible(false);
                        booksModel.refresh();
                    }else{
                        //really nothing
                    }
                }


            }

        }

    }
}
