package bin.fr.pantheonsorbonne.miage.game;

public enum Language {
    FRENCH("Français", new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'},
           new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 8, 10, 8, 10, 10}),
    ENGLISH("English", new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'},
            new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 4, 6, 6, 6, 4, 2, 2, 1, 2, 1}),
    SPANISH("Español", new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'},
            new int[]{12, 4, 2, 6, 12, 6, 5, 5, 8, 10, 12, 5, 3, 8, 9, 7, 7, 9, 6, 6, 9, 8, 5, 4, 9, 10});

    private final String displayName; 
    private final char[] letters; 
    private final int[] letterValues; 

    Language(String displayName, char[] letters, int[] letterValues) {
        this.displayName = displayName;
        this.letters = letters;
        this.letterValues = letterValues;
    }

    public String getDisplayName() {
        return displayName;
    }

    public char[] getLetters() {
        return letters;
    }

    public int[] getLetterValues() {
        return letterValues;
    }

    public int getLetterValue(char letter) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == letter) {
                return letterValues[i];
            }
        }
        return 0; 
    }
}
