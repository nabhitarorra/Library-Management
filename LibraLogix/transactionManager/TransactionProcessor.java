package transactionManager;
import LibraryComponents.Book;
import userManagement.UserAccount;
import java.util.Date;
import java.util.List;

public class TransactionProcessor {
    private Transaction transaction;

    public TransactionProcessor(Transaction transaction) {
        this.transaction = transaction;
    }

    public void checkOutBook(Book book, UserAccount user, Transaction transaction) {
        if (book.getQuantityInLibrary() > 0 && user.checkPermissions()) {
            book.setQuantityInLibrary(book.getQuantityInLibrary() - 1);
            user.addTransaction(transaction);
            System.out.println("Book checked out successfully.");
        } else {
            System.out.println("Sorry, the book is not currently available or you don't have permission.");
        }
    }

    public void renewBook(Book book, UserAccount user, int additionalDays) {
        List<Transaction> transactions = user.getTransactionHistory();
        if (!transactions.isEmpty()) {
            Transaction lastTransaction = transactions.get(transactions.size() - 1);
            if (lastTransaction != null && lastTransaction.getReturnDate() == null) {
                lastTransaction.setRenewDate(additionalDays);
                System.out.println("Book renewed successfully.");
                System.out.println("The book is now due on: " + lastTransaction.getDueDate());
            } else {
                System.out.println("No valid transaction found to renew.");
            }
        } else {
            System.out.println("No transactions available to renew.");
        }
    }

    public double returnBook(Book book, UserAccount user, Date returnDate) {
        List<Transaction> transactions = user.getTransactionHistory();
        if (!transactions.isEmpty()) {
            Transaction lastTransaction = transactions.get(transactions.size() - 1);
            if (lastTransaction != null && lastTransaction.getReturnDate() == null) {
                lastTransaction.setReturnDate(returnDate);
                double fine = lastTransaction.calculateFine(returnDate);
                return fine;
            }
        }
        return 0.0;
    }
}
