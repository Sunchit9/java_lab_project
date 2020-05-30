package driver_management;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import connection_package.ConnectionClass;
import customer_exit.CustomerExitApplet;

@SuppressWarnings("serial")
public class DriverManagementApplet extends JFrame implements ActionListener{
	 
	 JLabel  drivermobnoL, drivernameL, driverageL, driveraddressL, notification; 
	 JTextField drivermobnoT, drivernameT, driverageT, driveraddressT;
	 JButton added, fetch, cancel, updated, deleted;
	 Connection con;
	public DriverManagementApplet() {
		 setSize(900, 500);
		  setLocation(500, 200);
		  setVisible(true);
			
		  setDefaultCloseOperation(this.HIDE_ON_CLOSE);
		 setLayout(new FlowLayout(FlowLayout.CENTER));
		 setBackground(Color.PINK);
		 
		 con = ConnectionClass.doConnect();
		 
		 drivernameL = new JLabel("Driver Name");
		 drivernameT = new JTextField(30);
		 
		 drivermobnoL = new JLabel("Driver Mobile");
		 drivermobnoT = new JTextField(10);
		 
		 driverageL = new JLabel("Driver Age");
		 driverageT = new JTextField(5);
		 
		 driveraddressL = new JLabel("Driver Address");
		 driveraddressT = new JTextField(50);
		 added = new JButton("Add");
		 cancel = new JButton("Exit");
		 updated = new JButton("Update");
		 deleted = new JButton("Delete");
		 fetch = new JButton("Fetch");
		 
		 notification = new JLabel();
		 
		 add(drivermobnoL);
		 add(drivermobnoT);
		 add(fetch);
		 fetch.addActionListener(this);
		 add(drivernameL);
		 add(drivernameT);
		 add(driverageL);
		 add(driverageT);
		 add(driveraddressL);
		 add(driveraddressT);
		 add(added);
		 added.addActionListener(this);
		 add(updated);
		 updated.addActionListener(this);
		 add(deleted);
		 deleted.addActionListener(this);
		 add(cancel);
		 cancel.addActionListener(this);
		 add(notification);
	}
	public void actionPerformed(ActionEvent arg0) {
		String D_mobile = drivermobnoT.getText();
		String D_name = drivernameT.getText();
		String D_age = driverageT.getText();
		String D_address = driveraddressT.getText();
		String res = arg0.getActionCommand();
		PreparedStatement pst ;
		if( (D_mobile.equals("") || D_name.equals("") || D_age.equals("") || D_address.equals("") ) && 
				( !res.equals("Delete") ) && ( !res.equals("Fetch") ))
		{
			//open dialog box;
		}
		else if(res.equals("Fetch"))
		{
		 try {
			pst = con.prepareStatement("select driver_name, driver_age, driver_address from driversinfo where driver_mobile= ?");
			pst.setString(1, D_mobile);
			ResultSet result = pst.executeQuery();
			String driver_result_name = "not found";
			String driver_result_age = "not found";
			String driver_result_address = "not found";
			while(result.next())
			{
				driver_result_name = result.getString("driver_name");
				driver_result_age = result.getString("driver_age");
				driver_result_address = result.getString("driver_address");
			}
			drivernameT.setText(driver_result_name);
			driverageT.setText(driver_result_age);
			driveraddressT.setText(driver_result_address);
			notification.setText("Record fetched");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		else if(res.equals("Add"))
		{
			try {
				pst = con.prepareStatement("insert into driversinfo values(null, ? , ? , ? , ?, ?)");
				
				pst.setString(1, D_mobile);
				pst.setString(2, D_name);
				pst.setString(3, D_age);
				pst.setString(4, D_address);
				pst.setString(5, "false");
				
				pst.executeUpdate();
				
				drivermobnoT.setText("");
				drivernameT.setText("");
				driverageT.setText("");
				driveraddressT.setText("");
				
				notification.setText("Record saved");
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(res.equals("Update"))
		{
			try {
				pst = con.prepareStatement("update driversinfo set driver_name = ?, driver_age = ?, driver_address = ? where driver_mobile = ?");
				pst.setString(1, D_name);
				pst.setString(2, D_age);
				pst.setString(3,  D_address);
				pst.setString(4, D_mobile);
				
				pst.executeUpdate();
				
				drivermobnoT.setText("");
				drivernameT.setText("");
				driverageT.setText("");
				driveraddressT.setText("");
				
				notification.setText("Record updated");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(res.equals("Delete"))
		{
			try {
				pst = con.prepareStatement("delete from driversinfo where driver_mobile = ?");
				pst.setString(1, D_mobile);
				
				pst.executeUpdate();
				pst.executeUpdate();
				
				drivermobnoT.setText("");
				drivernameT.setText("");
				driverageT.setText("");
				driveraddressT.setText("");
				
				notification.setText("Record deleted");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else//exit
		{
			
		}
	}
	 public static void main(String args[]) throws Exception
	   {
		  SwingUtilities.invokeAndWait(new Runnable(){
			        public void run()
			        {
			        	new DriverManagementApplet();
			        }
				  } ); 
	   }
}
