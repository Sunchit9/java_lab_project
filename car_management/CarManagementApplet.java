package car_management;

import connection_package.ConnectionClass;
import dashboard.DashBoard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

@SuppressWarnings("serial")
public class CarManagementApplet extends JFrame implements ActionListener{
	 JLabel carnameL, carnoL, carcapacityL , notification; 
	 JTextField carnameT, carnoT, carcapacityT;
	 JButton added, fetch, cancel, updated, deleted;
	 Connection con;
	 public CarManagementApplet(){
		    setVisible(true);
			setLayout(new GridLayout(2,2));
			setSize(900, 500);
		    setLocation(500, 200);
		    setVisible(true);
			
			setDefaultCloseOperation(this.HIDE_ON_CLOSE);
		 con = ConnectionClass.doConnect();
		 
		 setLayout(new FlowLayout(FlowLayout.CENTER));
		 setBackground(Color.GRAY);
		 
		 carnameL = new JLabel("Name of car");
		 carnameT = new JTextField(30);
		 
		 carnoL = new JLabel("Car number");
		 carnoT = new JTextField(10);
		 
		 carcapacityL = new JLabel("Capacity of car");
		 carcapacityT = new JTextField(30);
		 
		 added = new JButton("Add");
		 cancel = new JButton("Exit");
		 updated = new JButton("Update");
		 deleted = new JButton("Delete");
		 fetch = new JButton("Fetch");
		 
		 notification = new JLabel();
		 
		 add(carnoL);
		 add(carnoT);
		 add(fetch);
		 fetch.addActionListener(this);
		 add(carnameL);
		 add(carnameT);
		 add(carcapacityL);
		 add(carcapacityT);
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
		String c_name = carnameT.getText();	
		String c_no = carnoT.getText();
		String c_capacity = carcapacityT.getText();
		String response = arg0.getActionCommand();
		PreparedStatement pst;
		if( ( c_name.equals("") || c_no.equals("") || c_capacity.equals("") ) && 
				( !response.equals("Delete") ) && ( !response.equals("Fetch") ))
		{
			//open dialog box;
		}
		else if(response.equals("Fetch")) {
			try {
				pst = con.prepareStatement("select car_name , car_capacity from carsinfo where car_no = ?");
				pst.setString(1, c_no);
				ResultSet res = pst.executeQuery();
				String cresult_name = "notFound", cresult_capacity = "notFound";
				while(res.next())
				{
					cresult_name = res.getString("car_name");
					cresult_capacity = res.getString("car_capacity");
				}
				carnameT.setText(cresult_name);
				carcapacityT.setText(cresult_capacity);
				notification.setText("Record fetched");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(response.equals("Add")) {
			try {
				pst = con.prepareStatement("insert into carsinfo values(null, ?, ?, ?, ?)");
				pst.setString(1, c_no);
				pst.setString(2, c_name);
				pst.setString(3, c_capacity);
				pst.setString(4, "false");
				pst.executeUpdate();
				
				carnameT.setText("");
				carnoT.setText("");
				carcapacityT.setText("");
				
				notification.setText("Record saved");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		else if(response.equals("Update")){
			try {
				pst = con.prepareStatement("update carsinfo set car_name = ? , car_capacity = ? where car_no = ?");
				pst.setString(1, c_name);
				pst.setString(2, c_capacity);
				pst.setString(3, c_no);
				pst.executeUpdate();
				
				carnameT.setText("");
				carnoT.setText("");
				carcapacityT.setText("");
				
				notification.setText("Record updated");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		else if(response.equals("Delete"))
		{
			try {
				pst = con.prepareStatement("delete from carsinfo where car_no = ?");
				pst.setString(1, c_no);
				
				pst.executeUpdate();
				
				carnameT.setText("");
				carnoT.setText("");
				carcapacityT.setText("");
				
				notification.setText("Record deleted");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else //for exit
		{
			
		}
	}
	   public static void main(String args[]) throws Exception
	   {
		  SwingUtilities.invokeAndWait(new Runnable(){
			        public void run()
			        {
			        	new CarManagementApplet();
			        }
				  } ); 
	   }
}
