import java.util.*;
import java.io.*;
import LibraryComponents.*;
import transactionManager.*;
import userManagement.*;

public class LibraLogix {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        List<UserAccount> userAccounts = AccountStorage.loadAccounts();  // Load user accounts from storage
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to LibraLogix Library Management System");
        System.out.print("Do you have an account? (Y/N): ");
        String hasAccount = sc.nextLine(); // Get user input

        if (hasAccount.equalsIgnoreCase("y")) {
            System.out.print("Please enter Name: ");
            String name = sc.nextLine();
            UserAccount user = findUserByName(userAccounts, name);
            if (user != null) {
                run(user, catalog);
            } else {
                System.out.println("User not found. Please check your ID or create a new account.");
            }
        } else {
            System.out.println("Let's create a new account for you.");
            System.out.print("Please enter your full name: ");
            String fullName = sc.nextLine();
            System.out.print("Please enter your address: ");
            String address = sc.nextLine();
            UserAccount newUser = new UserAccount(fullName, address);
            userAccounts.add(newUser);
            AccountStorage.saveAccounts(userAccounts); // Save the updated list of accounts
            System.out.println("Account created successfully!");
            run(newUser, catalog);
        }
        sc.close();
    }

    // Function to handle user interaction
    private static void run(UserAccount user, Catalog catalog) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome back, " + user.getName() + "!");
        System.out.println("(1) Check out a book");
        System.out.println("(2) Return a book");
        System.out.println("(3) Renew a book");
        System.out.println("(4) Quit");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                checkoutBook(user, catalog, sc);
                break;
            case 2:
                returnBook(user, catalog, sc);
                break;
            case 3:
                renewBook(user, catalog, sc);
                break;
            case 4:
                System.out.println("Quitting...");
                break;
            default:
                System.out.println("Invalid option selected.");
                break;
        }
        sc.close();
    }

    // Additional methods used in run for processing different user commands
    private static void checkoutBook(UserAccount user, Catalog catalog, Scanner sc) {
        System.out.print("Please enter the title of the book you want to check out: ");
        String title = sc.nextLine();
        List<Book> matchingBooks = catalog.findBooksByTitle(title);

        if (matchingBooks.isEmpty()) {
            System.out.println("No books found with that title.");
            return;
        }

        Book bookToCheckout = matchingBooks.get(0); // Assuming the first book is the desired one
        Date checkoutDate = new Date(); // Current date and time

        // Calculate due date, e.g., 14 days from checkout
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkoutDate);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        Date dueDate = calendar.getTime();

        // Create a new transaction object with the correct constructor parameters
        Transaction transaction = new Transaction(bookToCheckout, checkoutDate, dueDate, null);
        TransactionProcessor transactionProcessor = new TransactionProcessor(transaction);
        transactionProcessor.checkOutBook(bookToCheckout, user, transaction);
    }

    private static void returnBook(UserAccount user, Catalog catalog, Scanner sc) {
        System.out.print("Please enter the title of the book you want to return: ");
        String title = sc.nextLine();
        List<Book> matchingBooks = catalog.findBooksByTitle(title);

        if (matchingBooks.isEmpty()) {
            System.out.println("No books found with that title in the catalog.");
            return;
        }

        Book bookToReturn = matchingBooks.get(0);
        List<Transaction> transactions = user.getTransactionHistory();
        Transaction transactionToReturn = null;
        for (Transaction transaction : transactions) {
            if (transaction.getBook().equals(bookToReturn) && transaction.getReturnDate() == null) {
                transactionToReturn = transaction;
                break;
            }
        }

        if (transactionToReturn == null) {
            System.out.println("No ongoing transaction found for this book with your account.");
            return;
        }

        Date returnDate = new Date(); // Current date as return date
        transactionToReturn.setReturnDate(returnDate); // Update the transaction with return date
        TransactionProcessor transactionProcessor = new TransactionProcessor(transactionToReturn);
        double fine = transactionProcessor.returnBook(bookToReturn, user, returnDate);

        if (fine > 0) {
            System.out.println("Book returned with a fine of $" + fine);
        } else {
            System.out.println("Book returned successfully with no fine.");
        }
        bookToReturn.setQuantityInLibrary(bookToReturn.getQuantityInLibrary() + 1);
    }

    private static void renewBook(UserAccount user, Catalog catalog, Scanner sc) {
        System.out.println("Please enter the title of the book you want to renew: ");
        String title = sc.nextLine();
        List<Book> matchingBooks = catalog.findBooksByTitle(title);

        if (matchingBooks.isEmpty()) {
            System.out.println("No books found with that title in the catalog.");
            return;
        }

        Book bookToRenew = matchingBooks.get(0);
        List<Transaction> transactions = user.getTransactionHistory();
        Transaction lastTransaction = null;

        for (Transaction transaction : transactions) {
            if (transaction.getBook().equals(bookToRenew) && transaction.getReturnDate() == null) {
                lastTransaction = transaction;
                break;
            }
        }

        if (lastTransaction == null) {
            System.out.println("No ongoing transaction found for this book with your account.");
            return;
        }

        System.out.print("Enter the number of days you want to extend the due date by: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number of days.");
            sc.next(); // to consume the incorrect input
            return;
        }
        int daysToAdd = sc.nextInt();
        sc.nextLine(); // to consume the newline after the integer input

        TransactionProcessor transactionProcessor = new TransactionProcessor(lastTransaction);
        transactionProcessor.renewBook(bookToRenew, user, daysToAdd); // Adapt parameters as needed

        System.out.println("Book renewed successfully for " + daysToAdd + " more days.");
    }

    // Helper method to find a user by name
    private static UserAccount findUserByName(List<UserAccount> userAccounts, String name) {
        for (UserAccount user : userAccounts) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }
}
