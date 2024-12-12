package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Tile[][] grid;
    private final SpecialType[][] specialGrid;
    private static final int SIZE = 15;

    public Board() {
        grid = new Tile[SIZE][SIZE];
        specialGrid = new SpecialType[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                specialGrid[x][y] = determineSpecialType(x, y);
            }
        }
    }

    private SpecialType determineSpecialType(int x, int y) {
        if ((x == 0 && y == 0) || (x == 14 && y == 14) || (x == 0 && y == 14) || (x == 14 && y == 0)) {
            return SpecialType.TRIPLE_WORD;
        }
        if ((x == 1 && y == 1) || (x == 13 && y == 13) || (x == 1 && y == 13) || (x == 13 && y == 1)) {
            return SpecialType.DOUBLE_WORD;
        }
        if ((x + y) % 4 == 0) {
            return SpecialType.TRIPLE_LETTER;
        }
        if ((x + y) % 3 == 0) {
            return SpecialType.DOUBLE_LETTER;
        }
        if (x == 7 && y == 7) {
            return SpecialType.LANGUAGE_CHANGE;
        }
        return SpecialType.NONE;
    }

    public void placeTile(Tile tile, int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Position hors du plateau.");
        }
        if (grid[x][y] != null) {
            throw new IllegalArgumentException("Case déjà occupée.");
        }
        grid[x][y] = tile;
        applySpecialEffect(tile, x, y);
    }

    private void applySpecialEffect(Tile tile, int x, int y) {
        SpecialType specialType = specialGrid[x][y];
        switch (specialType) {
            case DOUBLE_LETTER:
                tile.setValue(tile.getValue() * 2);
                break;
            case TRIPLE_LETTER:
                tile.setValue(tile.getValue() * 3);
                break;
            case DOUBLE_WORD:
                System.out.println("Bonus double mot !");
                break;
            case TRIPLE_WORD:
                System.out.println("Bonus triple mot !");
                break;
            case LANGUAGE_CHANGE:
                System.out.println("Changement de langue activé !");
                break;
            case NONE:
                break;
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Position hors du plateau.");
        }
        return grid[x][y];
    }

    public SpecialType getSpecialType(int x, int y) {
        return specialGrid[x][y];
    }

    public void displayBoard() {
        System.out.print("    "); 
        for (int y = 0; y < SIZE; y++) {
            System.out.printf("%2d  ", y); 
        }
        System.out.println();
    
        for (int x = 0; x < SIZE; x++) {
            System.out.printf("%2d  ", x); 
            for (int y = 0; y < SIZE; y++) {
                if (grid[x][y] != null) {
                    System.out.print(" " + grid[x][y].getLetter() + " ");
                } else {
                    switch (specialGrid[x][y]) {
                        case TRIPLE_WORD -> System.out.print(" TW ");
                        case DOUBLE_WORD -> System.out.print(" DW ");
                        case TRIPLE_LETTER -> System.out.print(" TL ");
                        case DOUBLE_LETTER -> System.out.print(" DL ");
                        case LANGUAGE_CHANGE -> System.out.print(" LC ");
                        default -> System.out.print(" .. ");
                    }
                }
            }
            System.out.println();
        }
    }
    
    public boolean isValidMove(String word, int startX, int startY, boolean isHorizontal, Player player) {
        if (this.isEmpty()) { 
            if (!coversCenter(startX, startY, word.length(), isHorizontal)) {
                System.out.println("Mot rejeté (ne couvre pas la case centrale) : " + word);
                return false;
            }
        } else {
            if (!isConnectedToExistingWord(startX, startY, word, isHorizontal)) {
                System.out.println("Mot rejeté (pas connecté aux mots existants) : " + word);
                return false;
            }
        }
    
     
        for (int i = 0; i < word.length(); i++) {
            int currentX = startX + (isHorizontal ? 0 : i);
            int currentY = startY + (isHorizontal ? i : 0);
    
            if (currentX < 0 || currentX >= 15 || currentY < 0 || currentY >= 15) {
                System.out.println("Mot rejeté (en dehors des limites) : " + word);
                return false;
            }
    
         
            if (grid[currentX][currentY] != null &&
                grid[currentX][currentY].getLetter() != word.charAt(i)) {
                System.out.println("Mot rejeté (conflit avec une lettre existante) : " + word);
                return false;
            }
        }
    
        return true;
    }
    
    
    public boolean isConnectedToExistingWord(int startX, int startY, String word, boolean isHorizontal) {
        int x = startX;
        int y = startY;
    
        for (int i = 0; i < word.length(); i++) {
            
            if (isHorizontal) {
                
                if ((x > 0 && grid[x - 1][y] != null) || (x < 14 && grid[x + 1][y] != null)) {
                    return true;
                }
            } else {
              
                if ((y > 0 && grid[x][y - 1] != null) || (y < 14 && grid[x][y + 1] != null)) {
                    return true;
                }
            }
    
           
            if (grid[x][y] != null) {
                return true;
            }
    
          
            if (isHorizontal) {
                y++;
            } else {
                x++;
            }
    
            
            if (x < 0 || x >= 15 || y < 0 || y >= 15) {
                break; 
            }
        }
    
        return false;
    }
    
    

    public boolean coversCenter(int startX, int startY, int wordLength, boolean isHorizontal) {
        int centerX = 7;
        int centerY = 7;
    
        if (isHorizontal) {
            return startX == centerX && startY <= centerY && startY + wordLength - 1 >= centerY;
        } else {
            return startY == centerY && startX <= centerX && startX + wordLength - 1 >= centerX;
        }
    }
    

    public boolean isEmpty() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] != null) { 
                    return false; 
                }
            }
        }
        return true; 
    }
    public void placeWord(String word, int startX, int startY, boolean isHorizontal, Player player) {
    System.out.println("Tentative de placement du mot : " + word + " par " + player.getName());
    System.out.println("Tuiles actuelles du joueur : " + player.getTilePile());

    
    List<Character> requiredTiles = new ArrayList<>();
    for (char letter : word.toCharArray()) {
        requiredTiles.add(letter);
    }

    for (char letter : requiredTiles) {
        if (!player.hasTile(letter)) {
            System.out.println("Erreur : Le joueur ne possède pas la tuile : " + letter);
            throw new IllegalArgumentException("Le joueur ne possède pas toutes les tuiles nécessaires pour le mot.");
        }
    }

    
    for (int i = 0; i < word.length(); i++) {
        int x = startX + (isHorizontal ? 0 : i);
        int y = startY + (isHorizontal ? i : 0);

        char currentChar = word.charAt(i);
        System.out.println("Placement de la lettre '" + currentChar + "' en (" + x + ", " + y + ")");

       
        grid[x][y] = new Tile(currentChar, player.getTileValue(currentChar));
    }

    
    for (char letter : word.toCharArray()) {
        player.removeTile(letter);
    }

    System.out.println("Mot placé avec succès : " + word);
}

    
    
    public int calculateWordScore(String word, int startX, int startY, boolean isHorizontal) {
        int score = 0;
        int wordMultiplier = 1;

        for (int i = 0; i < word.length(); i++) {
            int x = startX + (isHorizontal ? 0 : i);
            int y = startY + (isHorizontal ? i : 0);

            Tile tile = grid[x][y];
            int letterScore = tile.getValue();

            switch (specialGrid[x][y]) {
                case DOUBLE_LETTER:
                    letterScore *= 2;
                    break;
                case TRIPLE_LETTER:
                    letterScore *= 3;
                    break;
                case DOUBLE_WORD:
                    wordMultiplier *= 2;
                    break;
                case TRIPLE_WORD:
                    wordMultiplier *= 3;
                    break;
            }

            score += letterScore;
        }

        return score * wordMultiplier;
    }

}
