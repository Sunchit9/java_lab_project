package connection_package;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionClass
{
	public static Connection doConnect()
	{
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql://localhost/javaproject","root","");
			System.out.println("Connected....");
		} 
		catch (Exception e) 
		  {
			e.printStackTrace();
		  }
		return con;
	}
	public static void main(String[] args) 
	{
		doConnect();
	}

}
