package bin.fr.pantheonsorbonne.miage.game;

public class ScrabbleGame {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le jeu Scrabble !");

     
        Player player = new Player("Alice");
        System.out.println("Joueur : " + player.getName());
        System.out.println("Langue active : " + player.getActiveLanguage());

        Tile tile = new Tile('A', 1);
        System.out.println("Tuile créée : Lettre - " + tile.getLetter() + ", Valeur - " + tile.getValue());

        Board board = new Board();
        board.placeTile(tile, 7, 7);
       

        player.addTile(new Tile('B', 3));
        System.out.println("Pile du joueur après ajout : " + player.getTilePile().size());
    }
    
}
