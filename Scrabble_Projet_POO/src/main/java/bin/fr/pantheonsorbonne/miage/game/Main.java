package bin.fr.pantheonsorbonne.miage.game;

public class Main {
    public static void main(String[] args) {
        // Créer une instance du jeu Scrabble
        ScrabbleGame game = new ScrabbleGame();
        
        // Démarrer la partie et afficher les informations demandées
        game.startGame();  // Démarre la partie
        game.displayPlayersTiles();  // Affiche les tuiles de chaque joueur
        game.displayBoard();  // Affiche le plateau de jeu
    }
}
