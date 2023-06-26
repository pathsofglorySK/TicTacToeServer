import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private static final int PORT = 12345;

    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for players to connect...");

            // Wait for the first player to connect
            Socket player1Socket = serverSocket.accept();
            System.out.println("Player 1 connected.");

            // Wait for the second player to connect
            Socket player2Socket = serverSocket.accept();
            System.out.println("Player 2 connected.");

            // Create a new game session for the players
            TicTacToeGameSession gameSession = new TicTacToeGameSession(player1Socket, player2Socket);
            gameSession.start();

            serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

class TicTacToeGameSession extends Thread
{
    private Socket player1Socket;
    private Socket player2Socket;
    private BufferedReader player1Reader;
    private BufferedReader player2Reader;
    private PrintWriter player1Writer;
    private PrintWriter player2Writer;
    private char[][] gameBoard;
    private boolean isPlayer1Turn;

    public TicTacToeGameSession(Socket player1Socket, Socket player2Socket) throws IOException
    {
        this.player1Socket = player1Socket;
        this.player2Socket = player2Socket;

        player1Reader = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
        player2Reader = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
        player1Writer = new PrintWriter(player1Socket.getOutputStream(), true);
        player2Writer = new PrintWriter(player2Socket.getOutputStream(), true);

        gameBoard = new char[3][3];
        isPlayer1Turn = true;

        // Initialize the game board
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                gameBoard[row][col] = '.';
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            // Send the initial game state to both players
            sendGameState();

            // Game loop
            while (true)
            {
                // Get the current player's socket and reader/writer
                Socket currentPlayerSocket = isPlayer1Turn ? player1Socket : player2Socket;
                BufferedReader currentPlayerReader = isPlayer1Turn ? player1Reader : player2Reader;
                PrintWriter currentPlayerWriter = isPlayer1Turn ? player1Writer : player2Writer;

                // Send the appropriate message to the current player
                currentPlayerWriter.println("YOUR_TURN");
                currentPlayerWriter.println("Enter X and Y coordinates (1-3):");

                // Receive the move from the current player
                String move = currentPlayerReader.readLine();
                int x = Integer.parseInt(move.split(" ")[0]) - 1;
                int y = Integer.parseInt(move.split(" ")[1]) - 1;

                // Update the game board
                gameBoard[x][y] = isPlayer1Turn ? 'X' : 'O';

                // Switch turns
                isPlayer1Turn = !isPlayer1Turn;

                // Send the updated game state to both players
                sendGameState();

                // Check for a win or draw
                if (checkWin('X'))
                {
                    player1Writer.println("X_WINS");
                    player2Writer.println("X_WINS");
                    break;
                }
                else if (checkWin('O'))
                {
                    player1Writer.println("O_WINS");
                    player2Writer.println("O_WINS");
                    break;
                }
                else if (isTableFull())
                {
                    player1Writer.println("DRAW");
                    player2Writer.println("DRAW");
                    break;
                }
            }

            // Close connections
            player1Socket.close();
            player2Socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    void sendGameState()
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                player1Writer.print(gameBoard[row][col] + " ");
                player2Writer.print(gameBoard[row][col] + " ");
            }
            player1Writer.println();
            player2Writer.println();
        }
        player1Writer.println();
        player2Writer.println();
    }

    private boolean checkWin(char dot)
    {
        for (int i = 0; i < 3; i++)
        {
            if ((gameBoard[i][0] == dot && gameBoard[i][1] == dot && gameBoard[i][2] == dot) ||
                    (gameBoard[0][i] == dot && gameBoard[1][i] == dot && gameBoard[2][i] == dot))
            {
                return true;
            }
        }
        return (gameBoard[0][0] == dot && gameBoard[1][1] == dot && gameBoard[2][2] == dot) ||
                (gameBoard[2][0] == dot && gameBoard[1][1] == dot && gameBoard[0][2] == dot);
    }

    private boolean isTableFull()
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (gameBoard[row][col] == '.')
                {
                    return false;
                }
            }
        }
        return true;
    }
}
