package com.twu.biblioteca;

import com.twu.biblioteca.LibraryElement.Book;
import com.twu.biblioteca.LibraryElement.BookList;
import com.twu.biblioteca.LibraryElement.Menu;
import com.twu.biblioteca.userOperation.SearchCatalog;
import com.twu.biblioteca.userOperation.Operation;
import com.twu.biblioteca.userOperation.Quit;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PrinterTest {
    private static final String WELCOME_MESSAGE = "Welcome to Bibliteca!";
    private ByteArrayOutputStream output;
    private Printer printer;
    private List<Book> books = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        printer = new Printer();
        books.add(new Book("Grimm's Fairy Tales", "Jacob Grimm", "2011"));
        books.add(new Book("Wuthering Heights", "Emily Bronte", "2011"));
        books.add(new Book("Moon and Sixpence", "W. Somerset", "2012"));
    }

    @Test
    public void testMessagePrinter() {
        printer.print(WELCOME_MESSAGE);
        assertThat(output.toString(), is("Welcome to Bibliteca!\n"));
    }

    @Test
    public void testBookPrinter() throws Exception {
        String expect =
                "Grimm's Fairy Tales\tJacob Grimm\t2011\n" +
                        "Wuthering Heights\tEmily Bronte\t2011\n" +
                        "Moon and Sixpence\tW. Somerset\t2012\n";
        printer.print(books);
        assertThat(output.toString(), is(expect));
    }

    @Test
    public void testMenunPrinter() throws Exception {
        BookList bookList = new BookList(books);
        Map<Integer, Operation> menuItem = new HashMap<>();
        menuItem.put(1, new SearchCatalog(bookList,printer));
        menuItem.put(2, new Quit());

        Menu menu = new Menu(menuItem);

        String expect =
                "1\tSearchCatalog\n2\tQuit\n";
        printer.print(menu);
        assertThat(output.toString(), is(expect));

    }
}