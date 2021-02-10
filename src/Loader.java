import java.io.IOException;

public class Loader {
    public static void main(String[] args) throws IOException {
        Coder coder = new Coder("Books/book.txt", "Books/text.txt");
        coder.code("Books/codedText.txt");
        coder.deCode("Books/codedText.txt", "Books/deCodedText.txt");
    }
}
