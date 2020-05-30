package dashboard;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Exception;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import car_management.CarManagementApplet;
import customer_entry.CustumerEntryApplet;
import customer_exit.CustomerExitApplet;
import driver_management.DriverManagementApplet;

public class DashBoard
{
	Image carI, customer_entryI, customer_exitI, driverI;
	JButton carB, customer_entryB, customer_exitB, driverB; 
	public DashBoard()
	{
		
		JFrame jfrm = new JFrame("An Event Example");
		jfrm.setLayout(new GridLayout(2,2));
		jfrm.setSize(900, 500);
		jfrm.setLocation(500, 200);
		jfrm.setVisible(true);
		
		jfrm.setDefaultCloseOperation(jfrm.EXIT_ON_CLOSE);
		
	  carI = new ImageIcon("F:\\java_files\\java_lab_file\\dashboard\\Car.png").getImage();
	  Image carII = carI.getScaledInstance(200,300,Image.SCALE_SMOOTH);
	  carB = new JButton("Cars Information");
	  carB.setIcon(new ImageIcon(carII));
	  customer_entryI = new ImageIcon("F:\\java_files\\java_lab_file\\dashboard\\CutomerEntry.png").getImage(); 
	  Image customer_entryII = customer_entryI.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
	  customer_entryB = new JButton("Customer Entry");
	  customer_entryB.setIcon(new ImageIcon(customer_entryII));
	  
	  customer_exitI = new ImageIcon("F:\\java_files\\java_lab_file\\dashboard\\CustomerExit.png").getImage(); 
	  Image customer_exitII = customer_exitI.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
	  customer_exitB = new JButton("Customer Exit");
	  customer_exitB.setIcon(new ImageIcon(customer_exitII));
	  
	  driverI = new ImageIcon("F:\\java_files\\java_lab_file\\dashboard\\Driver.png").getImage();
	  Image driverII = driverI.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
	  driverB = new JButton("Drivers Information");
	  driverB.setIcon(new ImageIcon(driverII));
	 
	  
	  jfrm.add(carB);
	  carB.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent ae) {
			  new CarManagementApplet();
			  }
			  });
	  
	  jfrm.add(customer_entryB);
	  customer_entryB.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent ae) {
			  new CustumerEntryApplet();
			  }
			  });
	  
	  jfrm.add(customer_exitB);
	  customer_exitB.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent ae) {
			  new CustomerExitApplet();
			  }
			  });
	  
      jfrm.add(driverB);
	  driverB.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent ae) {		  
			new DriverManagementApplet(); 
		  }
			  });

	}
   public static void main(String args[]) throws Exception
   {
	  SwingUtilities.invokeAndWait(new Runnable(){
		        public void run()
		        {
		        	new DashBoard();
		        }
			  } ); 
   }
}
