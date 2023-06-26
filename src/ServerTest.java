import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    private TicTacToe ticTacToe;

    @BeforeEach
    public void setUp() {
        ticTacToe = new TicTacToe();
    }

    @Test
    public void testInitTable() {
        char[][] expectedTable = {
                {'.', '.', '.'},
                {'.', '.', '.'},
                {'.', '.', '.'}
        };
        ticTacToe.initTable();
        assertArrayEquals(expectedTable, ticTacToe.getTable());
    }

    @Test
    public void testIsCellValid_ValidCell_ReturnsTrue() {
        char[][] table = {
                {'.', '.', '.'},
                {'.', '.', '.'},
                {'.', '.', '.'}
        };
        assertTrue(ticTacToe.isCellValid(1, 1, table));
    }

    @Test
    public void testIsCellValid_InvalidCell_ReturnsFalse() {
        char[][] table = {
                {'.', '.', '.'},
                {'.', '.', '.'},
                {'.', '.', '.'}
        };
        assertFalse(ticTacToe.isCellValid(3, 3, table));
    }



    @Test
    public void testCheckWin_PlayerXWins_Horizontal() {
        char[][] table = {
                {'x', 'x', 'x'},
                {'.', '.', '.'},
                {'.', '.', '.'}
        };
        assertTrue(ticTacToe.checkWin('x', table));
    }

    @Test
    public void testCheckWin_PlayerOWins_Vertical() {
        char[][] table = {
                {'o', '.', '.'},
                {'o', '.', '.'},
                {'o', '.', '.'}
        };
        assertTrue(ticTacToe.checkWin('o', table));
    }

    @Test
    public void testIsTableFull_TableNotFull_ReturnsFalse() {
        char[][] table = {
                {'x', 'o', '.'},
                {'o', 'x', '.'},
                {'.', '.', '.'}
        };
        assertFalse(ticTacToe.isTableFull(table));
    }

    @Test
    public void testIsTableFull_TableFull_ReturnsTrue() {
        char[][] table = {
                {'x', 'o', 'x'},
                {'o', 'x', 'o'},
                {'o', 'x', 'o'}
        };
        assertTrue(ticTacToe.isTableFull(table));
    }
}
