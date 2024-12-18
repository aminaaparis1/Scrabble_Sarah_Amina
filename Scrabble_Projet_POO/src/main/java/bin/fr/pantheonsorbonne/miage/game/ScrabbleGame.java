package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScrabbleGame {

    private List<Player> players;
    private TileBag tileBag;
   
    private Board board;

   

    public void startGame() {

       int numberOfPlayers=4;

       System.out.println("Nombre de joueurs : " + numberOfPlayers);

        tileBag = new TileBag("FRENCH");

        board = new Board();

        Random rand = new Random();
        Player player;

        players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            if (rand.nextBoolean()) {
                player = new StupidPlayer("Joueur " + (i + 1));
                players.add(player);
                System.out.println(player.getName() + " est un joueur stupide  !");

            } else {
                player = new SmartPlayer("Joueur " + (i + 1));
                players.add(player);
                System.out.println(player.getName() + " est un joueur intelligent !");
            }

            distributeTiles(player);
            
        }
        board.displayBoard();

    }

    public void distributeTiles(Player player) {
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
        if (word == null) {
            System.out.println(player.getName() + " n'a trouvé aucun mot valide.");
            System.out.println("Coup invalide. Le joueur passe son tour.");
            return;
        }
    
        int startX = player.chooseStartX(board);
        int startY = player.chooseStartY(board);
        boolean isHorizontal = player.chooseOrientation();
    
       
        if (board.isValidMove(word, startX, startY, isHorizontal, player)) {
            
                board.placeWord(word, startX, startY, isHorizontal, player);
    
               
                int wordScore = board.calculateWordScore(word, startX, startY, isHorizontal);
                player.addScore(wordScore);
                System.out.println("Score de " + player.getName() + " après ce tour : " + player.getScore());

                if (isWordReversed(word, board, startX, startY, isHorizontal)) {
                    System.out.println(player.getName() + " a posé un mot inversé !");
                    player.penalizeOpponent(players); 
                }
    
                while (player.getTilePile().size() < 7 && tileBag.getRemainingTiles() > 0) {
                    player.addTile(tileBag.drawTile());
                }
    
            } 
                
            
        else {
            System.out.println("Coup invalide. Le joueur passe son tour.");
        }
        board.displayBoard();
        displayPlayersTiles();
    }

    public boolean isWordReversed(String word, Board board, int startX, int startY, boolean isHorizontal) {
        for (int i = 0; i < word.length(); i++) {
            int x = startX + (isHorizontal ? 0 : i);
            int y = startY + (isHorizontal ? i : 0);
    
            Tile tile = board.getTile(x, y);
            if (tile == null) {
                return false; 
            }
    
            char tileLetter = tile.getLetter();
    
            if (Character.toLowerCase(tileLetter) != Character.toLowerCase(word.charAt(word.length() - 1 - i))) {
                return false; 
            }
        }
        return true; 
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

        while (!isGameOver() || !allPlayersBlocked()) {
            Player currentPlayer = players.get(turn % players.size());
            playTurn(currentPlayer);
            turn++;
        }

        if (isGameOver()) {
            System.out.println("Le sac est vide et tous les joueurs ont vidé leurs tuiles. Fin du jeu !");
        }
        if (allPlayersBlocked()) {
            System.out.println("Aucun joueur ne peut plus jouer. Fin du jeu !");
        }
        
        displayScores();
        announceWinner();
    }

    public boolean allPlayersBlocked() {
        Dictionary dictionary = new Dictionary();
        for (Player player : players) {
            if (player.canFormAnyWord(board, dictionary)) {
                return false; 
            }
        }
        return true; 
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
