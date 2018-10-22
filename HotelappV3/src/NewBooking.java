import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Window.Type;

/*
* @author  Özdemir Nizam
*/

public class NewBooking {

	JFrame frmBookingFormular;
	private JTextField tf_firstname;
	private JTextField tf_lastname;
	private JTextField tf_nationality;
	private JTextField tf_street;
	private JTextField tf_zip;
	private JTextField tf_country;
	private JTextField tf_city;
	private JTextField tf_email;
	private JTextField tf_deposit;
	private String valueOfComboBoxDay;
	private String valueOfComboBoxMonth;
	private String valueOfComboBoxYear;
	private String valueOfComboBoxRoom;
	private static ArrayList<String> list = new ArrayList<>();
	private static ArrayList<String> excellist = new ArrayList<>();
	protected IDateEditor dateEditor;
	public Date publicDateFrom;
	public Date publicDateTo;

	// Startet die APP
	public static void startNewBookingFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewBooking window = new NewBooking();
					window.frmBookingFormular.setVisible(true);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	// Erstellt die APP
	public NewBooking() throws SQLException {
		initialize();
	}

	// Gibt eine Liste mit den zu verarbeitenden Daten weiter Richtung BookingModel
	public static ArrayList<String> getBookingDataFromNewBooking() {
		return list;
	}

	// Erzeugt Formular mit allen Input Felder
	private void initialize() {
		frmBookingFormular = new JFrame();
		frmBookingFormular.setTitle("Booking Formular");
		frmBookingFormular.setBounds(700, 200, 363, 621);
		frmBookingFormular.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBookingFormular.getContentPane().setLayout(null);

		tf_firstname = new JTextField();
		tf_firstname.setBorder(null);
		tf_firstname.setBounds(91, 21, 243, 22);
		frmBookingFormular.getContentPane().add(tf_firstname);
		tf_firstname.setColumns(10);

		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(12, 24, 82, 16);
		frmBookingFormular.getContentPane().add(lblFirstname);

		tf_lastname = new JTextField();
		tf_lastname.setBorder(null);
		tf_lastname.setBounds(91, 51, 243, 22);
		frmBookingFormular.getContentPane().add(tf_lastname);
		tf_lastname.setColumns(10);

		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(12, 54, 64, 16);
		frmBookingFormular.getContentPane().add(lblLastname);

		tf_nationality = new JTextField();
		tf_nationality.setBorder(null);
		tf_nationality.setBounds(91, 81, 243, 22);
		frmBookingFormular.getContentPane().add(tf_nationality);
		tf_nationality.setColumns(10);

		JLabel lblNation = new JLabel("Nationality");
		lblNation.setBounds(12, 84, 72, 16);
		frmBookingFormular.getContentPane().add(lblNation);

		tf_street = new JTextField();
		tf_street.setBorder(null);
		tf_street.setBounds(91, 111, 243, 22);
		frmBookingFormular.getContentPane().add(tf_street);
		tf_street.setColumns(10);

		JLabel lblNewLabel = new JLabel("Street");
		lblNewLabel.setBounds(12, 113, 56, 16);
		frmBookingFormular.getContentPane().add(lblNewLabel);

		tf_zip = new JTextField();
		tf_zip.setBorder(null);
		tf_zip.setBounds(91, 141, 243, 22);
		frmBookingFormular.getContentPane().add(tf_zip);
		tf_zip.setColumns(10);

		JLabel lblZip = new JLabel("Zip");
		lblZip.setBounds(12, 144, 56, 16);
		frmBookingFormular.getContentPane().add(lblZip);

		tf_country = new JTextField();
		tf_country.setBorder(null);
		tf_country.setBounds(91, 171, 243, 22);
		frmBookingFormular.getContentPane().add(tf_country);
		tf_country.setColumns(10);

		JLabel lblCountry = new JLabel("Country");
		lblCountry.setBounds(12, 173, 56, 16);
		frmBookingFormular.getContentPane().add(lblCountry);

		tf_email = new JTextField();
		tf_email.setBorder(null);
		tf_email.setBounds(90, 201, 243, 22);
		frmBookingFormular.getContentPane().add(tf_email);
		tf_email.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(12, 204, 56, 16);
		frmBookingFormular.getContentPane().add(lblEmail);

		tf_city = new JTextField();
		tf_city.setBorder(null);
		tf_city.setBounds(91, 236, 244, 20);
		frmBookingFormular.getContentPane().add(tf_city);
		tf_city.setColumns(10);

		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(12, 241, 46, 14);
		frmBookingFormular.getContentPane().add(lblCity);

		JLabel lblDeposit = new JLabel("Deposit");
		lblDeposit.setBounds(10, 419, 56, 16);
		frmBookingFormular.getContentPane().add(lblDeposit);

		tf_deposit = new JTextField("");
		tf_deposit.setBorder(null);
		tf_deposit.setBounds(91, 417, 243, 22);
		frmBookingFormular.getContentPane().add(tf_deposit);
		tf_deposit.setColumns(10);

		String[] contentDays = { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
		JComboBox comboBoxDay = new JComboBox(contentDays);
		comboBoxDay.setBounds(91, 279, 56, 22);
		frmBookingFormular.getContentPane().add(comboBoxDay);
		comboBoxDay.setSelectedIndex(2);

		String[] contentMonths = { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		JComboBox comboBoxMonth = new JComboBox(contentMonths);
		comboBoxMonth.setBounds(160, 279, 75, 22);
		frmBookingFormular.getContentPane().add(comboBoxMonth);
		comboBoxMonth.setSelectedIndex(2);

		String[] contentYears = { "", "1952","1953","1954","1955","1956","1957","1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979",
				"1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992",
				"1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010" };
		JComboBox comboBoxYear = new JComboBox(contentYears);
		comboBoxYear.setBounds(246, 279, 88, 22);
		frmBookingFormular.getContentPane().add(comboBoxYear);
		comboBoxYear.setSelectedIndex(10);

		String[] contentRooms = { "", "102", "103", "104", "105", "106", "107", "108", "109", "201", "202",
				"203", "204", "205", "206", "207", "208", "209" };
		JComboBox comboBoxRoom = new JComboBox(contentRooms);
		comboBoxRoom.setBounds(91, 380, 72, 22);
		frmBookingFormular.getContentPane().add(comboBoxRoom);

		String[] contentStayDays = { "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

		JLabel lblDateOfBirth = new JLabel("Date Birth");
		lblDateOfBirth.setBounds(12, 282, 82, 16);
		frmBookingFormular.getContentPane().add(lblDateOfBirth);

		JDateChooser dateChooserFrom = new JDateChooser();
		dateChooserFrom.setBorder(null);
		dateChooserFrom.setBounds(91, 314, 93, 22);
		frmBookingFormular.getContentPane().add(dateChooserFrom);

		JDateChooser dateChooserTo = new JDateChooser();
		dateChooserTo.setBorder(null);
		dateChooserTo.setBounds(91, 347, 93, 22);
		frmBookingFormular.getContentPane().add(dateChooserTo);

		dateChooserTo.getJCalendar().setMinSelectableDate(new Date());
		dateChooserFrom.getJCalendar().setMinSelectableDate(new Date());
		dateChooserTo.setMinSelectableDate(new Date());
		dateChooserFrom.setMinSelectableDate(new Date());

		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(12, 347, 25, 16);
		frmBookingFormular.getContentPane().add(lblTo);

		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(12, 314, 56, 16);
		frmBookingFormular.getContentPane().add(lblFrom);

		// Session 1 steht für INSERT
		JButton btnSaveOpenInsert = new JButton("Save & Open");
		btnSaveOpenInsert.addActionListener(new ActionListener() {

			// Sammelt alle Inputdaten aus dem Formular, verarbeitet alle Werte zu Strings
			// und füllt diese in eine ArrayList<String>
			public void actionPerformed(ActionEvent arg0) {

				// Überprüft ob alle Felder einen gültigen Wert haben, falls nicht wird eine
				// Fehlermeldung ausgegeben und die Daten werden nicht gesendet
				if (tf_firstname.getText().trim().length() == 0 || tf_firstname.getText().trim().length() == 0
						|| tf_nationality.getText().trim().length() == 0 || tf_street.getText().trim().length() == 0
						|| tf_zip.getText().trim().length() == 0 || tf_country.getText().trim().length() == 0
						|| tf_email.getText().trim().length() == 0 || tf_deposit.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "Fehlende Werte!");
				} else {

					// Teilt allen Rooms die Eigenschaften zu (Preis/Nacht usw.)
					RoomModel.setRoomMap();

					// Comboboxwerte werden zu Strings verarbeitet
					valueOfComboBoxDay = String.valueOf(comboBoxDay.getSelectedItem());
					valueOfComboBoxMonth = String.valueOf(comboBoxMonth.getSelectedItem());
					valueOfComboBoxYear = String.valueOf(comboBoxYear.getSelectedItem());
					String birthdateString = valueOfComboBoxYear + "/" + valueOfComboBoxMonth + "/"
							+ valueOfComboBoxDay;

					valueOfComboBoxRoom = String.valueOf(comboBoxRoom.getSelectedItem());

					// Datechooserwerte werden zu Strings verarbeitet
					Date dateFrom = dateChooserFrom.getDate();
					Date dateTo = dateChooserTo.getDate();

					list.clear();
					// Füllt die ArrayList mit Stringwerten
					list.add(tf_firstname.getText()); // 1
					list.add(tf_lastname.getText()); // 2
					list.add(tf_nationality.getText()); // 3
					list.add(tf_street.getText()); // 4
					list.add(tf_zip.getText()); // 5
					list.add(tf_city.getText()); // 6
					list.add(tf_country.getText()); // 7
					list.add(tf_email.getText()); // 8
					list.add(birthdateString); // 9
					list.add(DateFormat.getDateInstance().format(dateFrom)); // 10
					list.add(DateFormat.getDateInstance().format(dateTo)); // 11
					list.add(tf_deposit.getText()); // 12
					list.add(valueOfComboBoxRoom); // 13

					excellist.clear();
					// Füllt die ArrayList mit Stringwerten für Excel
					excellist.add(tf_firstname.getText()); // 0
					excellist.add(tf_lastname.getText()); // 1
					excellist.add(tf_nationality.getText()); // 2
					excellist.add(tf_street.getText()); // 3
					excellist.add(tf_zip.getText()); // 4
					excellist.add(tf_city.getText()); // 5
					excellist.add(tf_country.getText()); // 6
					excellist.add(tf_email.getText()); // 7
					excellist.add(birthdateString); // 8
					excellist.add(DateFormat.getDateInstance().format(dateFrom)); // 9
					excellist.add(DateFormat.getDateInstance().format(dateTo)); // 10
					excellist.add(tf_deposit.getText()); // 11
					excellist.add(valueOfComboBoxRoom); // 12

					// Löst den Versendermechanismus aus in BookingModel Klasse und schickt ein
					// Parameter mit für INSERT and Excel OPEN
					try {
						BookingModel.getBookingData(0, list, excellist);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Aktualisiert alle Tabellen
					MainclassView.refreshAllTables();

					// Prüft auf doppelte Einträge
					try {
						Boolean b = null;
						b = CustomerModel.checkDoubleCostumer(tf_firstname.getText(), tf_lastname.getText());
						if (b == false) {
							ArrayList<String> insertCustomerList = new ArrayList<String>();
							insertCustomerList.add(""); // 0
							insertCustomerList.add(tf_firstname.getText()); // 1
							insertCustomerList.add(tf_lastname.getText()); // 2
							insertCustomerList.add(tf_nationality.getText()); // 3
							insertCustomerList.add(tf_street.getText()); // 4
							insertCustomerList.add(tf_zip.getText()); // 5
							insertCustomerList.add(tf_city.getText()); // 6
							insertCustomerList.add(birthdateString); // 7
							insertCustomerList.add("079"); // 8
							insertCustomerList.add(tf_email.getText()); // 9
							insertCustomerList.add(tf_country.getText()); // 10
							insertCustomerList.add("none"); // 11
							Controller.insertCustomerData(insertCustomerList);
							JOptionPane.showMessageDialog(null, "Neuer Kunde!");
						} else {
							JOptionPane.showMessageDialog(null, "Bestandskunde!");
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					list.clear();
					// Schliesst das Formularfenster
					frmBookingFormular.dispose();
				}
			}
		});
		btnSaveOpenInsert.setBorder(null);
		btnSaveOpenInsert.setBounds(9, 546, 97, 25);
		frmBookingFormular.getContentPane().add(btnSaveOpenInsert);

		JButton btnSaveInsert = new JButton("Save");
		btnSaveInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Überprüft ob alle Felder einen gültigen Wert haben, falls nicht wird eine
				// Fehlermeldung ausgegeben und die Daten werden nicht gesendet
				if (tf_firstname.getText().trim().length() == 0 || tf_firstname.getText().trim().length() == 0
						|| tf_nationality.getText().trim().length() == 0 || tf_street.getText().trim().length() == 0
						|| tf_zip.getText().trim().length() == 0 || tf_country.getText().trim().length() == 0
						|| tf_email.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "Fehlende Werte!");
				} else {

					// Teilt allen Rooms die Eigenschaften zu (Preis/Nacht usw.)
					RoomModel.setRoomMap();

					// Comboboxwerte werden zu Strings verarbeitet
					valueOfComboBoxDay = String.valueOf(comboBoxDay.getSelectedItem());
					valueOfComboBoxMonth = String.valueOf(comboBoxMonth.getSelectedItem());
					valueOfComboBoxYear = String.valueOf(comboBoxYear.getSelectedItem());
					String birthdateString = valueOfComboBoxYear + "/" + valueOfComboBoxMonth + "/"
							+ valueOfComboBoxDay;

					valueOfComboBoxRoom = String.valueOf(comboBoxRoom.getSelectedItem());

					// Datechooserwerte werden zu Strings verarbeitet
					Date dateFrom = dateChooserFrom.getDate();
					Date dateTo = dateChooserTo.getDate();
					publicDateFrom = dateFrom;
					publicDateTo = dateTo;

					list.clear();

					// Füllt die ArrayList mit Stringwerten
					list.add(tf_firstname.getText()); // 1
					list.add(tf_lastname.getText()); // 2
					list.add(tf_nationality.getText()); // 3
					list.add(tf_street.getText()); // 4
					list.add(tf_zip.getText()); // 5
					list.add(tf_city.getText()); // 6
					list.add(tf_country.getText()); // 7
					list.add(tf_email.getText()); // 8
					list.add(birthdateString); // 9
					list.add(DateFormat.getDateInstance().format(dateFrom)); // 10
					list.add(DateFormat.getDateInstance().format(dateTo)); // 11
					list.add(tf_deposit.getText()); // 12
					list.add(valueOfComboBoxRoom); // 13

					// Löst den Versendermechanismus aus in BookingModel Klasse und schickt ein
					// Parameter 1 mit für INSERT
					try {
						BookingModel.getBookingData(3, list, null);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Prüft auf doppelte Customer Einträge
					try {
						Boolean b = null;
						b = CustomerModel.checkDoubleCostumer(tf_firstname.getText(), tf_lastname.getText());
						if (b == false) {
							ArrayList<String> insertCustomerList = new ArrayList<String>();
							insertCustomerList.add(""); // 0
							insertCustomerList.add(tf_firstname.getText()); // 1
							insertCustomerList.add(tf_lastname.getText()); // 2
							insertCustomerList.add(tf_nationality.getText()); // 3
							insertCustomerList.add(tf_street.getText()); // 4
							insertCustomerList.add(tf_zip.getText()); // 5
							insertCustomerList.add(tf_city.getText()); // 6
							insertCustomerList.add(birthdateString); // 7
							insertCustomerList.add("079"); // 8
							insertCustomerList.add(tf_email.getText()); // 9
							insertCustomerList.add(tf_country.getText()); // 10
							insertCustomerList.add("none"); // 11
							insertCustomerList.add(checkCustomerStatus()); // 12
							Controller.insertCustomerData(insertCustomerList);
							JOptionPane.showMessageDialog(null, "Neuer Kunde!");
						} else {
							JOptionPane.showMessageDialog(null, "Bestandskunde!");
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Überprüft Datum der Buchung mit aktuellem Datum
					try {
						RoomModel.setFree(valueOfComboBoxRoom, tf_firstname.getText(),
								DateFormat.getDateInstance().format(dateFrom),
								DateFormat.getDateInstance().format(dateTo));
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Aktualisiert alle Tabellen
					MainclassView.refreshAllTables();
					
					// Schliesst das Formularfenster
					list.clear();
					frmBookingFormular.dispose();
				}
			}
		});
		btnSaveInsert.setBorder(null);
		btnSaveInsert.setBounds(122, 546, 97, 25);
		frmBookingFormular.getContentPane().add(btnSaveInsert);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBorder(null);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBookingFormular.dispose();
			}
		});
		btnCancel.setBounds(236, 546, 97, 25);
		frmBookingFormular.getContentPane().add(btnCancel);

		JLabel lblRoom = new JLabel("Special");
		lblRoom.setBounds(12, 471, 56, 16);
		frmBookingFormular.getContentPane().add(lblRoom);

		JTextPane tp_special = new JTextPane();
		tp_special.setBounds(91, 450, 243, 67);
		frmBookingFormular.getContentPane().add(tp_special);
		tp_special.setText("Irgend ein Text");

		JLabel lblRoom_1 = new JLabel("Room");
		lblRoom_1.setBounds(12, 383, 56, 16);
		frmBookingFormular.getContentPane().add(lblRoom_1);

	}
	
	public String checkCustomerStatus() {
		String status = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date todayDate = new Date();
		
		if(todayDate.after(publicDateFrom) && todayDate.before(publicDateTo)) {
			status = "active";
		} else {
			status = "disabled";
		}
		
		return status;
		
	}
}
