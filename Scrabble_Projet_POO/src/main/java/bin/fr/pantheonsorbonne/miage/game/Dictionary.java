package bin.fr.pantheonsorbonne.miage.game;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private Set<String> frenchWords;
    private Set<String> englishWords;
    private Set<String> spanishWords;

    public Dictionary() {
        frenchWords = new HashSet<>();
        englishWords = new HashSet<>();
        spanishWords = new HashSet<>();
        loadWords();
    }

    private void loadWords() {
        loadWordsFromFile("Scrabble_Projet_POO/src/main/resources/dictionnaires/dictionnaire_fr.txt", frenchWords);
        loadWordsFromFile("Scrabble_Projet_POO/src/main/resources/dictionnaires/dictionnaire_en.txt", englishWords);
        loadWordsFromFile("Scrabble_Projet_POO/src/main/resources/dictionnaires/dictionnaire_es.txt", spanishWords);
        
    }

    private void loadWordsFromFile(String filePath, Set<String> wordSet) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordSet.add(removeAccents(line.trim().toLowerCase()));
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier : " + filePath);
        }
    }

    private String removeAccents(String input) {
        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }
    public boolean isWordValid(String word, String language) {
        switch (language) {
            case "FRENCH":
                return frenchWords.contains(removeAccents(word.toLowerCase()));
            case "ENGLISH":
                return englishWords.contains(removeAccents(word.toLowerCase()));
            case "SPANISH":
                return spanishWords.contains(removeAccents(word.toLowerCase()));
            default:
                return false; 
        }
    }

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        System.out.println(dictionary.isWordValid("chat", "fr"));   
        System.out.println(dictionary.isWordValid("dog", "en"));    
        System.out.println(dictionary.isWordValid("gato", "es"));   
        System.out.println(dictionary.isWordValid("voiture", "fr"));
    }
}
