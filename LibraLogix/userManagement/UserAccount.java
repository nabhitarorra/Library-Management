package userManagement;
import transactionManager.Transaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fullName;
    private String address;
    private boolean hasElevatedPermissions;
    private boolean hasPatronAccess;
    private List<Transaction> transactionHistory;

    public UserAccount(String fullName, String address) {
        this.fullName = fullName;
        this.address = address;
        this.hasElevatedPermissions = false;
        this.hasPatronAccess = true;
        this.transactionHistory = new ArrayList<>();
    }

    public String getName() {
        return fullName;
    }

    public void setName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean checkPermissions() {
        return hasElevatedPermissions || hasPatronAccess;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }
}
