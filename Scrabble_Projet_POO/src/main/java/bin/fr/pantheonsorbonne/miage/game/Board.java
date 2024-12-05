package bin.fr.pantheonsorbonne.miage.game;

public class Board {
    private final Tile[][] grid;
    private final SpecialType[][] specialGrid;
    private static final int SIZE = 15;

    // Constructeur de la classe Board
    public Board() {
        grid = new Tile[SIZE][SIZE];
        specialGrid = new SpecialType[SIZE][SIZE];
        initializeBoard();
    }

    // Initialisation du plateau et des cases spéciales
    private void initializeBoard() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                specialGrid[x][y] = determineSpecialType(x, y); // Assigner le type spécial à chaque case
            }
        }
    }

    // Détermine le type spécial pour chaque case
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

    // Place une tuile sur le plateau
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

    // Applique l'effet spécial de la case (bonus)
    private void applySpecialEffect(Tile tile, int x, int y) {
        SpecialType specialType = specialGrid[x][y];
        switch (specialType) {
            case DOUBLE_LETTER:
                tile.setValue(tile.getValue() * 2);  // Applique un bonus de double lettre
                break;
            case TRIPLE_LETTER:
                tile.setValue(tile.getValue() * 3);  // Applique un bonus de triple lettre
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

    // Récupère la tuile en fonction de sa position sur le plateau
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Position hors du plateau.");
        }
        return grid[x][y];
    }

    // Récupère le type spécial d'une case en fonction de sa position
    public SpecialType getSpecialType(int x, int y) {
        return specialGrid[x][y];
    }

    // Affiche le plateau de jeu
    public void displayBoard() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (grid[x][y] != null) {
                    System.out.print(" " + grid[x][y].getLetter() + " "); // Affiche la lettre de la tuile
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
            System.out.println(); // Passer à la ligne suivante
        }
    }

}
