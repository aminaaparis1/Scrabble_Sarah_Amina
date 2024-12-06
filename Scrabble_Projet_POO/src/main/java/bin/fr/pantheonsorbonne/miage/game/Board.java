package bin.fr.pantheonsorbonne.miage.game;

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
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (grid[x][y] != null) {
                    System.out.print(" " + grid[x][y].getLetter() + " "); 
                } else {
                    switch (specialGrid[x][y]) {
                        case TRIPLE_WORD:
                            System.out.print(" TW ");
                            break;
                        case DOUBLE_WORD:
                            System.out.print(" DW ");
                            break;
                        case TRIPLE_LETTER:
                            System.out.print(" TL ");
                            break;
                        case DOUBLE_LETTER:
                            System.out.print(" DL ");
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

    public boolean isValidMove(String word, int startX, int startY, boolean isHorizontal, Player player) {
       
        if (isHorizontal && (startY + word.length() > SIZE)){
            return false;

        } 
        if (!isHorizontal && (startX + word.length() > SIZE)) {
            return false;
        }
    
      
        for (int i = 0; i < word.length(); i++) {
            int x = startX + (isHorizontal ? 0 : i);
            int y = startY + (isHorizontal ? i : 0);
    
            if (grid[x][y] != null && grid[x][y].getLetter() != word.charAt(i)) {
                return false; 
            }
        }
    
        
        Dictionary dictionary = new Dictionary();
        if (!dictionary.isWordValid(word, "fr")) {
            return false; 
        }
    
        return true;
    }
    
    public void placeWord(String word,int startX, int startY, boolean isHorizontal, Player player){
        for (int i = 0; i < word.length(); i++) {
            int x = startX + (isHorizontal ? 0 : i);
            int y = startY + (isHorizontal ? i : 0);
    
           
            grid[x][y] = new Tile(word.charAt(i), player.getTileValue(word.charAt(i)));
   
            
            player.removeTile(word.charAt(i));
        }
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
