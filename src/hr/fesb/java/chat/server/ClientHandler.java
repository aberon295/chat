package hr.fesb.java.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Simple client definition.
 */
public class ClientHandler extends Thread {
  private static final Logger log = Logger.getLogger(ClientHandler.class.getName());

  private final Socket socket;
  private final String name;

  public ClientHandler(Socket socket, String name){
    this.socket = socket;
    this.name = name;
  }

  @Override
  public void run() {
    log.info("client starting: " + name);
    String message = null;
    try {
      DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
      DataInputStream dis = new DataInputStream(this.socket.getInputStream());

      // run forever
      while (true) {
        message = dis.readUTF();
        if (!message.isEmpty()){
          log.info(this.name + " says: " + message);
        }
      }
    } catch (IOException e) {
        // well fuck it :) e.printStackTrace();
    }
  }
}
