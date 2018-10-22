import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
* @author  Özdemir Nizam
*/

public class bookingPopUp {

	private JFrame bookingPopUp;
	private static int booking_id;
	/**
	 * Launch the application.
	 */
	public static void startBookingPopUp(int id) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bookingPopUp window = new bookingPopUp(id);
					window.bookingPopUp.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public bookingPopUp(int id) {
		booking_id = id;
		initialize();
		System.out.println(booking_id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		bookingPopUp = new JFrame();
		bookingPopUp.setBounds(800, 200, 310, 335);
		bookingPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bookingPopUp.getContentPane().setLayout(null);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookingPopUp.dispose();
			}
		});
		btnClose.setBounds(195, 262, 89, 23);
		bookingPopUp.getContentPane().add(btnClose);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setBounds(10, 262, 89, 23);
		bookingPopUp.getContentPane().add(btnPrint);
	}

}
