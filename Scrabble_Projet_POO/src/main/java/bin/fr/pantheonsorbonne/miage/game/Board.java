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

        if ((x == 0 && y == 13) || (x == 1 && y == 12) || (x == 1 && y == 6)) {
            return SpecialType.LANGUAGE_CHANGE;
        }
        
        return SpecialType.NONE;
    }

    public boolean placeTile(Tile tile, int x, int y, Player player) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            System.out.println("Position hors du plateau.");
            return false;
        }
        if (grid[x][y] != null) {
            System.out.println("Case déjà occupée.");
            return false;
        }
        grid[x][y] = tile;
        applySpecialEffect(tile, x, y, player);
        return true;
    }
    

    private void applySpecialEffect(Tile tile, int x, int y,  Player player) {
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
                languageChange(player);
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
       for(int x=0; x< SIZE; x++){
        for(int y=0; y< SIZE; y++){
            if(grid[x][y]!=null){
                System.out.print(" "+grid[x][y].getLetter()+"  ");
            }
            else {
                switch(specialGrid[x][y]){
                    case TRIPLE_WORD:
                    System.out.print(" TW ");
                    break;

                    case DOUBLE_WORD:
                    System.out.print(" DW ");
                    break;

                    case DOUBLE_LETTER:
                    System.out.print(" DL ");
                    break;

                    case TRIPLE_LETTER:
                    System.out.print(" TL ");
                    break;

                    case LANGUAGE_CHANGE:
                    System.out.print(" LC ");
                    break;

                    default: 
                    System.out.print(" .. ");
                    break;
                }
            }
        }
        System.out.println();
       }
    }

    public Language getNextLanguage(Language currentLanguage){
        Language[] languages = Language.values();
        int currentIndex=currentLanguage.ordinal();
        int nextIndex;
        if (currentIndex + 1 >= languages.length) {
            nextIndex = 0;
        } else {
            nextIndex = currentIndex + 1;
        } 
        return languages[nextIndex];
        
    }

    public void languageChange(Player player){
        Language currentLanguage=player.getActiveLanguage();
        Language newLanguage=getNextLanguage(currentLanguage);
        player.changeLanguage(newLanguage);

        TileBag currentTileBag=player.getTileBag();
        TileBag newTileBag=new TileBag(newLanguage.toString());

        while(newTileBag.getRemainingTiles()>0){
            Tile newTile=newTileBag.drawTile();
            currentTileBag.addTiles(newTile.getLetter(), 1, newTile.getValue());

        }
        
        System.out.println(player.getName() + " joue maintenant en " + newLanguage + " !");
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
    
        boolean isConnected = false;
    
        for (int i = 0; i < word.length(); i++) {
           
            if ((x > 0 && grid[x - 1][y] != null) || (x < 14 && grid[x + 1][y] != null) || 
                (y > 0 && grid[x][y - 1] != null) || (y < 14 && grid[x][y + 1] != null)) {
                isConnected = true;
            }
    
           
            if (grid[x][y] != null) {
                isConnected = true;
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
    
        return isConnected;
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
        
        if (grid[x][y] != null) {
            
            if (grid[x][y].getLetter() != currentChar) {
                throw new IllegalArgumentException("Conflit : lettre différente déjà placée sur la case (" + x + ", " + y + ")");
            } 
        }

        
       
        Tile tile = new Tile(currentChar, player.getTileValue(currentChar));
        placeTile(tile, x, y, player);
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