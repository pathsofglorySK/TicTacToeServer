import java.util.Random;
import java.util.Scanner;

public class TicTacToe
{
    public static final char SIGN_X = 'x';
    public static final char SIGN_O = 'o';
    public static final char SIGN_EMPTY = '.';
    public char[][] table;
    private Random random;
    private Scanner scanner;

    public static void game(String[] args)
    {
        new TicTacToe().game();
    }

    public TicTacToe()
    {
        random = new Random();
        scanner = new Scanner(System.in);
        table = new char[3][3];
    }

    public void game()
    {
        initTable();
        while (true) {
            turnPlayer();
            if (checkWin(SIGN_X, table)) {
                System.out.println("X WIN!");
                break;
            }
            if (isTableFull(table)) {
                System.out.println("Sorry, DRAW!");
                break;
            }
            turnPlayer();
            printTable();
            if (checkWin(SIGN_O, table)) {
                System.out.println("O WIN!");
                break;
            }
            if (isTableFull(table)) {
                System.out.println("Sorry, DRAW!");
                break;
            }
        }
        System.out.println("GAME OVER.");
        printTable();
    }

    public void initTable()
    {
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                this.table[row][col] = SIGN_EMPTY;
            }
        }
    }

    public void printTable()
    {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++)
                System.out.print(table[row][col] + " ");
            System.out.println();
        }
    }

    public void turnPlayer()
    {
        int x, y;
        do {
            System.out.println("Enter X and Y (1..3):");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y, table));
        table[x][y] = SIGN_X;
    }

    public boolean isCellValid(int x, int y, char[][] table)
    {
        this.table = table;
        if (x < 0 || y < 0 || x >= 3|| y >= 3)
            return false;
        return table[x][y] == SIGN_EMPTY;
    }

    public void turnSecondPlayer(char[][] table)
    {
        this.table = table;
        int x, y;
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
        } while (!isCellValid(x, y, table));
        this.table[x][y] = SIGN_O;
    }

    public boolean checkWin(char dot, char[][] table)
    {
        this.table = table;
        for (int i = 0; i < 3; i++)
            if ((table[i][0] == dot && table[i][1] == dot &&
                    table[i][2] == dot) ||
                    (table[0][i] == dot && table[1][i] == dot &&
                            table[2][i] == dot))
                return true;
        return (table[0][0] == dot && table[1][1] == dot &&
                table[2][2] == dot) ||
                (table[2][0] == dot && table[1][1] == dot &&
                        table[0][2] == dot);
    }

    public boolean isTableFull(char[][] table)
    {
        this.table = table;
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (table[row][col] == SIGN_EMPTY)
                    return false;
        return true;
    }

    public char[][] getTable() {
        return table;
    }

    public char getSignX() {
        return SIGN_X;
    }

    public char getSignO() {
        return SIGN_O;
    }
}