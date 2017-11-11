import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer {
	int portNumber = 44444;
	ServerSocket serverSocket = null;
	
	public void runServer(){
		try{
			serverSocket = new ServerSocket(portNumber);
		}
		catch (IOException e){
			System.out.println(e.getMessage());
	
		}
		
		while (true){
			try{
				Socket clientSocket = serverSocket.accept();
				new Thread(new ThreadRunnable(clientSocket)).start();
			}
			catch (IOException e){
				System.out.println(e.getMessage());
			}
		}
	}
}
