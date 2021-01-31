package img;

import data.Book;
import data.BookList;
import data.BooksAPIContext;
import gui.BooksGUI;

import javax.swing.*;
import java.util.ArrayList;

public class JournalApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame f = new BooksGUI();

                f.pack();
                f.setVisible(true);
            }
        });
    }
}
