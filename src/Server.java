import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static List<InfoSocket> listMessage = new ArrayList<>();

    public void startServer(ServerSocket servSocket, int port_server) {

        try{
            while(!servSocket.isClosed()){
                port_server = port_server+1;

                Socket socket = servSocket.accept();
                System.out.println("A new client has connected!");
                BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                ClientSender clientSender = new ClientSender(port_server);
                clientSender.start();
                ClientListner clientListner = new ClientListner(socket, bufferedReader, bufferedWriter,port_server);
                clientListner.start();

            }
        }catch(IOException ex){
            ex.printStackTrace();

        }
    }

    public static void main(String[] args)throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server();
        int port_server = 1243;
        server.startServer(serverSocket, port_server);

    }

}
