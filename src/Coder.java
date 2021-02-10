import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Coder {
    private final Map<Character, ArrayList<String>> book = new HashMap<>();
    private final Path bookPath;
    private final Path textPath;

    public Coder(String bookPath, String textPath) {
        this.bookPath = Paths.get(bookPath);
        this.textPath = Paths.get(textPath);
    }

    public void code(String codedTextPath) throws IOException {
        List<String> text = Files.readAllLines(textPath);
        List<String> codedText = new ArrayList<>();

        for(String line : text){
            String codedLine = codeLine(line.toLowerCase());
            if(codedLine.equals("")){
                System.out.println("Text was not encoded");
                return;
            }
            codedText.add(codedLine);
        }

        writeFile(codedTextPath, codedText);
        System.out.println("Text was encoded");
    }

    public void deCode(String codedTextPath, String decodedTextPath) throws IOException {
        List<String> bookText = Files.readAllLines(bookPath);
        List<String> codedText = Files.readAllLines(Paths.get(codedTextPath));
        List<String> deCodedText = new ArrayList<>();

        for(String line : codedText){
            String decodedString = decodeLine(line, bookText);
            if(decodedString.equals("")){
                System.out.println("Unable to decode text");
                return;
            }
            deCodedText.add(decodedString);
        }

        writeFile(decodedTextPath, deCodedText);
        System.out.println("Text was decoded");
    }

    private String codeLine(String line){
        if(book.isEmpty()){
            try {
                createAlphabet();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String codedLine = "";
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == ' '){
                codedLine = codedLine.concat(" ");
            }
            else {
                if(!book.containsKey(line.charAt(i))){
                    return "";
                }
                else {
                    int pos = (int) (Math.random() * book.get(line.charAt(i)).size());
                    codedLine = codedLine.concat(book.get(line.charAt(i)).get(pos) + " ");
                }
            }
        }
        return codedLine;
    }

    private String decodeLine(String line, List<String> bookText){
        String decodedString = "";
        String[] letters = line.split(" ");
        for (String letter : letters) {
            if(letter.equals("")){
                decodedString = decodedString.concat(" ");
            }
            else {
                String[] coordinates = letter.split(":");
                int lineNumber = Integer.parseInt(coordinates[0]);
                int charNumber = Integer.parseInt(coordinates[1]);

                if(bookText.size() < lineNumber){
                    return "";
                }

                String lineOnNumber = bookText.get(lineNumber);
                if(lineOnNumber.length() < charNumber){
                    return "";
                }

                decodedString = decodedString.concat(
                        Character.toString(
                                lineOnNumber.charAt(charNumber))
                                .toLowerCase());
            }
        }
        return decodedString;
    }

    private void createAlphabet() throws IOException {
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

    private void writeFile(String path, List<String> text) throws IOException {
        FileWriter writer = new FileWriter(path);
        for(String str: text) {
            writer.write(str + System.lineSeparator());
        }
        writer.flush();
        writer.close();
    }
}
