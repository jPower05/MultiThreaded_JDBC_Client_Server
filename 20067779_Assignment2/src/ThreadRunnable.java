import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ThreadRunnable implements Runnable{

	public Socket clientSocket = null;
	
	public ThreadRunnable(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		//thread logic
		
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())
			);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			String arg1;
			arg1 = in.readLine();
			String arg2 = "From the server";
			System.out.println("Client says " + arg1);
			out.println("Thanks for the message" + arg2);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
