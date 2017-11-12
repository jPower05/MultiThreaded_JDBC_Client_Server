import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientA2{
	
	public static void main(String[] args){
		String hostName = "127.0.0.1";
		int portNumber = 44444;
		
		Socket clientSocket;
		PrintWriter out;
		BufferedReader in;
		InputStreamReader ir;
		
		try{
			//create a client socket with ip address and portnumber
			clientSocket = new Socket(hostName, portNumber);
			
			//Create IO Streams
			out = new PrintWriter(clientSocket.getOutputStream(),true);
			ir = new InputStreamReader(clientSocket.getInputStream());
			in = new BufferedReader(ir);
			
			Scanner input = new Scanner(System.in);
			String id = input.next();
			String moduleName = "Game Design";
			
			
			out.println(id);
			out.println(moduleName);
			out.flush();
			
			
			System.out.println("Server : " + in.readLine());
		
		}
		catch(UnknownHostException e){
			e.getMessage();
			System.exit(1);
		}
		catch(IOException e){
			e.getMessage();
			System.exit(1);
			
		}
	}
	
	
	
	
	
	
	
}