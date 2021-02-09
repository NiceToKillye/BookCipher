import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Coder {
    private final Path bookPath = Paths.get("Books/book.txt");
    private final Map<Character, ArrayList<String>> book = new HashMap<>();
    private final Path textPath;

    public Coder(String textPath) throws IOException {
        this.textPath = Paths.get(textPath);
        List<String> bookText = Files.readAllLines(bookPath);
        String buffer;
        for(int i = 0; i < bookText.size(); i++){
            buffer = bookText.get(i).toLowerCase();
            for(int j = 0; j < buffer.length(); j++){
                String position = i + ":" + j;
                Character character = buffer.charAt(j);
                if(book.containsKey(character)){
                    ArrayList<String> newArr = book.get(character);
                    newArr.add(position);
                    book.replace(character, book.get(character), newArr);
                }
                else{
                    ArrayList<String> newArr = new ArrayList<>();
                    newArr.add(position);
                    book.put(character, newArr);
                }
            }
        }
    }

    public void code() throws IOException {
        List<String> text = Files.readAllLines(textPath);
        List<String> codedText = new ArrayList<>();
        for(String line : text){
            String codedLine = codeLine(line.toLowerCase());
            codedText.add(codedLine);
        }
        String dataFile = "Books/codedText.txt";
        FileWriter writer = new FileWriter(dataFile);
        for(String str: codedText) {
            writer.write(str + System.lineSeparator());
        }
        writer.flush();
        writer.close();
    }

    private String codeLine(String string){
        String codedString = "(";
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == ' '){

                codedString = codedString.concat(") (");
            }
            else {
                int pos = (int) (Math.random() * book.get(string.charAt(i)).size());
                codedString = codedString.concat(book.get(string.charAt(i)).get(pos) + " ");
            }
            if(i + 1 == string.length()){
                codedString = codedString.concat(")");
            }
        }

        return codedString;
    }
}
