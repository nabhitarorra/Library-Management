package LibraryComponents;
import java.util.List;
import java.util.ArrayList;

// Enum for book categories
enum Category {
    FANTASY, SCIENCE_FICTION, MYSTERY, ROMANCE, THRILLER, HORROR, NON_FICTION
}

public class Catalog {
    private List<Book> books; // Stores all books available in the library

    public Catalog() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> findBooksByTitle(String title) {
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    public List<Book> findBooksByCategory(Category category) {
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory() == category) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    public void updateBookQuantity(Book book, int newQuantity) {
        book.setQuantityInLibrary(newQuantity);
    }

    @Override
    public String toString() {
        return "Catalog{" + "books=" + books + '}';
    }
}
