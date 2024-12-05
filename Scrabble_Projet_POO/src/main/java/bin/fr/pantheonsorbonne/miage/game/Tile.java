package bin.fr.pantheonsorbonne.miage.game;

public class Tile {
    private char letter; // Lettre de la tuile
    private int value; // Valeur de la tuile

    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    public char getLetter() {
        return letter;
    }

    public int getValue() {
        return value;
    }

    // Ajout de la méthode setValue() pour modifier la valeur d'une tuile
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return letter + "(" + value + ")";
    }
}
