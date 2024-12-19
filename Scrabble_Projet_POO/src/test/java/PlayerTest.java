import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import bin.fr.pantheonsorbonne.miage.game.Player;
import bin.fr.pantheonsorbonne.miage.game.SmartPlayer;
import bin.fr.pantheonsorbonne.miage.game.Tile;
public class PlayerTest {

    @Test
    public void testDrawTiles() {
        Player player = new SmartPlayer("Test Player");

        
        assertEquals(7, player.getTilePile().size());
    }

    @Test
    public void testCanFormWord() {
        Player player = new SmartPlayer("Test Player");

        
        player.addTile(new Tile('C', 3));
        player.addTile(new Tile('A', 1));
        player.addTile(new Tile('T', 1));

        assertTrue(player.canFormWord("CAT"));
        assertFalse(player.canFormWord("DOG"));
    }
}
