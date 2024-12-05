package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name; 
    private int score; 
    private Language activeLanguage; 
    private TileBag tileBag; 
    private List<Tile> tilePile; 

    private static final int MAX_TILES = 7;

    // Constructeur du joueur avec le nom et langue par défaut (Français)
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.activeLanguage = Language.FRENCH; // Langue par défaut
        this.tileBag = new TileBag(this.activeLanguage.name().toLowerCase()); // Utilisation du nom de l'énumération
        this.tilePile = new ArrayList<>();
        drawTiles(); // Remplir la pile de tuiles au début
    }

    // Getter pour le nom du joueur
    public String getName() {
        return name;
    }

    // Getter pour le score du joueur
    public int getScore() {
        return score;
    }

    // Ajouter des points au score du joueur
    public void addScore(int points) {
        this.score += points;
    }

    // Réduire le score du joueur
    public void reduceScore(int points) {
        this.score = Math.max(0, this.score - points); // Le score ne peut pas être négatif
    }

    // Getter pour la pile de tuiles du joueur
    public List<Tile> getTilePile() {
        return tilePile;
    }

    // Ajouter une tuile à la pile de tuiles
    public void addTile(Tile tile) {
        if (tilePile.size() < MAX_TILES) {
            tilePile.add(tile);
        }
    }

    // Retirer une tuile de la pile de tuiles
    public void removeTile(Tile tile) {
        tilePile.remove(tile);
    }

    // Getter pour la langue active du joueur
    public Language getActiveLanguage() {
        return activeLanguage;
    }

    // Changer la langue active du joueur et mettre à jour la pioche
    public void changeLanguage(Language newLanguage) {
        this.activeLanguage = newLanguage;
        this.tileBag = new TileBag(newLanguage.name().toLowerCase());  // Utilisation du nom de l'énumération
        drawTiles(); // Re-piocher les tuiles selon la nouvelle langue
    }

    // Méthode pour tirer les tuiles au début de la partie ou après un changement de langue
    private void drawTiles() {
        while (tilePile.size() < 7 && tileBag.getRemainingTiles() > 0) {
            Tile tile = tileBag.drawTile();
            if (tile != null) {
                tilePile.add(tile);
            }
        }
    }
    
    public abstract void penalizeOpponent(List<Player> players);

    // Vérifier si le joueur peut jouer (s'il a des tuiles)
    public boolean canPlay() {
        return !tilePile.isEmpty();
    }
}
