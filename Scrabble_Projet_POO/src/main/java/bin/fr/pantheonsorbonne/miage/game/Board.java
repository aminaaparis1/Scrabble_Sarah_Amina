package bin.fr.pantheonsorbonne.miage.game;

public class Board {
    private final Tile[][] grid; // Grille des tuiles placées
    private final SpecialType[][] specialGrid; // Grille des cases spéciales
    private static final int SIZE = 15; // Taille du plateau (15x15)

    public Board() {
        grid = new Tile[SIZE][SIZE];
        specialGrid = new SpecialType[SIZE][SIZE];
        initializeBoard();
    }

    // Initialisation des cases spéciales du plateau
    private void initializeBoard() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                specialGrid[x][y] = determineSpecialType(x, y);
            }
        }
    }

    // Détermine le type de case spéciale à chaque position
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

    // Place une tuile sur le plateau à la position spécifiée
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

    // Applique l'effet de la case spéciale sur la tuile placée
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

    // Récupère la tuile placée à une position donnée
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Position hors du plateau.");
        }
        return grid[x][y];
    }

    // Récupère le type de case spéciale à une position donnée
    public SpecialType getSpecialType(int x, int y) {
        return specialGrid[x][y];
    }

    // Méthode pour afficher le plateau dans la console
    public void displayBoard() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (grid[x][y] != null) {
                    System.out.print(" " + grid[x][y].getLetter() + " "); // Affiche la lettre de la tuile
                } else {
                    switch (specialGrid[x][y]) {
                        case TRIPLE_WORD:
                            System.out.print(" TW "); // Case triple mot
                            break;
                        case DOUBLE_WORD:
                            System.out.print(" DW "); // Case double mot
                            break;
                        case TRIPLE_LETTER:
                            System.out.print(" TL "); // Case triple lettre
                            break;
                        case DOUBLE_LETTER:
                            System.out.print(" DL "); // Case double lettre
                            break;
                        case LANGUAGE_CHANGE:
                            System.out.print(" LC "); // Case changement de langue
                            break;
                        default:
                            System.out.print(" .. "); // Case neutre
                            break;
                    }
                }
            }
            System.out.println(); // Nouvelle ligne après chaque rangée
        }
    }

    public static void main(String[] args) {
        // Création d'une instance de Board
        Board board = new Board();
        
        // Affichage du plateau initial
        System.out.println("Plateau Scrabble :");
        board.displayBoard();
    }
    
}
