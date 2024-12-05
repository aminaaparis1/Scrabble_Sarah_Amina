package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileBag {
    private final List<Tile> tiles;

    public TileBag(Language language) {
        tiles = new ArrayList<>();
        // Ajouter les tuiles de la langue spécifique dans la pioche
        char[] letters = language.getLetters();
        int[] values = language.getLetterValues();
        
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < values[i]; j++) {
                tiles.add(new Tile(letters[i], values[i]));
            }
        }
        Collections.shuffle(tiles); // Mélanger les tuiles
    }

    public Tile drawTile() {
        if (tiles.isEmpty()) {
            return null; // Pas de tuiles à tirer
        }
        return tiles.remove(0); // Tirer la première tuile
    }

    public int getRemainingTiles() {
        return tiles.size();
    }
}
