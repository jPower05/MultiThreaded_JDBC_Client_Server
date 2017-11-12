import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ThreadRunnable implements Runnable{
	
	public static boolean connected = false;

	public Socket clientSocket = null;
	
	public ThreadRunnable(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		//thread logic
		try{
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			String studentId;
			String moduleName;
			studentId = in.readLine();
			//System.out.println("input =  " + studentId );
			moduleName = in.readLine();
			//System.out.println("input = " + moduleName);
			
			String output = "";
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradedatabase" ,"root", "");
			Statement st=con.createStatement();		
			String query = ("SELECT * FROM `students` WHERE `STUD_ID` = " + studentId);
			//System.out.println(query);
			
			//QUERY TO CHECK VALID USER
			
			ResultSet rs=st.executeQuery("SELECT * FROM `students` WHERE `STUD_ID` = '" + studentId + "'");
			
			if(rs.next()) {
				connected = true;
				System.out.println("Connection status = " + connected);
				output = ("Welcome Student " + studentId);
			}
			else{
				out.println("Invalid Student Number. Closing Connection");
				con.close();
			    return;
			}
			//IF VALID USER
			if (connected){
				
				//QUERY TO GET GRADES
				
				query = ("SELECT modulegrades.ModuleName, modulegrades.CA_Mark, modulegrades.Exam_Mark, modulegrades.STUD_ID"
						+ " FROM modulegrades"
						+ " INNER JOIN students ON students.STUD_ID = modulegrades.STUD_ID"
						+ " WHERE modulegrades.ModuleName = '" + moduleName + "' AND modulegrades.STUD_ID = '" + studentId + "'");
				
				rs=st.executeQuery(query);
				
				String modName = "";
				int caMark = 0;
				int examMark = 0;
				int stuId = 0;
				double overall_grade = 0;
				if (rs.next()){
					modName = rs.getString(1);
					System.out.println("Module Name = "+ modName);
					
					caMark = rs.getInt(2);
					System.out.println("CA_Mark = " + caMark);
					
					examMark = rs.getInt(3);
					System.out.println("Exam Mark = " + examMark);
					
					stuId = rs.getInt(4);
					System.out.println("Student Id = " + stuId);
					
					//calculating the overall grade 70% exam + 30% ca
					overall_grade = ((caMark / 100.0 * 30 / 1) + (examMark / 100.0 * 70 / 1));
					String grade = String.format("%.2f", overall_grade);
					
					
					output = (stuId + " " + modName + " " + caMark + " " + examMark + " " + grade);
					
					
					
				}
				else{
					out.println("Not a valid module name " + modName);
					
				}
			}
			out.println(output);

		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	
	
	

}
