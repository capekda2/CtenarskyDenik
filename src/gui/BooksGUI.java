package gui;

import data.Book;
import data.BookList;
import data.BooksModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class BooksGUI extends JFrame implements ActionListener {

    //Buttons
    JButton btnDelete = new JButton("Delete");
    JButton btnSearchForBook = new JButton("Search for book");
    JButton btnEdit = new JButton("Edit");

    //Books
    BookList books = new BookList();
    BooksModel bookModel = new BooksModel(books);

    //Table
    JTable tbBooks = new JTable(bookModel);

    //Controls
    JTextField tfTitle = new JTextField(25);
    JTextField tfAuthors = new JTextField(25);
    JTextArea tfDescription = new JTextArea(8,25);
    JTextField tfPageCount = new JTextField(25);

    private BookSearchDialog dialog = new BookSearchDialog(this, books, bookModel);


    Action actionPageCount, actionSort, actionInfo;

    public BooksGUI() {
        super("Book journal");
        books.getBooksFromJSON(); // loads books from saved location

        initGui();
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
    }

    protected void initGui() {
        //Nastavit ikonu okna

        //actions, menu,
        createActions();
        createMenu();

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Book detail"));

        FocusListener autoSelectListener = new FocusAdapter() {

            public void focusGained(FocusEvent e) {
                JTextComponent tc = (JTextComponent) e.getSource();
                tc.selectAll();
            }

        };
        tfTitle.addFocusListener(autoSelectListener);
        tfTitle.setToolTipText("Title");
        tfAuthors.addFocusListener(autoSelectListener);
        tfAuthors.setToolTipText("Authors");
        tfDescription.addFocusListener(autoSelectListener);
        tfDescription.setToolTipText("Description");
        tfDescription.setLineWrap(true);
        tfDescription.setWrapStyleWord(true);
        tfPageCount.addFocusListener(autoSelectListener);
        tfPageCount.setToolTipText("Page count");




        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(10, 10, 10, 10);



        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        JLabel lblTitle = new JLabel("Title");
        p.add(lblTitle, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        p.add(tfTitle, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        JLabel lblAuthors = new JLabel("Authors");
        p.add(lblAuthors, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        p.add(tfAuthors, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        JLabel lblPageCount = new JLabel("Page count");
        p.add(lblPageCount,constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        p.add(tfPageCount,constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridheight = 8;
        JLabel lblDescription = new JLabel("Description");
        p.add(lblDescription,constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        p.add(tfDescription,constraints);





        //Control buttons
        btnSearchForBook.addActionListener(this);
        btnSearchForBook.setMnemonic('S');
        btnSearchForBook.setToolTipText("Searches a book from google");

        btnDelete.addActionListener(this);
        btnDelete.setMnemonic('D');
        btnDelete.setToolTipText("Deletes the selected record");

        btnEdit.addActionListener(this);
        btnEdit.setMnemonic('E');
        btnEdit.setToolTipText("Edits the selected record");

        constraints.gridy = 13;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        p.add(btnSearchForBook, constraints);
        constraints.gridx = 10;
        constraints.weightx = 0;
        p.add(btnEdit, constraints);
        constraints.gridx = 20;
        constraints.weightx = 0;
        p.add(btnDelete, constraints);



        add(p, BorderLayout.NORTH);

        //Tabulka ...
        //nastaveni zpusobu vyberu bunek - jednoduche ozn.
        tbBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tbBooks.setPreferredScrollableViewportSize(new Dimension(300,100));

        tbBooks
                .getSelectionModel()
                .addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        int pos = tbBooks.getSelectedRow();
                        if (pos != -1) {
                            Book rec = books.getBooks().get(pos);
                            tfTitle.setText(rec.getTitle());
                            tfAuthors.setText(rec.getAuthors());
                            tfDescription.setText(rec.getDescription());
                            tfPageCount.setText(rec.getPageCount());
                        }
                    }
                });
        tbBooks.setToolTipText("List of books");
        tbBooks.setRowHeight(30);
        JScrollPane sp = new JScrollPane(tbBooks);
        sp.setBorder(BorderFactory.createTitledBorder("Books"));
        add(sp, "Center");





    }


    private void createMenu() {
        JMenuBar menu = new JMenuBar();

        //File
        JMenu fileMenu = new JMenu("Program");
        fileMenu.setMnemonic('P');
        fileMenu.add(actionPageCount);
        fileMenu.add(actionSort);
        fileMenu.addSeparator();
        fileMenu.add(actionInfo);
        menu.add(fileMenu);

        setJMenuBar(menu);
    }

    private void createActions() {
        actionPageCount = new AbstractAction("Total page count") {

            public void actionPerformed(ActionEvent e) {
                showPageCount();
            }
        };

        actionInfo = new AbstractAction("Info") {

            public void actionPerformed(ActionEvent e) {
                about();
            }
        };

        actionSort = new AbstractAction("Sort by page count") {

            public void actionPerformed(ActionEvent e) {
                sortByPages();
            }
            };
        }





    private void sortByPages() {
        books.sortByPages();
        bookModel.refresh();
    }

    private void about(){
        JOptionPane.showMessageDialog(this,
                "READING JOURNAL\nThis program allows you to add and save information about books you have read!\n" +
                        "It is using Google Books API to search for necessary information about your books.\n Daniel Capek",
                "O programu",
                JOptionPane.INFORMATION_MESSAGE + JOptionPane.OK_OPTION
                );
    }

    private void showPageCount(){
        JOptionPane.showMessageDialog(this, "You have read total of: " + books.getTotalPages() +" pages!");
    }


    public void actionPerformed(ActionEvent e) {
        int position;

        if(e.getSource() == btnEdit){
            if((position = tbBooks.getSelectedRow()) != -1){
                Book book = new Book(tfTitle.getText(),
                        tfAuthors.getText(),
                        "",
                        "",
                        tfDescription.getText(),
                        tfPageCount.getText());

                books.set(book, position);
                bookModel.refresh();
                books.saveBooksToJSON();

            }
        }
        else if(e.getSource() == btnDelete){
            if((position = tbBooks.getSelectedRow()) != -1){
                if (JOptionPane.showConfirmDialog(this,"Do you really want to delete this record?","Confirm",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
                    books.remove(position);
                bookModel.refresh();
                books.saveBooksToJSON();
            }
        }
        else if(e.getSource() == btnSearchForBook){

            dialog.setVisible(true);
            bookModel.refresh();
        }

    }
}
