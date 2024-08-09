package LibraryComponents;

public class Book {
    private String title;
    private int quantityInLibrary;
    private String author;
    private Category category;

    public Book(String title, int quantityInLibrary, String author, Category category) {
        this.title = title;
        this.quantityInLibrary = quantityInLibrary;
        this.author = author;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantityInLibrary() {
        return quantityInLibrary;
    }

    public void setQuantityInLibrary(int quantityInLibrary) {
        this.quantityInLibrary = quantityInLibrary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
