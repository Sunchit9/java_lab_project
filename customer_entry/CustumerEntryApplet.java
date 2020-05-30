package customer_entry;

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

import car_management.CarManagementApplet;
import connection_package.ConnectionClass;

@SuppressWarnings("serial")
public class CustumerEntryApplet extends JFrame implements ActionListener{
	JLabel capacityL,customernameL,customermobL,daysreqL,carselectedL,driverselectedL, carsL, driversL,notification;
	JTextField capacityT,customernameT,customermobT,daysreqT,carselectedT,driverselectedT ;
	JButton fetch, cancel, submit;
	List carsLi;
	List driversLi;
	Connection con;
	public CustumerEntryApplet() {
		  setVisible(true);
		  setLayout(new GridLayout(2,2));
		  setSize(900, 500);
		  setLocation(500, 200);
		  setVisible(true);
			
		  setDefaultCloseOperation(this.HIDE_ON_CLOSE);
		
		 setLayout(new FlowLayout(FlowLayout.CENTER));
		 setBackground(Color.CYAN);
		 
		 con = ConnectionClass.doConnect();
		 
		 capacityL = new JLabel("Capacity");
		 capacityT = new JTextField(20);
		 fetch = new JButton("Fetch");
		 
		 cancel = new JButton("CANCEL");
		 
		 submit = new JButton("SUBMIT");
		 
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
		 
		 carsL = new JLabel("Cars Available");
		 carsLi = new List();
		 
		 driversL = new JLabel("Drivers Available");
		 driversLi = new List();
		 
		 notification = new JLabel("");
		 //add labels to applet window
		add(capacityL);
		add(capacityT);
		add(fetch);
		fetch.addActionListener(this);
		add(carsL);
		add(carsLi);
		carsLi.addActionListener(this);
		add(driversL);
		add(driversLi);
		driversLi.addActionListener(this);
		add(customernameL);
		add(customernameT);
		add(customermobL);
		add(customermobT);
		add(daysreqL);
		add(daysreqT);
		add(carselectedL);
		add(carselectedT);
		add(driverselectedL);
		add(driverselectedT);
		add(submit);
		submit.addActionListener(this);
		add(cancel);
		cancel.addActionListener(this);
		add(notification);
	}
	public void actionPerformed(ActionEvent arg0) {
		String c_capacity = capacityT.getText();
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
				
				pst = con.prepareStatement("select car_no, car_name, car_capacity from carsinfo where car_capacity >= ? and car_status = 'false'");
				pst.setString(1, c_capacity);
				ResultSet result = pst.executeQuery();
				while(result.next())
				{
					carsLi.add(result.getString("car_no")+ "/"+result.getString("car_name")+ "/"+ result.getString("car_capacity"));
					
				}
				pst = con.prepareStatement("select driver_name, driver_mobile from driversinfo where driver_status = 'false'");
				result = pst.executeQuery();
				while(result.next())
				{
				  driversLi.add(result.getString("driver_name")+"/"+ result.getString("driver_mobile"));
				}
				notification.setText("Record Fetched");
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		  carselectedT.setText(carsLi.getSelectedItem());
		  driverselectedT.setText(driversLi.getSelectedItem());
		if(response.equals("SUBMIT"))
		{
			try {
				pst = con.prepareStatement("insert into logs value(null, ?, ?, ?, ?, ?, ?, ?, ?, ?,null,null)");
				pst.setString(1, c_name);
				pst.setString(2, c_mobile);
				pst.setString(3, daysreq);
				String cars[] = carselectedT.getText().split("/");
				String drivers[] = driverselectedT.getText().split("/");
				pst.setString(4, cars[0]);
				pst.setString(5, cars[1]);
				pst.setString(6, drivers[0]);
				pst.setString(7, drivers[1]);
				pst.setString(8, "true");
				pst.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
				pst.executeUpdate();
				
				pst = con.prepareStatement("update carsinfo set car_status = 'true' where car_no = ?");
				pst.setString(1, cars[0]);
				pst.executeUpdate();
				
				pst = con.prepareStatement("update driversinfo set driver_status = 'true' where driver_mobile = ?");
				pst.setString(1, drivers[1]);
				pst.executeUpdate();
				
				capacityT.setText("");
				customernameT.setText("");
			    customermobT.setText("");
				daysreqT.setText("");
				carselectedT.setText("");
				driverselectedT.setText("");
				carsLi.clear();
				driversLi.clear();
				
				notification.setText("Record Submitted");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
     }
	   public static void main(String args[]) throws Exception
	   {
		  SwingUtilities.invokeAndWait(new Runnable(){
			        public void run()
			        {
			        	new CustumerEntryApplet();
			        }
				  } ); 
	   }
}
