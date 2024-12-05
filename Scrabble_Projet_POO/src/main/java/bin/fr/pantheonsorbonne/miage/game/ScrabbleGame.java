package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScrabbleGame {

    private List<Player> players;  // Liste des joueurs
    private TileBag tileBag;  // La pioche
    private int numberOfPlayers;  // Nombre de joueurs
    private Board board; // Plateau de jeu

    public static void main(String[] args) {


        ScrabbleGame game = new ScrabbleGame();  // Créer une instance du jeu
        game.startGame();  // Lancer la partie
    }

    // Méthode pour démarrer la partie
    public void startGame() {
        // Demander le nombre de joueurs
        Scanner scanner = new Scanner(System.in);
        System.out.print("Combien de joueurs (2 à 4) ? ");
        numberOfPlayers = scanner.nextInt();  // Récupérer le nombre de joueurs
        
        if (numberOfPlayers < 2 || numberOfPlayers > 4) {
            System.out.println("Nombre de joueurs invalide. Choisissez entre 2 et 4 joueurs.");
            return;
        }

        System.out.println("Nombre de joueurs : " + numberOfPlayers);

        // Créer la pioche (par défaut en français)
        tileBag = new TileBag("fr");

        // Créer le plateau
        board = new Board();

        // Créer les joueurs
        players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new StupidPlayer("Joueur " + (i + 1));  // Créer chaque joueur
            players.add(player);
            System.out.println(player.getName() + " est prêt à jouer !");
            // Distribuer 7 tuiles à chaque joueur depuis la pioche
            distributeTiles(player);
        }
/* 
        // Afficher les tuiles de chaque joueur
        displayPlayersTiles();

        // Afficher le plateau de jeu
        System.out.println("Plateau de jeu :");
        board.displayBoard(); // Afficher le plateau*/
    }

    // Méthode pour distribuer 7 tuiles aux joueurs
    private void distributeTiles(Player player) {
        for (int i = 0; i < 7; i++) {
            Tile tile = tileBag.drawTile();  // Tirer une tuile de la pioche
            if (tile != null) {
                player.addTile(tile);  // Ajouter la tuile au joueur
            }
        }
    }

    // Méthode pour afficher les tuiles de tous les joueurs
    public void displayPlayersTiles() {
        for (Player player : players) {
            System.out.print(player.getName() + " a les tuiles : ");
            for (Tile tile : player.getTilePile()) {
                System.out.print(tile.getLetter() + " ");  // Afficher la lettre de chaque tuile
            }
            System.out.println(" | Score : " + player.getScore()); // Afficher le score du joueur
        }
    }

    // Afficher le plateau
    public void displayBoard() {
        board.displayBoard();  // Appeler la méthode displayBoard() de la classe Board 

    }
}
