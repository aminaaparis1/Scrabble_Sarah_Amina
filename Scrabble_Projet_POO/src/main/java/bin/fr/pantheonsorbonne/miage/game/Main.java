package bin.fr.pantheonsorbonne.miage.game;

public class Main {
    public static void main(String[] args) {
        
        ScrabbleGame game = new ScrabbleGame();
        
        
        game.startGame();  
        game.displayPlayersTiles();  
        game.playGame();
    }
}
