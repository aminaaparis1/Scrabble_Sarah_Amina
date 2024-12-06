package bin.fr.pantheonsorbonne.miage.game;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Player {
    protected String name; 
    private int score; 
    private Language activeLanguage; 
    private TileBag tileBag; 
    protected List<Tile> tilePile; 

    private static final int MAX_TILES = 7;

  
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.activeLanguage = Language.FRENCH; 
        this.tileBag = new TileBag(this.activeLanguage.name().toLowerCase()); 
        this.tilePile = new ArrayList<>();
        drawTiles(); 
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
        this.score = Math.max(0, this.score - points); 
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
        this.tileBag = new TileBag(newLanguage.name()); 
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
    
    public abstract void penalizeOpponent(List<Player> players);

    
    public boolean canPlay() {
        return !tilePile.isEmpty();
    }

    public int chooseStartX() {
    return new Random().nextInt(0,15); 
}

public int chooseStartY() {
    return new Random().nextInt(0,15); 
}

public boolean chooseOrientation() {
    return new Random().nextBoolean(); 
}

public abstract String chooseWord(Board board, TileBag tileBag);

public void removeTile(char letter) {
    for (int i = 0; i < tilePile.size(); i++) { 
        if (tilePile.get(i).getLetter() == letter) {
            tilePile.remove(i); 
            return;
        }
    }
    throw new IllegalArgumentException("Le joueur ne possède pas la tuile : " + letter);
}

public int getTileValue(char letter) {
    for (Tile tile : tilePile) { 
        if (tile.getLetter() == letter) {
            return tile.getValue(); 
        }
    }
    throw new IllegalArgumentException("Le joueur ne possède pas la tuile : " + letter);
}



}
