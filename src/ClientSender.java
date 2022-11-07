import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSender extends Thread{




    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    private ServerSocket serverSocket;

    public ClientSender(int port) throws IOException {
         serverSocket = new ServerSocket(port);


    }
    @Override
    public void run()  {

        String messageFromClient;
        while(!serverSocket.isClosed()){
            try{
                Socket s = serverSocket.accept();
                BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            }catch(IOException ex){
                ex.printStackTrace();
                break;
            }
        }


    }

    private void broadcastMessage(String messageFromClient) {
        String sender = getSenderFromMessage(messageFromClient);
        String message = getMessageFromMessage(messageFromClient);
        for(InfoSocket infoSocket : Server.listMessage){
            if(!infoSocket.getClientUserName().equals(sender)){
                try {
                    infoSocket.getBufferedWriter().write(message);
                    infoSocket.getBufferedWriter().newLine();
                    infoSocket.getBufferedWriter().flush();
                }catch(IOException exp){
                    exp.printStackTrace();
                }
            }

        }
    }

    private String getSenderFromMessage(String message){
        return message.split(":")[0];
    }

    private String getMessageFromMessage(String message){
        return message.split(":")[0];
    }


}
