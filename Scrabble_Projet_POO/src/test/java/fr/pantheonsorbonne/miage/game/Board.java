package fr.pantheonsorbonne.miage.game;

public class Board {
    private final Tile[][] grid;
    private static final int SIZE = 15;

    public Board() {
        grid = new Tile[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        // Placez les cases spéciales sur le plateau
    }

    public void placeTile(Tile tile, int x, int y) {
        // Vérifiez si l'emplacement est valide et placez la lettre
        grid[x][y] = tile;
    }
}
