package customer_exit;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.SwingUtilities;

import java.time.LocalDate;

import connection_package.ConnectionClass;
import customer_entry.CustumerEntryApplet;

@SuppressWarnings("serial")
public class CustomerExitApplet extends JFrame implements ActionListener{
	JLabel customernameL,customermobL,daysreqL,carselectedL,driverselectedL,notification, billL;
	JTextField customernameT,customermobT,daysreqT,carselectedT,driverselectedT, billT;
	JButton fetch, cancel, bill;
	Connection con;
	public CustomerExitApplet() {
		 setSize(900, 500);
		  setLocation(500, 200);
		  setVisible(true);
			
		  setDefaultCloseOperation(this.HIDE_ON_CLOSE);
		 setBackground(Color.RED);
		
		 con = ConnectionClass.doConnect();
		 setLayout(new FlowLayout());
		 fetch = new JButton("Fetch");
		 
		 cancel = new JButton("CANCEL");
		 
		 bill = new JButton("Generate bill");
		 billL = new JLabel("Total Bill");
		 billT = new JTextField(20);
		 customernameL = new JLabel("Customer name");
		 customernameT = new JTextField(30);
		 
		 customermobL = new JLabel("Customer mobile");
		 customermobT = new JTextField(30);
		 
		 daysreqL = new JLabel("Days required");
		 daysreqT = new JTextField(30);
		 
		 carselectedL = new JLabel("Car selected");
		 carselectedT = new JTextField(30);
		 
		 driverselectedL = new JLabel("Driver alloted");
		 driverselectedT = new JTextField(30);
		 
		 notification = new JLabel("");
		 //add labels to applet window
		add(customermobL);
		add(customermobT);
		add(fetch);
		fetch.addActionListener(this);
		add(customernameL);
		add(customernameT);
		add(daysreqL);
		add(daysreqT);
		add(carselectedL);
		add(carselectedT);
		add(driverselectedL);
		add(driverselectedT);
		add(bill);
		bill.addActionListener(this);
		add(billL);
		add(billT);
		add(cancel);
		cancel.addActionListener(this);
		add(notification);
	}
	public void actionPerformed(ActionEvent arg0) {
		String c_name = customernameT.getText();
		String c_mobile = customermobT.getText();
		String daysreq = daysreqT.getText();
		String carselected = carselectedT.getText();
		String driverselected = driverselectedT.getText();
		String response = arg0.getActionCommand();
		PreparedStatement pst;
		if(response.equals("Fetch"))
		{
			try {
				
				pst = con.prepareStatement("select* from logs where customer_mobile = ? and status = 'true'");
				pst.setString(1, c_mobile);
				ResultSet result = pst.executeQuery();
				while(result.next())
				{
		          customernameT.setText(result.getString("customer_name"));
		          daysreqT.setText(result.getString("days_required"));
		          carselectedT.setText(result.getString("car_no")+"   "+result.getString("car_name"));
		          driverselectedT.setText(result.getString("driver_name")+"   "+result.getString("driver_mobile"));
				}
				notification.setText("Record Fetched");
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(response.equals("Generate bill"))
		{
			try {
				pst = con.prepareStatement("select car_capacity from logs, carsinfo where logs.car_no = carsinfo.car_no and status = 'true' and customer_mobile = ?");
				pst.setString(1, c_mobile);
				ResultSet result = pst.executeQuery();
				String capacity = "";
				while(result.next()) {
					capacity = result.getString("car_capacity");
				}
				int x = Integer.parseInt(capacity);
				int y = Integer.parseInt(daysreqT.getText());
				int total_charges = x*100 + y*200;
				billT.setText("Rs " + String.valueOf(total_charges));
				
				pst = con.prepareStatement("update carsinfo set car_status = 'false' where car_no  in(select car_no from logs where status = 'true' and customer_mobile = ?)");
				pst.setString(1, c_mobile);
				pst.executeUpdate();
				
				pst = con.prepareStatement("update driversinfo set driver_status = 'false' where driver_mobile  in(select driver_mobile from logs where status = 'true' and customer_mobile = ?)");
				pst.setString(1, c_mobile);
				pst.executeUpdate();
					
			    pst = con.prepareStatement("update logs set status = 'false', charges = ?, return_date = ? where status = 'true' and customer_mobile = ?");
				pst.setString(1, String.valueOf(total_charges));
				pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
				pst.setString(3, c_mobile);
				pst.executeUpdate();
				
				notification.setText("Bill Generated");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
     }
	public static void main(String args[]) throws Exception
	   {
		  SwingUtilities.invokeAndWait(new Runnable(){
			        public void run()
			        {
			        	new CustomerExitApplet();
			        }
				  } ); 
	   }
}
