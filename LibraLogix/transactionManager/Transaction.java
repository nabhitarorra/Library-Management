package transactionManager;
import LibraryComponents.Book;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Book book;
    private Date checkoutDate;
    private Date returnDate;
    private Date dueDate;

    public Transaction(Book book, Date checkoutDate, Date dueDate, Date returnDate) {
        this.book = book;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setRenewDate(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dueDate);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        this.dueDate = calendar.getTime();
    }

    public double calculateFine(Date currentDate) {
        if (currentDate.after(dueDate)) {
            long daysOverdue = TimeUnit.MILLISECONDS.toDays(currentDate.getTime() - dueDate.getTime());
            return daysOverdue * 1.50; // $1.50 per day overdue
        }
        return 0.0;
    }
}
