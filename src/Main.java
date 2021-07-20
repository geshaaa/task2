import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        HashSet<String> words = new HashSet<>();
        ArrayList<String> startWords = new ArrayList<>();

        String wordsProviderUrl = "https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt";

        try {
            Scanner scanner = new Scanner(new URL(wordsProviderUrl).openStream(),
                    StandardCharsets.UTF_8.toString());
            scanner.useDelimiter(Pattern.compile("(\\n)"));
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (word.length() > 0 && word.length() < 9) {
                    words.add(word);
                } else if (word.length() == 9) {
                    startWords.add(word);
                }
            }

            words.add("I");
            words.add("A");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        List<String> result = startWords.stream()
                .filter(word -> checkIfValid(word, words))
                .collect(Collectors.toList());

        System.out.println(result);

    }

    private static boolean checkIfValid(String word, HashSet<String> words) {
        if (word.length() == 1) {
            return words.contains(word);
        }

        for (int i = 0; i < word.length(); i++) {
            String newWord = word.substring(0, i) + word.substring(i + 1);
            if (words.contains(newWord) && checkIfValid(newWord, words)) {
                return true;
            }
        }
        return false;
    }
}
