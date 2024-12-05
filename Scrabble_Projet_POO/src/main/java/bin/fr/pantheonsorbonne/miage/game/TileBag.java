package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileBag {

    private List<Tile> tiles;

    public TileBag(String language) {
        tiles = new ArrayList<>();
        if (language.equals("fr")) {
            addFrenchTiles();  // Ajouter les tuiles françaises
        } else if (language.equals("en")) {
            addEnglishTiles();  // Ajouter les tuiles anglaises
        } else if (language.equals("es")) {
            addSpanishTiles();  // Ajouter les tuiles espagnoles
        } else {
            System.out.println("Langue non reconnue, pioche vide.");
        }
        Collections.shuffle(tiles); // Mélanger les tuiles
    }

    private void addFrenchTiles() {
        addTiles('A', 9, 1);
        addTiles('E', 15, 1);
        addTiles('I', 8, 1);
        addTiles('N', 6, 1);
        addTiles('O', 6, 1);
        addTiles('R', 6, 1);
        addTiles('S', 6, 1);
        addTiles('T', 6, 1);
        addTiles('U', 6, 1);
        addTiles('L', 5, 1);
        addTiles('D', 3, 2);
        addTiles('G', 2, 2);
        addTiles('M', 3, 2);
        addTiles('B', 2, 3);
        addTiles('C', 2, 3);
        addTiles('P', 2, 3);
        addTiles('F', 2, 4);
        addTiles('H', 2, 4);
        addTiles('V', 2, 4);
        addTiles('J', 1, 8);
        addTiles('Q', 1, 8);
        addTiles('K', 1, 10);
        addTiles('W', 1, 10);
        addTiles('X', 1, 10);
        addTiles('Y', 1, 10);
        addTiles('Z', 1, 10);
        addTiles('*', 2, 0);  // Joker
    }

    private void addEnglishTiles() {
        addTiles('A', 9, 1);
        addTiles('E', 12, 1);
        addTiles('I', 9, 1);
        addTiles('O', 8, 1);
        addTiles('R', 6, 1);
        addTiles('N', 6, 1);
        addTiles('T', 6, 1);
        addTiles('L', 4, 1);
        addTiles('S', 4, 1);
        addTiles('U', 4, 1);
        addTiles('D', 4, 2);
        addTiles('G', 3, 2);
        addTiles('B', 2, 3);
        addTiles('C', 2, 3);
        addTiles('M', 2, 3);
        addTiles('P', 2, 3);
        addTiles('F', 2, 4);
        addTiles('H', 2, 4);
        addTiles('V', 2, 4);
        addTiles('W', 2, 4);
        addTiles('Y', 2, 4);
        addTiles('K', 1, 5);
        addTiles('J', 1, 8);
        addTiles('X', 1, 8);
        addTiles('Q', 1, 10);
        addTiles('Z', 1, 10);
        addTiles('*', 2, 0);  // Joker
    }

    private void addSpanishTiles() {
        addTiles('A', 12, 1);
        addTiles('E', 12, 1);
        addTiles('O', 9, 1);
        addTiles('I', 6, 1);
        addTiles('S', 6, 1);
        addTiles('N', 5, 1);
        addTiles('R', 5, 1);
        addTiles('U', 5, 1);
        addTiles('L', 4, 1);
        addTiles('T', 4, 1);
        addTiles('D', 5, 2);
        addTiles('G', 2, 2);
        addTiles('C', 4, 3);
        addTiles('B', 2, 3);
        addTiles('M', 2, 3);
        addTiles('P', 2, 3);
        addTiles('H', 2, 4);
        addTiles('F', 1, 4);
        addTiles('V', 1, 4);
        addTiles('Y', 1, 4);
        addTiles('Q', 1, 5);
        addTiles('J', 1, 8);
        addTiles('Ñ', 1, 8);
        addTiles('X', 1, 8);
        addTiles('Z', 1, 10);
        addTiles('*', 2, 0);  // Joker
    }

    private void addTiles(char letter, int count, int value) {
        for (int i = 0; i < count; i++) {
            tiles.add(new Tile(letter, value));
        }
    }

    public Tile drawTile() {
        if (tiles.isEmpty()) {
            return null;  // Retourner null si la pioche est vide
        }
        return tiles.remove(0);  // Retirer et retourner la première tuile
    }

    public int getRemainingTiles() {
        return tiles.size();
    }
}
