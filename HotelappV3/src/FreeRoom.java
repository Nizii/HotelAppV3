import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;

/*
* @author  Özdemir Nizam
*/

public class FreeRoom {

	private JFrame frame;
	private String currentRoom;
	private static ArrayList<String> roomList = new ArrayList<>();
	private static ArrayList<String> bookingList = new ArrayList<>();
	private JTextField tf_booking_id;
	private JTextField tf_booked;
	private JTextField tf_firstname;
	private JTextField tf_lastname;
	private JTextField tf_birthday;
	private JTextField tf_country;

	/**
	 * Launch the application.
	 */
	public static void createFreeRoom() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FreeRoom window = new FreeRoom();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FreeRoom() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(700, 200, 323, 282);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("ROOM  "+currentRoom);
		
		JButton btnCloseRoom = new JButton("Close");
		btnCloseRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCloseRoom.setBorder(null);
		btnCloseRoom.setBounds(247, 183, 50, 50);
		frame.getContentPane().add(btnCloseRoom);
		
		JButton btnBooking = new JButton("Booking");
		btnBooking.setBorder(null);
		btnBooking.setBounds(10, 183, 50, 50);
		frame.getContentPane().add(btnBooking);
		
		JLabel lblBookingId = new JLabel("Booking ID");
		lblBookingId.setBounds(10, 11, 97, 14);
		frame.getContentPane().add(lblBookingId);
		
		JLabel lblBooked = new JLabel("Booked");
		lblBooked.setBounds(10, 36, 97, 14);
		frame.getContentPane().add(lblBooked);
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(10, 61, 97, 14);
		frame.getContentPane().add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(10, 86, 97, 14);
		frame.getContentPane().add(lblLastname);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(10, 111, 97, 14);
		frame.getContentPane().add(lblBirthday);
		
		JLabel lblCountry = new JLabel("Country");
		lblCountry.setBounds(10, 136, 97, 14);
		frame.getContentPane().add(lblCountry);
		
		tf_booking_id = new JTextField();
		tf_booking_id.setEditable(false);
		tf_booking_id.setBackground(UIManager.getColor("CheckBox.background"));
		tf_booking_id.setBorder(null);
		tf_booking_id.setBounds(117, 8, 180, 20);
		frame.getContentPane().add(tf_booking_id);
		tf_booking_id.setColumns(10);
		
		tf_booked = new JTextField();
		tf_booked.setText("                     Available");
		tf_booking_id.setBackground(UIManager.getColor("CheckBox.background"));
		tf_booked.setEditable(false);
	    tf_booked.setBackground(new Color(102, 255, 51));
		tf_booked.setBorder(null);
		tf_booked.setBounds(117, 33, 180, 20);
		frame.getContentPane().add(tf_booked);
		tf_booked.setColumns(10);
		
		tf_firstname = new JTextField();
		tf_firstname.setDoubleBuffered(true);
		tf_firstname.setEditable(false);
		tf_firstname.setBackground(UIManager.getColor("CheckBox.background"));
		tf_firstname.setBorder(null);
		tf_firstname.setBounds(117, 58, 180, 20);
		frame.getContentPane().add(tf_firstname);
		tf_firstname.setColumns(10);
		
		tf_lastname = new JTextField();
		tf_lastname.setEditable(false);
		tf_lastname.setBackground(UIManager.getColor("CheckBox.background"));
		tf_lastname.setBorder(null);
		tf_lastname.setBounds(117, 83, 180, 20);
		frame.getContentPane().add(tf_lastname);
		tf_lastname.setColumns(10);
		
		tf_birthday = new JTextField();
		tf_birthday.setEditable(false);
		tf_birthday.setBackground(UIManager.getColor("CheckBox.background"));
		tf_birthday.setBorder(null);
		tf_birthday.setBounds(117, 108, 180, 20);
		frame.getContentPane().add(tf_birthday);
		tf_birthday.setColumns(10);
		
		tf_country = new JTextField();
		tf_country.setEditable(false);
		tf_country.setBackground(UIManager.getColor("CheckBox.background"));
		tf_country.setBorder(null);
		tf_country.setBounds(117, 133, 180, 20);
		frame.getContentPane().add(tf_country);
		tf_country.setColumns(10);
		
	}
}
