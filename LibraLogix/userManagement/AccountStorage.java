package userManagement;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class AccountStorage {
    private static final String FILE_NAME = "accountStorage.ser";

    public static void saveAccounts(List<UserAccount> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static List<UserAccount> loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<UserAccount>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Account file not found, creating a new one.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
