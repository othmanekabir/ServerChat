import java.io.*;
import java.net.Socket;

public class ClientListner extends Thread {
    private Socket socketForClient;
    private Socket socketForSender;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String  clientUserName;
    private int portForSender;

    public Socket getSocketForSender() {
        return socketForSender;
    }

    public ClientListner(Socket sock, BufferedReader bufferedReader, BufferedWriter bufferedWriter, int port ){
        try{
            this.portForSender = port;
            this.socketForClient = sock;
            this.bufferedReader = bufferedReader;
            this.bufferedWriter = bufferedWriter;
            this.clientUserName = bufferedReader.readLine();
            sendToServer( clientUserName+" :" +clientUserName + " has entred the chat!");
            InfoSocket infoSocket = new InfoSocket(socketForClient,bufferedReader,bufferedWriter,clientUserName);
            Server.listMessage.add(infoSocket);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(socketForClient.isConnected()){
            try{

                String messageFromClient = bufferedReader.readLine();
                sendToServer(messageFromClient);
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private void sendToServer(String messageFromClient) {
        try{
            System.out.println(portForSender);
            this.socketForSender = new Socket("localhost",portForSender);

            BufferedWriter bufferedWriterForSender =  new BufferedWriter(new OutputStreamWriter(socketForSender.getOutputStream()));
            bufferedWriterForSender.write(messageFromClient);
            bufferedWriterForSender.newLine();
            bufferedWriterForSender.flush();
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }


}
