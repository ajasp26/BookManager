package bookmanager;

/**
 *
 * @author ajasp
 */
import java.io.*;
import java.util.*;

public class BookManager {

    private static final String FILE_NAME = "books.csv";

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    createBook();
                    break;
                case 2:
                    displayBooks();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nBook Management System");
        System.out.println("1. Add a new book");
        System.out.println("2. Display all books");
        System.out.println("3. Update a book");
        System.out.println("4. Delete a book");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static List<String[]> loadData() {
        List<String[]> data = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        }
        return data;
    }

    private static void saveData(List<String[]> data) {
        try ( PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String[] entry : data) {
                pw.println(entry[0] + "," + entry[1]);
            }
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
        }
    }

    private static void createBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        List<String[]> data = loadData();
        data.add(new String[]{title, author});
        saveData(data);

        System.out.println("Book added successfully!");
    }

    private static void displayBooks() {
        List<String[]> data = loadData();
        System.out.println("\nBooks List:");
        for (String[] entry : data) {
            System.out.println("Title: " + entry[0] + ", Author: " + entry[1]);
        }
    }

    private static void updateBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter title of the book to update: ");
        String titleToUpdate = scanner.nextLine();

        List<String[]> data = loadData();
        boolean found = false;
        for (String[] entry : data) {
            if (entry[0].equalsIgnoreCase(titleToUpdate)) {
                System.out.print("Enter new title (press enter to skip): ");
                String newTitle = scanner.nextLine();
                System.out.print("Enter new author (press enter to skip): ");
                String newAuthor = scanner.nextLine();

                if (!newTitle.isEmpty()) {
                    entry[0] = newTitle;
                }
                if (!newAuthor.isEmpty()) {
                    entry[1] = newAuthor;
                }

                saveData(data);
                System.out.println("Book updated successfully!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Book not found!");
        }
    }

    private static void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter title of the book to delete: ");
        String titleToDelete = scanner.nextLine();

        List<String[]> data = loadData();
        boolean found = false;
        for (Iterator<String[]> iterator = data.iterator(); iterator.hasNext();) {
            String[] entry = iterator.next();
            if (entry[0].equalsIgnoreCase(titleToDelete)) {
                iterator.remove();
                saveData(data);
                System.out.println("Book deleted successfully!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Book not found!");
        }
    }
}
