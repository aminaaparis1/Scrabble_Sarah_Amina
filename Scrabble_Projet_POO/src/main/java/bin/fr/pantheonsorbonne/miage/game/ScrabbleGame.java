package bin.fr.pantheonsorbonne.miage.game;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ScrabbleGame {

    private List<Player> players;  
    private TileBag tileBag;  
    private int numberOfPlayers;  
    private Board board; 

    public static void main(String[] args) {


        ScrabbleGame game = new ScrabbleGame(); 
        game.startGame();  
    }

  
    public void startGame() {

       
        Scanner scanner = new Scanner(System.in);
        System.out.print("Combien de joueurs (2 à 4) ? ");
        numberOfPlayers = scanner.nextInt();  
        
        if (numberOfPlayers < 2 || numberOfPlayers > 4) {
            System.out.println("Nombre de joueurs invalide. Choisissez entre 2 et 4 joueurs.");
            return;
        }

        System.out.println("Nombre de joueurs : " + numberOfPlayers);

        
        tileBag = new TileBag("FRENCH");

        
        board = new Board();


        Random rand=new Random();
        Player player;

        players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            if(rand.nextBoolean()){
                 player = new StupidPlayer("Joueur " + (i + 1));  
                players.add(player);
                System.out.println(player.getName() + " est un joueur stupide  !");

            } else{
                player = new SmartPlayer("Joueur " + (i + 1));
                players.add(player);
                System.out.println(player.getName() + " est un joueur intelligent !");
            }
           
            
            distributeTiles(player);
            board.displayBoard();
        }

    }

   
    private void distributeTiles(Player player) {
        for (int i = 0; i < 7; i++) {
            Tile tile = tileBag.drawTile();  
            if (tile != null) {
                player.addTile(tile);  
            }
        }
    }

    public void displayPlayersTiles() {
        for (Player player : players) {
            System.out.print(player.getName() + " a les tuiles : ");
            for (Tile tile : player.getTilePile()) {
                System.out.print(tile.getLetter() + " ");  
            }
            System.out.println(" | Score : " + player.getScore()); 
        }
    }

    
    public void displayBoard() {
        board.displayBoard();  

    }

    public void playTurn(Player player) {
        System.out.println(player.getName() + " joue son tour.");
    
        
        String word = player.chooseWord(board, tileBag);
        int startX = player.chooseStartX();
        int startY = player.chooseStartY(); 
        boolean isHorizontal = player.chooseOrientation();
    
        
        if (board.isValidMove(word, startX, startY, isHorizontal, player)) {
            
            board.placeWord(word, startX, startY, isHorizontal, player);
            System.out.println(player.getName() + " a placé le mot : " + word);
    
            
            int points = board.calculateWordScore(word, startX, startY, isHorizontal);
            player.addScore(points);
            System.out.println("Score gagné : " + points);
    
           
            refillPlayerTiles(player);
        } else {
            System.out.println("Coup invalide. Le joueur passe son tour.");
        }

        board.displayBoard();
        displayPlayersTiles();
    }
    
    private void refillPlayerTiles(Player player) {
        while (player.getTilePile().size() < 7) {
            Tile tile = tileBag.drawTile();
            if (tile != null) {
                player.addTile(tile);
            } else {
                break; 
            }
        }
    }

    public boolean isGameOver() {
        if (tileBag.getRemainingTiles() == 0) {
            for (Player player : players) {
                if (!player.getTilePile().isEmpty()) {
                    return false;
                }
            }
            return true; 
        }
        return false;
    }

    public void playGame() {
        int turn = 0;
    
        while (!isGameOver()) {
            Player currentPlayer = players.get(turn % players.size());
            playTurn(currentPlayer);
            turn++;
        }
    
      
        displayScores();
        announceWinner();
    }
    
    public void announceWinner() {
        Player winner = null;
        int highestScore = 0;
    
       
        for (Player player : players) {
            if (player.getScore() > highestScore) {
                highestScore = player.getScore();
                winner = player;
            }
        }
    
        
        if (winner != null) {
            System.out.println("Le vainqueur est " + winner.getName() + " avec " + highestScore + " points !");
        } else {
            System.out.println("Aucun vainqueur !");
        }
    }

    public void displayScores() {
        System.out.println("Scores des joueurs :");
        for (Player player : players) {
            System.out.println(player.getName() + " : " + player.getScore() + " points");
        }
    }
    
    
}
