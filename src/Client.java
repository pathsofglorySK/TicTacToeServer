import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "192.168.56.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            // Read and print the initial game state
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
            }

            // Game loop
            while (true) {
                String serverMessage = reader.readLine();

                if (serverMessage.equals("YOUR_TURN")) {
                    // It's the client's turn to make a move
                    System.out.println("Your turn. Enter X and Y coordinates (1-3):");
                    String move = consoleReader.readLine();
                    writer.println(move);
                } else {
                    // It's the other player's turn, or the game has ended
                    System.out.println(serverMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
