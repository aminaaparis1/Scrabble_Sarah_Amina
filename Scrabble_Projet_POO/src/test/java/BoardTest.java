import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import bin.fr.pantheonsorbonne.miage.game.Board;
import bin.fr.pantheonsorbonne.miage.game.Player;
import bin.fr.pantheonsorbonne.miage.game.SmartPlayer;
import bin.fr.pantheonsorbonne.miage.game.StupidPlayer;
import bin.fr.pantheonsorbonne.miage.game.Tile;

public class BoardTest {

    @Test
    public void testPlaceTile() {
        Board board = new Board();
        Tile tile = new Tile('A', 1);
        Player player = new StupidPlayer("Test Player");

        
        assertTrue(board.placeTile(tile, 7, 7, player));

       
        assertFalse(board.placeTile(new Tile('B', 3), 7, 7, player));
    }

    @Test
    public void testSpecialTileEffect() {
        Board board = new Board();
        Tile tile = new Tile('A', 1);
        Player player = new StupidPlayer("Test Player");

        
        board.placeTile(tile, 1, 1, player);

        
        assertEquals(2, tile.getValue());
    }


    @Test
    public void testIsValidMove() {
        Board board = new Board();
        Player player = new SmartPlayer("Test Player");
        assertTrue(board.isValidMove("AT", 7, 11, false, player));
        assertFalse(board.isValidMove("DOG", 0, 0, true, player));
    }
}
