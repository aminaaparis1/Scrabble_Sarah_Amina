package bin.fr.pantheonsorbonne.miage.game;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    public TileBag getTileBag(){
        return this.tileBag;
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

    public int chooseStartX(Board board) {
        if (board.isEmpty()) {
            return 7; 
        }
        return chooseValidStartPosition(board)[0]; 
    }
    
    public int chooseStartY(Board board) {
        if (board.isEmpty()) {
            return 7; 
        }
        return chooseValidStartPosition(board)[1]; 
    }
    
    
    public int[] chooseValidStartPosition(Board board) {
        List<int[]> validPositions = findValidStartPositions(board);
    
        if (validPositions.isEmpty()) {
            
            return new int[]{new Random().nextInt(15), new Random().nextInt(15)};
        }
    
       
        return validPositions.get(new Random().nextInt(validPositions.size()));
    }
    
    
    public  List<int[]> findValidStartPositions(Board board) {
        List<int[]> validPositions = new ArrayList<>();
        for (int x = 0; x < 15; x++) {
          for (int y = 0; y < 15; y++) {
            if (board.getTile(x, y) == null) {
              if ((x > 0 && board.getTile(x - 1, y) != null) ||
                (x < 14 && board.getTile(x + 1, y) != null) ||
                (y > 0 && board.getTile(x, y - 1) != null) ||
                (y < 14 && board.getTile(x, y + 1) != null)) {
                 validPositions.add(new int[]{x, y});
                    }
                }
            }
        }
        return validPositions;
    }
    

public boolean chooseOrientation() {
    return new Random().nextBoolean(); 
}

public String chooseWord(Board board, TileBag tileBag) {
    Dictionary dictionary = new Dictionary();
    List<String> possibleWords = generatePossibleWords(dictionary);
    String bestWord = null;

  
    for (String word : possibleWords) {
       
        if (!dictionary.isWordValid(word, getActiveLanguage().name())) {
            System.out.println("Mot rejeté (invalide dans le dictionnaire) : " + word);
            continue;
        }

        if (!canFormWord(word)) {
            System.out.println("Mot rejeté (lettres manquantes) : " + word);
            continue;
        }
 
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                for (boolean isHorizontal : new boolean[]{true, false}) {
                   
                    if (board.isEmpty() && !board.coversCenter(x, y, word.length(), isHorizontal)) {
                        continue;
                    }

                    if (!board.isEmpty() && !board.isConnectedToExistingWord(x, y, word, isHorizontal)) {
                        continue;
                    }

                    if (board.isValidMove(word, x, y, isHorizontal, this)) {
                        bestWord = word; 
                        break;
                    }
                }
                if (bestWord != null) break;
            }
            if (bestWord != null) break;
        }
        if (bestWord != null) break;
    }

    
    if (bestWord != null) {
        System.out.println(getName() + " a choisi le mot : " + bestWord);
    } else {
        System.out.println(getName() + " n'a trouvé aucun mot valide.");
    }

    return bestWord; 
}


public List<String> generatePossibleWords(Dictionary dictionary) {
    Set<String> possibleWords = new HashSet<>(); 
    List<Tile> tiles = new ArrayList<>(this.getTilePile());
    generatePermutations("", tiles, possibleWords, dictionary);
    return new ArrayList<>(possibleWords);
}

 
public void generatePermutations(String prefix, List<Tile> tiles, Set<String> words, Dictionary dictionary) {
   
    if (!prefix.isEmpty() && dictionary.isWordValid(prefix, this.getActiveLanguage().name())) {
        words.add(prefix);
    }

    for (int i = 0; i < tiles.size(); i++) {
        Tile tile = tiles.get(i);
        List<Tile> remainingTiles = new ArrayList<>(tiles); 
        remainingTiles.remove(i);
        if (tile.getLetter() == '*') { 
            for (char letter = 'A'; letter <= 'Z'; letter++) {
                generatePermutations(prefix + letter, remainingTiles, words, dictionary);
            }
        } else {
            generatePermutations(prefix + tile.getLetter(), remainingTiles, words, dictionary);
        }
    }
}
public void removeTile(char letter) {
    for (int i = 0; i < tilePile.size(); i++) {
        Tile tile = tilePile.get(i);
        if (tile.getLetter() == letter || (letter != '*' && tile.getLetter() == '*')) {
            tilePile.remove(i);
            return; 
        }
    }
    throw new IllegalArgumentException("Le joueur ne possède pas la tuile : " + letter);
}

public int getTileValue(char letter) {
    for (Tile tile : tilePile) { 
        if (tile.getLetter() == letter || (letter != '*' && tile.getLetter() == '*')) {
            return tile.getValue(); 
        }
    }
    throw new IllegalArgumentException("Le joueur ne possède pas la tuile : " + letter);
}

public boolean canFormWord(String word) {
    List<Tile> tempTiles = new ArrayList<>(this.getTilePile()); 
    for (char letter : word.toCharArray()) {
        boolean letterFound = false;
        for (Tile tile : tempTiles) {
            if (tile.getLetter() == letter || tile.getLetter() == '*') {
                tempTiles.remove(tile);
                letterFound = true;
                break;
            }
        }
        if (!letterFound) {
            return false;
        }
    }
    return true; 
}

public boolean canFormAnyWord(Board board, Dictionary dictionary) {
    List<String> possibleWords = generatePossibleWords(dictionary); 
    for (String word : possibleWords) {
        if (canFormWord(word)) { 
            for (int x = 0; x < 15; x++) {
                for (int y = 0; y < 15; y++) {
                    for (boolean isHorizontal : new boolean[]{true, false}) {
                        if (board.isValidMove(word, x, y, isHorizontal, this)) {
                            return true; 
                        }
                    }
                }
            }
        }
    }
    return false; 
}

public void refillTiles(TileBag tileBag) {
    while (tilePile.size() < MAX_TILES && tileBag.getRemainingTiles() > 0) {
        Tile tile = tileBag.drawTile();
        if (tile != null) {
            addTile(tile); 
        }
    }
}

public boolean hasTile(char letter) {
    for (Tile tile : tilePile) {
        if (tile.getLetter() == letter || tile.getLetter() == '*') {
            return true;
        }
    }
    return false;
}


}
