package hr.fesb.java.chat.client;

import hr.fesb.java.chat.server.ChatServer;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class ChatClient {
  private static final Logger log = Logger.getLogger(ChatClient.class.getName());

  private final String name;
  private final Socket socket;

  public ChatClient(String name, String server, int port) throws IOException {
    this.name = name;
    this.socket = new Socket(server, port);
    SendMessage(name);
  }

  public void SendMessage(String message) {
    try {
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      dos.writeUTF(message);
    } catch (IOException e) {
      log.info(e.getMessage());
    }
  }

  public static void main(String[] args) {
    try {
      ChatClient client = new ChatClient("test_cliet", "localhost", ChatServer.SERVER_PORT);
      client.SendMessage("kita");
    } catch (IOException e) {
      log.info("Neko je sranje: " + e.getMessage());
    }
  }
}
