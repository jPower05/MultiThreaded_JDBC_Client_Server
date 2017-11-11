public class MultiThreadedServerA2{
	
	public static void main(String args []){
		System.out.println("Hello Server");
		
		SocketServer s = new SocketServer();
		s.runServer();
	}
}