package hr.fesb.java.chat.server;

import java.io.DataInputStream;
import java.util.HashMap;
import java.io.IOException;
import java.util.Map;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;


/**
 * Server class
 */
public class ChatServer {
  // constants are always public static final
  public static final int SERVER_PORT = 51593;

  // use final if possible (almost always)
  // prefer logger to system.out.println
  private static final Logger log = Logger.getLogger(ChatServer.class.getName());
  private final ServerSocket socket;
  private final Map<String, ClientHandler> clients = new HashMap<String, ClientHandler>();

  // never use static. almost never. define all class members
  // and put everything that would ever need to change in the constructor
  public ChatServer(ServerSocket socket) {
    this.socket = socket;
  }

  private void handleLogins() throws IOException {
    log.info("server is listening for connections...");
    while (true) {
      Socket sessionSocket = socket.accept();

      if (sessionSocket.isConnected()) {
        DataInputStream dis = new DataInputStream(sessionSocket.getInputStream());
        String name = dis.readUTF();

        // create a thread to handle input from this client and run it
        ClientHandler handler = new ClientHandler(sessionSocket, name);
        handler.run();

        this.clients.put(name, handler);
      }
    }
  }

  // main usually just starts the class going, no logic there (not nicely testable)
  public static void main(String args[]) {
    ChatServer server = null;
    try {
      ServerSocket socket = new ServerSocket(SERVER_PORT);
      socket.setSoTimeout(0);
      server = new ChatServer(socket);

      // start acepting connections
      server.handleLogins();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }
}
