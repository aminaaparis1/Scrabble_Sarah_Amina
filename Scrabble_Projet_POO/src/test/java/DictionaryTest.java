import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import bin.fr.pantheonsorbonne.miage.game.Dictionary;


public class DictionaryTest {

    @Test
    public void testLoadWords() {
        Dictionary dictionary = new Dictionary();

        
        assertTrue(dictionary.isWordValid("chat", "FRENCH"));
        assertTrue(dictionary.isWordValid("cat", "ENGLISH"));
        assertTrue(dictionary.isWordValid("gato", "SPANISH"));

      
        assertFalse(dictionary.isWordValid("xyz", "FRENCH"));
    }
}
