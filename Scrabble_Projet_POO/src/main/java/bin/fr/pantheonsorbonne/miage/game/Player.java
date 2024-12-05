package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name; // Nom du joueur
    private int score; // Score actuel
    private Language activeLanguage; // Langue active du joueur
    private TileBag tileBag; // Pioche spécifique à la langue
    private List<Tile> tilePile; // Pile de tuiles du joueur

    private static final int MAX_TILES = 7;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.activeLanguage = Language.FRENCH; // Langue par défaut
        this.tileBag = new TileBag(this.activeLanguage); // Pioche spécifique à la langue active
        this.tilePile = new ArrayList<>();
        drawTiles(); // Remplir la pile de tuiles au début
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void reduceScore(int points) {
        this.score = Math.max(0, this.score - points); // Score ne peut pas être négatif
    }

    public List<Tile> getTilePile() {
        return tilePile;
    }

    public void addTile(Tile tile) {
        if (tilePile.size() < MAX_TILES) {
            tilePile.add(tile);
        }
    }

    public void removeTile(Tile tile) {
        tilePile.remove(tile);
    }

    public Language getActiveLanguage() {
        return activeLanguage;
    }

    
    public void changeLanguage(Language newLanguage) {
        this.activeLanguage = newLanguage;
        this.tileBag = new TileBag(newLanguage); 
        drawTiles();
    }

   
    private void drawTiles() {
        while (tilePile.size() < 7 && tileBag.getRemainingTiles() > 0) {
            Tile tile = tileBag.drawTile();
            if (tile != null) {
                tilePile.add(tile);
            }
        }
    }

    public boolean canPlay() {
        return !tilePile.isEmpty();
    }
}
