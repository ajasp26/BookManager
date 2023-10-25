package bookmanager;

import java.io.*;
import java.util.*;

/*
 * Author: Alex Jasper
 */

/**
 * A simple console-based book management application that
 * allows users to add, view, update, and delete book records.
 * This data is stored persistently in a CSV file.
 */
public class BookManager {
    private static final String FILE_NAME = "books.csv";
    private static Scanner scanner = new Scanner(System.in);

    /**
     * The main method that drives the program.
     */
    public static void main(String[] args) {
        List<String[]> bookList = loadData();

        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook(bookList);
                    break;
                case 2:
                    displayBooks(bookList);
                    break;
                case 3:
                    updateBook(bookList);
                    break;
                case 4:
                    deleteBook(bookList);
                    break;
                case 5:
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Loads book data from the CSV file into memory.
     *
     * @return List of books, with each book represented as a String array.
     */
    private static List<String[]> loadData() {
        List<String[]> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookDetails = line.split(",");
                books.add(bookDetails);
            }
        } catch (IOException e) {
            System.out.println("No existing file found. A new file will be created.");
        }
        return books;
    }

    /**
     * Adds a new book to the book list and saves the updated list to the file.
     *
     * @param bookList The current list of books.
     */
    private static void addBook(List<String[]> bookList) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();

        bookList.add(new String[] { title, author });
        saveData(bookList);
        System.out.println("Book added successfully!");
    }

    /**
     * Displays all books in the book list.
     *
     * @param bookList The current list of books.
     */
    private static void displayBooks(List<String[]> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (int i = 0; i < bookList.size(); i++) {
            String[] book = bookList.get(i);
            System.out.println((i + 1) + ". Title: " + book[0] + ", Author: " + book[1]);
        }
    }

    /**
     * Updates a book in the book list based on the user's input and saves the
     * updated list.
     *
     * @param bookList The current list of books.
     */
    private static void updateBook(List<String[]> bookList) {
        displayBooks(bookList);
        System.out.print("Enter the number of the book you want to update: ");
        int bookNumber = scanner.nextInt();
        scanner.nextLine(); 

        if (bookNumber < 1 || bookNumber > bookList.size()) {
            System.out.println("Invalid book number!");
            return;
        }

        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new author: ");
        String author = scanner.nextLine();

        bookList.set(bookNumber - 1, new String[] { title, author });
        saveData(bookList);
        System.out.println("Book updated successfully!");
    }

    /**
     * Deletes a book from the book list based on the user's input and saves the
     * updated list.
     *
     * @param bookList The current list of books.
     */
    private static void deleteBook(List<String[]> bookList) {
        displayBooks(bookList);
        System.out.print("Enter the number of the book you want to delete: ");
        int bookNumber = scanner.nextInt();

        if (bookNumber < 1 || bookNumber > bookList.size()) {
            System.out.println("Invalid book number!");
            return;
        }

        bookList.remove(bookNumber - 1);
        saveData(bookList);
        System.out.println("Book deleted successfully!");
    }

    /**
     * Saves the current state of book data into the CSV file.
     *
     * @param data The current list of books.
     */
    private static void saveData(List<String[]> data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String[] entry : data) {
                pw.println(entry[0] + "," + entry[1]);
            }
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
        }
    }
}
