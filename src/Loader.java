import java.io.IOException;
import java.util.Scanner;

public class Loader {
    public static void main(String[] args) throws IOException {
//        System.out.println("Enter path to file: ");
//        Scanner scanner = new Scanner(System.in);
//        String str = scanner.nextLine();
//        System.out.println(str);
        Coder coder = new Coder("Books/text.txt");
        coder.code();
    }
}
