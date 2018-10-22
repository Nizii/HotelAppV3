/**
* Herr Wanner schön das Sie sich die Zeit genommen haben rein zu schauen.
* 
* Dieses CRM System entwickle ich für die Eltern meiner Freundin die in Thailand leben und eine kleine Pension mit 30 Zimmern betreiben.
* Mit diesem CRM System können Sie ihre Finanzen, Buchungen, Kunden und die Liegenschaft an sich verwalten. Sie können Quittungen, Buchungen und Kunden erfassen, bearbeiten oder löschen.
* Zudem können Sie Räume erstellen bearbeiten oder löschen. Quittungen werden über Excel geöffnet. das habe ich mit Woorkbook gemacht.
* Die Datenbankverbindung wird mit dem JDBC Treiber hergestellt aktuell local auf meinem PC.
* Die MainclassView ist die Hauptklasse hier startet die App.
* Hier in der MainclassView befindet sich das gesamte Frontend (Tabs, Tablellen Buttons Textfields usw.), diese kommuniziert über Funktionen mit den ModlerKlassen und diese Wiederum mit der Klasse Controller für die SQL Anfragen.
* 
* Leider habe ich noch keine Dokumentation erstellt. Ich würde ihnen aber gerne bei Interesse das System live zeigen im Zulassungsgespräch.
*
* Bitte entschuldigen Sie meine Rechtsschreibung in den Kommentaren
*
* @author  Özdemir Nizam
*/


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;
import javax.swing.JScrollPane;
import java.awt.Window.Type;

public class MainclassView {

	private static JFrame frmSspGroupApp;
	static JTable outputTable;
	static JTable bookingTable;
	static JTable customerTable;
	static JTable roomTable;
	static JTable nextCustomerTable;
	static JTextField tf_amount;
	private static JTextField tf_description;
	private static JTextField tf_date;
	private static JTextField tf_output_nr;
	private static int jComboBoxInt;
	private static String jComboBoxItem;
	private static String jComboBoxItemRoom;
	private static String jComboBoxItemRoomStatus;
	private static String jComboBoxItemDayCustomer;
	private static String jComboBoxItemMonthCustomer;
	private static String jComboBoxItemYearCustomer;
	private static JTextField tfCustomerId;
	private static JTextField tfCustomerFirstname;
	private static JTextField tfCustomerLastname;
	private static JTextField tfCustomerNationality;
	private static JTextField tfCustomerStreet;
	private static JTextField tfCustomerZip;
	private static JTextField tfCustomerCity;
	private static JTextField tfCustomerTelefon;
	private static JTextField tfCustomerOthers;
	private static JTextField tfCustomerEmail;
	private static JTextField tfCustomerCountry;
	Component component;
	private static JTextField tf_room_nr;
	private static JTextField tf_price;
	ArrayList<String> roomInfoList = new ArrayList<>();
	ArrayList<String> bookingInfoList = new ArrayList<>();
	private String valueOfComboBoxDayCustomer;
	private String valueOfComboBoxMonthCustomer;
	private String valueOfComboBoxYearCustomer;
	private JTextField tf_search_booking;

	// Startet die APP
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainclassView window = new MainclassView();
					window.frmSspGroupApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Erstellt die APP
	public MainclassView() throws SQLException, ParseException {
		initialize();

		// Füllt alle Tabellen mit den Daten aus der Datenbank, dabei wird mit diesen
		// Methoden jeweils der Prozess dazu ausgelöst in der Klasse BookingModel und
		// Finance Model
		try {
			BookingModel.refreshActiv();
			RoomModel.refreshRoomStatus();
			FinanceModel.getOutputTableDataFromSql();
			BookingModel.getBookingTableDataFromSql();
			CustomerModel.getCustomerTableDataFromSql();
			RoomModel.getRoomTableDataFromSql(4); //Füllt roomTable in Controller
			RoomModel.getRoomTableDataFromSql(5); //Füllt nextCustomerTable in Controller
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Keine Verbindung zur Datenbank möglich!");
			System.exit(0);
		}
	}

	/*
	 * Getter/Setter FINANCE
	 */

	public static void setTfOutputNr(String text) {
		tf_output_nr.setText(text);
	}

	public static int getTfOutputNr() {
		int i = Integer.parseInt(tf_output_nr.getText());
		return i;
	}

	public static void setTfAmount(String text) {
		tf_amount.setText(text);
	}

	public static int getTfAmount() {
		int i = 0;
		if (tf_amount.getText().trim().length() == 0) {
			i = 0;
		} else {
			i = Integer.parseInt(tf_amount.getText());
		}
		return i;
	}

	public static void setTfDescription(String text) {
		tf_description.setText(text);
	}

	public static String getTfDescription() {
		String s = tf_description.getText();
		return s;
	}

	public static void setTfCategory(String text) {
		// tf_category.setText(text);
	}

	public static void setTfDate(String text) {
		tf_date.setText(text);
	}

	public int jCoboBoxRefresh() {
		return jComboBoxInt;
	}

	/*
	 * Getter/Setter CUSTOMER
	 */

	public static int getTfCustomerId() {
		int i = Integer.parseInt(tfCustomerId.getText());
		return i;
	}

	public static void setTfCustomerId(String text) {
		tfCustomerId.setText(text);
	}

	public static void setTfCustomerFirstname(String text) {
		tfCustomerFirstname.setText(text);
	}

	public static void setTfCustomerLastname(String text) {
		tfCustomerLastname.setText(text);
	}

	public static void setTfCustomerNationality(String text) {
		tfCustomerNationality.setText(text);
	}

	public static void setTfCustomerCity(String text) {
		tfCustomerCity.setText(text);
	}

	public static void setTfCustomerCountry(String text) {
		tfCustomerCountry.setText(text);
	}

	public static void setTfCustomerStreet(String text) {
		tfCustomerStreet.setText(text);
	}

	public static void setTfCustomerZip(String text) {
		tfCustomerZip.setText(text);
	}

	public static void setTfCustomerEmail(String text) {
		tfCustomerEmail.setText(text);
	}

	public static void setTfCustomerOthers(String text) {
		tfCustomerOthers.setText(text);
	}

	public static void setTfCustomerTelefon(String text) {
		tfCustomerTelefon.setForeground(Color.black);
		tfCustomerTelefon.setText(text);
	}

	// Getter Setter Room
	public static void setTfRoomNr(String room_nr) {
		tf_room_nr.setText(room_nr);
	}

	public static void setTfPrice(String price) {
		tf_price.setText(price);
	}

	public static String getTfRoomNr() {
		return tf_room_nr.getText();
	}

	public static int getTfPrice() {
		int i = 0;
		if (tf_price.getText().trim().length() == 0) {
			i = 0;
		} else {
			i = Integer.parseInt(tf_price.getText());
		}
		return i;
	}

	// ComboBoxFinance Die Funktion konvertiert den Wert (String) einer JComboBox zu
	// einem Int Wert damit später die JComboBox über den Index angesteuert werden
	// kann
	public static void setjComboBoxItemFinance(String s) {
		jComboBoxItem = s;
	}

	// ComboBoxRoom Die Funktion konvertiert den Wert (String) einer JComboBox zu
	// einem Int Wert damit später die JComboBox über den Index angesteuert werden
	// kann
	public static void setjComboBoxItemRoom(String roomString) {
		jComboBoxItemRoom = roomString;
	}

	// ComboBoxRoom Die Funktion konvertiert den Wert (String) einer JComboBox zu
	// einem Int Wert damit später die JComboBox über den Index angesteuert werden
	// kann
	public static void setjComboBoxItemRoomStatus(String statusString) {
		jComboBoxItemRoomStatus = statusString;
	}
	
	public static void setjComboBoxItemDayCustomer(String dayCustomerString) {
		jComboBoxItemDayCustomer = dayCustomerString;
	}
	
	public static void setjComboBoxItemMonthCustomer(String monthCustomerroomString) {
		jComboBoxItemMonthCustomer = monthCustomerroomString;
	}
	
	public static void setjComboBoxItemYearCustomer(String yearCustomerString) {
		jComboBoxItemYearCustomer = yearCustomerString;
	}

	// Erzeugt Formular mit allen Input Felder
	@SuppressWarnings("unchecked")
	private void initialize() throws SQLException, ParseException {
		frmSspGroupApp = new JFrame();
		frmSspGroupApp.setFocusTraversalPolicyProvider(true);
		frmSspGroupApp.setTitle("SSP Group App");
		frmSspGroupApp.setBounds(400, 100, 1092, 724);
		frmSspGroupApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSspGroupApp.getContentPane().setLayout(null);

		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setBounds(10, 29, 1056, 638);
		frmSspGroupApp.getContentPane().add(tabs);

		DefaultTableModel customerModel = new DefaultTableModel();

		String[] searchStrings = { "firstname", "lastname", "nationality", "city", "street", "zip", "country", "email",
				"birthdate", "telefon" };

		JPanel roomPanel = new JPanel();
		roomPanel.setBackground(SystemColor.control);
		tabs.addTab("Room", null, roomPanel, null);
		roomPanel.setLayout(null);


		roomTable = new JTable();
		roomTable.setBounds(527, 21, 514, 578);
		roomPanel.add(roomTable);

		roomTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				// c.setBackground(row % 2 == 0 ? Color.yellow : Color.ORANGE);
				c.setBackground(isSelected ? Color.yellow : Color.WHITE);
				return c;
			}
		});

		JScrollPane roomScrollPane = new JScrollPane(roomTable);
		roomScrollPane.setBounds(482, 34, 524, 440);
		roomPanel.add(roomScrollPane);

		JLabel lblRoomNr = new JLabel("Room Nr");
		lblRoomNr.setBounds(482, 493, 57, 14);
		roomPanel.add(lblRoomNr);

		JLabel lblPricenight = new JLabel("Price/Night");
		lblPricenight.setBounds(482, 518, 69, 14);
		roomPanel.add(lblPricenight);

		JLabel lblAircon = new JLabel("Aircon");
		lblAircon.setBounds(482, 543, 46, 14);
		roomPanel.add(lblAircon);

		tf_room_nr = new JTextField();
		tf_room_nr.setBackground(SystemColor.text);
		tf_room_nr.setBorder(null);
		tf_room_nr.setBounds(584, 490, 100, 20);
		roomPanel.add(tf_room_nr);
		tf_room_nr.setColumns(10);

		tf_price = new JTextField();
		tf_price.setBorder(null);
		tf_price.setBounds(584, 515, 100, 20);
		roomPanel.add(tf_price);
		tf_price.setColumns(10);

		// Erstellt eine JComboBox und füllt diese gleich mit Werten aus einer Array
		String[] contentStringsRoom = { "", "Aircon", "Ventilator", "Nothing" };
		JComboBox comboBoxRoom = new JComboBox(contentStringsRoom);
		comboBoxRoom.setBackground(SystemColor.controlHighlight);
		comboBoxRoom.setBorder(null);
		comboBoxRoom.setBounds(584, 540, 100, 20);
		roomPanel.add(comboBoxRoom);
		comboBoxRoom.setSelectedIndex(0);

		// Erstellt eine JComboBox und füllt diese gleich mit Werten aus einer Array
		String[] contentStringsRoomStatus = { "", "free", "occupaid", "reserved" };
		JComboBox comboBoxRoomStatus = new JComboBox(contentStringsRoomStatus);
		comboBoxRoomStatus.setBackground(SystemColor.controlHighlight);
		comboBoxRoomStatus.setBorder(null);
		comboBoxRoomStatus.setBounds(584, 564, 100, 20);
		roomPanel.add(comboBoxRoomStatus);
		comboBoxRoomStatus.setSelectedIndex(0);
		
		roomTable.addMouseListener(new MouseAdapter() {
			@Override
			// Sobald eine Reihe in der Tabelle ankeklickt wird, dass werden alle Textfelder
			// mit den Daten gefüllt
			public void mouseClicked(MouseEvent e) {
				try {
					RoomModel.getSelectedRowFromRoomTable();
					comboBoxRoom.setSelectedItem(jComboBoxItemRoom);
					comboBoxRoomStatus.setSelectedItem(jComboBoxItemRoomStatus);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {

				}
			}
		});



		JButton btnSaveRoom = new JButton("Edit");
		btnSaveRoom.addActionListener(new ActionListener() {
			// Löst den Prozess für ein UPDATE eines Rooms aus, dabei wird ein Parameter
			// übergeben um in der nächsten Funktion entscheiten zu können ob ein UPDATE
			// oder ein INSERT gemacht werden soll
			public void actionPerformed(ActionEvent e) {
				if (roomTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					RoomModel.checkComboboxRoom(comboBoxRoom.getSelectedItem().toString(),
							comboBoxRoomStatus.getSelectedItem().toString(), 2);
					comboBoxRoom.setSelectedIndex(0);
					comboBoxRoomStatus.setSelectedIndex(0);
				}
			}
		});
		btnSaveRoom.setBorder(null);
		btnSaveRoom.setBounds(774, 534, 50, 50);
		roomPanel.add(btnSaveRoom);

		JButton btnDeleteRoom = new JButton("Delete");
		btnDeleteRoom.addActionListener(new ActionListener() {
			// Löscht die angeklickte Reihe in der Tabelle aus der datenbank
			public void actionPerformed(ActionEvent e) {
				if (roomTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					try {
						RoomModel.getDataFromGuiForDelete(roomTable.getSelectedRow());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		btnDeleteRoom.setBorder(null);
		btnDeleteRoom.setBounds(835, 534, 50, 50);
		roomPanel.add(btnDeleteRoom);

		JButton btnNewRoom = new JButton("New");
		btnNewRoom.addActionListener(new ActionListener() {
			// Löst den Prozess für ein INSERT einer Quittung aus, dabei wird ein Parameter
			// übergeben um in der nächsten Funktion entscheiten zu können ob ein UPDATE
			// oder ein INSERT gemacht werden soll
			public void actionPerformed(ActionEvent e) {
				RoomModel.checkComboboxRoom(comboBoxRoom.getSelectedItem().toString(),
						comboBoxRoomStatus.getSelectedItem().toString(), 1);
				comboBoxRoom.setSelectedIndex(0);
				comboBoxRoomStatus.setSelectedIndex(0);
			}
		});
		btnNewRoom.setBorder(null);
		btnNewRoom.setBounds(714, 534, 50, 50);
		roomPanel.add(btnNewRoom);

		JPanel bookingPanel = new JPanel();
		bookingPanel.setBackground(new Color(245, 245, 245));
		tabs.addTab("Booking", null, bookingPanel, null);
		tabs.setEnabledAt(1, true);
		bookingPanel.setLayout(null);

		JButton btnNewBooking = new JButton("New");
		btnNewBooking.setBorder(null);
		btnNewBooking.addActionListener(new ActionListener() {

			// Löst den Prozess einer neuen Buchung aus
			public void actionPerformed(ActionEvent arg0) {
				NewBooking.startNewBookingFrame();
			}
		});
		btnNewBooking.setBounds(12, 39, 50, 50);
		bookingPanel.add(btnNewBooking);

		JButton btnUpdateBooking = new JButton("Edit");
		btnUpdateBooking.addActionListener(new ActionListener() {

			// Bestehende Buchung wird bearbeitet
			public void actionPerformed(ActionEvent arg0) {
				if (bookingTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Buchung ausgewählt!");
				} else {
					UpdateBooking.startUpdateBookingFrame();
				}
			}
		});
		btnUpdateBooking.setBorder(null);
		btnUpdateBooking.setBounds(12, 99, 50, 50);
		bookingPanel.add(btnUpdateBooking);

		DefaultTableModel bookingModel = new DefaultTableModel();

		bookingTable = new JTable(bookingModel);
		bookingTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		bookingTable.setBounds(72, 39, 967, 504);
		bookingPanel.add(bookingTable);

		bookingTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				// c.setBackground(row % 2 == 0 ? Color.GREEN : Color.ORANGE);
				c.setBackground(isSelected ? Color.yellow : Color.WHITE);
				return c;
			}
		});
		JScrollPane bookingScrollPane = new JScrollPane(bookingTable);
		bookingScrollPane.setBounds(72, 39, 969, 456);
		bookingPanel.add(bookingScrollPane);

		// JPOPUPMENUBOOKING
		JPopupMenu popupMenuBooking = new JPopupMenu();
		addPopup(bookingTable, popupMenuBooking);

		JMenuItem mntmNewBooking = new JMenuItem("New");
		mntmNewBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewBooking.startNewBookingFrame();
			}
		});
		popupMenuBooking.add(mntmNewBooking);

		JMenuItem mntmOpenExcel = new JMenuItem("Open Excel");
		mntmOpenExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BookingModel.getBookingData(5, null, null);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		popupMenuBooking.add(mntmOpenExcel);

		JMenuItem mntmUpdate = new JMenuItem("Edit");
		mntmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bookingTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile ausgewählt!");
				} else {
					UpdateBooking.startUpdateBookingFrame();
				}
			}
		});
		popupMenuBooking.add(mntmUpdate);

		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BookingModel.getBookingTableDataFromSql();
					BookingModel.refreshActiv();
					// JOptionPane.showMessageDialog(null, "Table Updated!");

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		popupMenuBooking.add(mntmRefresh);

		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bookingTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					try {
						BookingModel.getDataFromGuiForDelete(bookingTable.getSelectedRow());
						refreshAllTables();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		popupMenuBooking.add(mntmDelete);

		// System wird geschlossen
		JButton btnCancelBooking = new JButton("Exit");
		btnCancelBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancelBooking.setBorder(null);
		btnCancelBooking.setBounds(991, 506, 50, 50);
		bookingPanel.add(btnCancelBooking);

		JButton btnRefreshBooking = new JButton("Refresh");
		btnRefreshBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshAllTables();
			}
		});
		btnRefreshBooking.setBorder(null);
		btnRefreshBooking.setBounds(132, 506, 50, 50);
		bookingPanel.add(btnRefreshBooking);

		JButton btnDeleteBooking = new JButton("Delete");
		btnDeleteBooking.addActionListener(new ActionListener() {

			// Löscht die angeklickte Reihe in der Tabelle aus der datenbank
			public void actionPerformed(ActionEvent e) {
				if (bookingTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					try {
						BookingModel.getDataFromGuiForDelete(bookingTable.getSelectedRow());
						refreshAllTables();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		btnDeleteBooking.setBorder(null);
		btnDeleteBooking.setBounds(12, 159, 50, 50);
		bookingPanel.add(btnDeleteBooking);
		
		JButton btnActiveBooking = new JButton("Active");
		btnActiveBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Controller.getActiveBooking();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnActiveBooking.setBorder(null);
		btnActiveBooking.setBounds(72, 506, 50, 50);
		bookingPanel.add(btnActiveBooking);
		
		JLabel lblSearchInDatabase = new JLabel("Search in Database");
		lblSearchInDatabase.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSearchInDatabase.setBounds(237, 506, 132, 14);
		bookingPanel.add(lblSearchInDatabase);
		
		tf_search_booking = new JTextField();
		tf_search_booking.setBorder(null);
		tf_search_booking.setBounds(237, 554, 88, 20);
		bookingPanel.add(tf_search_booking);
		tf_search_booking.setColumns(10);
		
		String[] searchBookingStrings = { "", "booking_id", "firstname", "lastname", "nationality", "city", "street", "zip", "country", "email",
				"birthdate", "status", "deposit", "room" };
		JComboBox comboBoxSearchBooking = new JComboBox(searchBookingStrings);
		comboBoxSearchBooking.setBorder(null);
		comboBoxSearchBooking.setBounds(237, 526, 88, 20);
		bookingPanel.add(comboBoxSearchBooking);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBorder(null);
		btnSearch.setBounds(237, 581, 61, 23);
		bookingPanel.add(btnSearch);
		
		JLabel lblSearchWithDate = new JLabel("Search between tow Dates");
		lblSearchWithDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSearchWithDate.setBorder(null);
		lblSearchWithDate.setBounds(556, 506, 208, 14);
		bookingPanel.add(lblSearchWithDate);
		
		JLabel lblStart = new JLabel("Start");
		lblStart.setBounds(556, 534, 34, 14);
		bookingPanel.add(lblStart);
		
		JLabel lblEnd = new JLabel("End");
		lblEnd.setBounds(556, 557, 34, 14);
		bookingPanel.add(lblEnd);
		
		String[] contentDaySearchBooking = { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
		JComboBox comboBoxSearchBookingFromDay = new JComboBox(contentDaySearchBooking);
		comboBoxSearchBookingFromDay.setBounds(595, 531, 46, 20);
		bookingPanel.add(comboBoxSearchBookingFromDay);
		
		String[] contentMonthsSearchBooking = { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		JComboBox comboBoxSearchBookingFromMonth = new JComboBox(contentMonthsSearchBooking);
		comboBoxSearchBookingFromMonth.setBounds(644, 531, 43, 20);
		bookingPanel.add(comboBoxSearchBookingFromMonth);
		
		String[] contentYearSearchBooking = { "", "1952","1953","1954","1955","1956","1957","1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979",
				"1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992",
				"1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010" };
		JComboBox comboBoxSearchBookingFromYear = new JComboBox(contentYearSearchBooking);
		comboBoxSearchBookingFromYear.setBounds(690, 531, 60, 20);
		bookingPanel.add(comboBoxSearchBookingFromYear);
		
		JComboBox comboBoxSearchBookingToDay = new JComboBox(contentDaySearchBooking);
		comboBoxSearchBookingToDay.setBounds(595, 557, 46, 20);
		bookingPanel.add(comboBoxSearchBookingToDay);
		
		JComboBox comboBoxSearchBookingToMonth = new JComboBox(contentMonthsSearchBooking);
		comboBoxSearchBookingToMonth.setBounds(644, 557, 43, 20);
		bookingPanel.add(comboBoxSearchBookingToMonth);
		
		JComboBox comboBoxSearchBookingToYear = new JComboBox(contentYearSearchBooking);
		comboBoxSearchBookingToYear.setBounds(690, 557, 60, 20);
		bookingPanel.add(comboBoxSearchBookingToYear);
		
		JButton btnSearch_1 = new JButton("Search");
		btnSearch_1.setBorder(null);
		btnSearch_1.setBounds(690, 582, 61, 23);
		bookingPanel.add(btnSearch_1);

		/*
		 * Hier startet der FINANZ Part
		 */

		// CUSTOMERPART
		JPanel customerPanel = new JPanel();
		customerPanel.setBackground(new Color(245, 245, 245));
		tabs.addTab("Customer", null, customerPanel, null);
		customerPanel.setLayout(null);

		customerTable = new JTable(customerModel);
		customerTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		customerTable.setBounds(319, 32, 720, 520);
		customerPanel.add(customerTable);

		customerTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				// c.setBackground(row % 2 == 0 ? Color.yellow : Color.ORANGE);
				c.setBackground(isSelected ? Color.yellow : Color.WHITE);
				return c;
			}
		});

		JScrollPane customerScrollPane = new JScrollPane(customerTable);
		customerScrollPane.setBounds(236, 32, 803, 469);
		customerPanel.add(customerScrollPane);

		// JPOPUPMENU CUSTOMER

		JPopupMenu popupMenuCustomer = new JPopupMenu();
		addPopup(customerTable, popupMenuCustomer);

		JMenuItem mntmNewBookingCustomer = new JMenuItem("New Booking");
		mntmNewBookingCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customerTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					NewCustomerBooking.startNewCustomerBookingFrame(CustomerModel.getID());
				}
			}
		});
		popupMenuCustomer.add(mntmNewBookingCustomer);

		JMenuItem mntmDeleteCustomer = new JMenuItem("Delete");
		mntmDeleteCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customerTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					try {
						CustomerModel.getDataFromGuiForDelete();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		popupMenuCustomer.add(mntmDeleteCustomer);

		JMenuItem mntmRefreshCustomer = new JMenuItem("Refresh");
		mntmRefreshCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerModel.getCustomerTableDataFromSql();
					BookingModel.refreshActiv();
					JOptionPane.showMessageDialog(null, "Table Updated!");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		popupMenuCustomer.add(mntmRefreshCustomer);


		// JPOPUPMENU Room
		JPopupMenu popupMenuRoom = new JPopupMenu();
		addPopup(roomTable, popupMenuRoom);

		JMenuItem mntmNewBookingRoom = new JMenuItem("New Booking");
		mntmNewBookingRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewBooking.startNewBookingFrame();
			}
		});
		popupMenuRoom.add(mntmNewBookingRoom);

		JMenuItem mntmBookRoom = new JMenuItem("Book Room");
		popupMenuRoom.add(mntmBookRoom);

		JMenuItem mntmShowBooking = new JMenuItem("Show Booking");
		popupMenuRoom.add(mntmShowBooking);

		JMenuItem mntmOpenBookingexcel = new JMenuItem("Open Booking (Excel)");
		popupMenuRoom.add(mntmOpenBookingexcel);

		JMenuItem mntmDeleteBooking = new JMenuItem("Clear Booking Nr");
		popupMenuRoom.add(mntmDeleteBooking);

		JMenuItem mntmDeleteRoom = new JMenuItem("Delete");
		mntmDeleteRoom.addActionListener(new ActionListener() {
			// Löscht die angeklickte Reihe in der Tabelle aus der datenbank
			public void actionPerformed(ActionEvent e) {
				if (roomTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					try {
						RoomModel.getDataFromGuiForDelete(roomTable.getSelectedRow());
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		popupMenuRoom.add(mntmDeleteRoom);

		JMenuItem mntmRefreshRoom = new JMenuItem("Refresh");
		mntmRefreshRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RoomModel.getRoomTableDataFromSql(4);
					BookingModel.refreshActiv();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		popupMenuRoom.add(mntmRefreshRoom);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		popupMenuRoom.add(mntmClose);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(482, 567, 46, 14);
		roomPanel.add(lblStatus);
		
		JLabel lblSoonBooked = new JLabel("Next Booking");
		lblSoonBooked.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSoonBooked.setBounds(49, 8, 181, 27);
		roomPanel.add(lblSoonBooked);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setBorder(null);
		btnClose.setBounds(956, 534, 50, 50);
		roomPanel.add(btnClose);
		
		JLabel lblRooms = new JLabel("Rooms");
		lblRooms.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRooms.setBounds(482, 11, 133, 20);
		roomPanel.add(lblRooms);
		
		nextCustomerTable = new JTable();
		nextCustomerTable.setBounds(10, 34, 264, 448);
		roomPanel.add(nextCustomerTable);
		nextCustomerTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				//c.setBackground(row % 2 == 0 ? Color.GREEN : Color.ORANGE);
				//c.setBackground(isSelected ? Color.yellow : Color.WHITE);
				c.setBackground(isSelected ? Color.YELLOW: Color.GREEN);
				//Color backgroundColor = new Color(160, 220, 150);
                //setBackground(backgroundColor);
				return c;
			}
		});
		JScrollPane nextCustomerTableScrollPane = new JScrollPane(nextCustomerTable);
		nextCustomerTableScrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = nextCustomerTable.getSelectedRow();

			}
		});
		nextCustomerTableScrollPane.setBounds(49, 34, 268, 440);
		roomPanel.add(nextCustomerTableScrollPane);
		
		JButton btnRefreshRoom = new JButton("Refresh");
		btnRefreshRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAllTables();
			}
		});
		btnRefreshRoom.setBorder(null);
		btnRefreshRoom.setBounds(896, 534, 50, 50);
		roomPanel.add(btnRefreshRoom);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bookingPopUp.startBookingPopUp(nextCustomerTable.getSelectedRow());
			}
		});
		btnOpen.setBorder(null);
		btnOpen.setBounds(49, 534, 50, 50);
		roomPanel.add(btnOpen);


		JButton btnCloseCustomer = new JButton("Exit");
		btnCloseCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCloseCustomer.setBorder(null);
		btnCloseCustomer.setBounds(989, 523, 50, 50);
		customerPanel.add(btnCloseCustomer);

		JButton btnRefreshCustomer = new JButton("Refresh");
		btnRefreshCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshAllTables();
			}
		});
		btnRefreshCustomer.setBorder(null);
		btnRefreshCustomer.setBounds(252, 523, 50, 50);
		customerPanel.add(btnRefreshCustomer);

		JButton btnDeleteCustomer = new JButton("Delete");
		btnDeleteCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customerTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					try {
						CustomerModel.getDataFromGuiForDelete();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnDeleteCustomer.setBorder(null);
		btnDeleteCustomer.setBounds(929, 523, 50, 50);
		customerPanel.add(btnDeleteCustomer);

		JLabel lblNr = new JLabel("Nr");
		lblNr.setBounds(12, 39, 68, 16);
		customerPanel.add(lblNr);

		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(12, 78, 68, 16);
		customerPanel.add(lblFirstname);

		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(12, 118, 68, 16);
		customerPanel.add(lblLastname);

		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(12, 196, 56, 16);
		customerPanel.add(lblStreet);

		JLabel lblNationality = new JLabel("Nationality");
		lblNationality.setBounds(12, 156, 77, 16);
		customerPanel.add(lblNationality);

		JLabel lblZip = new JLabel("Zip");
		lblZip.setBounds(12, 237, 56, 16);
		customerPanel.add(lblZip);

		JLabel lblBirthdate = new JLabel("Birthdate");
		lblBirthdate.setBounds(12, 321, 63, 16);
		customerPanel.add(lblBirthdate);

		JLabel lblOthers = new JLabel("Others");
		lblOthers.setBounds(12, 481, 56, 16);
		customerPanel.add(lblOthers);

		tfCustomerId = new JTextField();
		tfCustomerId.setEditable(false);
		tfCustomerId.setBorder(null);
		tfCustomerId.setBounds(81, 36, 145, 22);
		customerPanel.add(tfCustomerId);
		tfCustomerId.setColumns(10);

		tfCustomerFirstname = new JTextField();
		tfCustomerFirstname.setBorder(null);
		tfCustomerFirstname.setBounds(81, 75, 145, 22);
		customerPanel.add(tfCustomerFirstname);
		tfCustomerFirstname.setColumns(10);

		tfCustomerLastname = new JTextField();
		tfCustomerLastname.setBorder(null);
		tfCustomerLastname.setBounds(81, 115, 145, 22);
		customerPanel.add(tfCustomerLastname);
		tfCustomerLastname.setColumns(10);

		tfCustomerNationality = new JTextField();
		tfCustomerNationality.setBorder(null);
		tfCustomerNationality.setBounds(81, 153, 145, 22);
		customerPanel.add(tfCustomerNationality);
		tfCustomerNationality.setColumns(10);

		tfCustomerStreet = new JTextField();
		tfCustomerStreet.setBorder(null);
		tfCustomerStreet.setBounds(78, 193, 145, 22);
		customerPanel.add(tfCustomerStreet);
		tfCustomerStreet.setColumns(10);

		tfCustomerZip = new JTextField();
		tfCustomerZip.setBorder(null);
		tfCustomerZip.setBounds(78, 234, 144, 22);
		customerPanel.add(tfCustomerZip);
		tfCustomerZip.setColumns(10);
		
		String[] contentDaysCustomer = { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
		JComboBox comboBoxDayCustomer = new JComboBox(contentDaysCustomer);
		comboBoxDayCustomer.setBounds(82, 319, 41, 20);
		customerPanel.add(comboBoxDayCustomer);
		
		String[] contentMonthsCustomer = { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		JComboBox comboBoxMonthCustomer = new JComboBox(contentMonthsCustomer);
		comboBoxMonthCustomer.setBounds(125, 319, 41, 20);
		customerPanel.add(comboBoxMonthCustomer);
		
		String[] contentYearsCustomer = { "", "1952","1953","1954","1955","1956","1957","1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979",
				"1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992",
				"1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010" };
		JComboBox comboBoxYearCustomer = new JComboBox(contentYearsCustomer);
		comboBoxYearCustomer.setBounds(168, 319, 58, 20);
		customerPanel.add(comboBoxYearCustomer);
		
		// Comboboxwerte werden zu Strings verarbeitet		
		JButton btnSaveCustomer = new JButton("Save");
		btnSaveCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				valueOfComboBoxDayCustomer = String.valueOf(comboBoxDayCustomer.getSelectedItem());
				valueOfComboBoxMonthCustomer = String.valueOf(comboBoxMonthCustomer.getSelectedItem());
				valueOfComboBoxYearCustomer = String.valueOf(comboBoxYearCustomer.getSelectedItem());
				
				String birthdateString = valueOfComboBoxDayCustomer + "." + valueOfComboBoxMonthCustomer + "."
						+ valueOfComboBoxDayCustomer;

				ArrayList<String> a = new ArrayList<>();
				a.add(tfCustomerId.getText());
				a.add(tfCustomerFirstname.getText());
				a.add(tfCustomerLastname.getText());
				a.add(tfCustomerNationality.getText());
				a.add(tfCustomerStreet.getText());
				a.add(tfCustomerZip.getText());
				a.add(tfCustomerCity.getText());
				a.add(birthdateString);
				a.add(tfCustomerTelefon.getText());
				a.add(tfCustomerEmail.getText());
				a.add(tfCustomerCountry.getText());
				a.add(tfCustomerOthers.getText());
				a.add("bitte aktualisieren");
				CustomerModel.checkCustomerDataFromGui(1, a);

			}
		});
		customerTable.addMouseListener(new MouseAdapter() {
			@Override
			// Sobald eine Reihe in der Tabelle ankeklickt wird, dass werden alle Textfelder
			// mit den Daten gefüllt
			public void mouseClicked(MouseEvent e) {
				try {
					CustomerModel.getSelectedRowFromOutputTable();
					comboBoxDayCustomer.setSelectedItem(jComboBoxItemDayCustomer);
					comboBoxMonthCustomer.setSelectedItem(jComboBoxItemMonthCustomer);
					comboBoxYearCustomer.setSelectedItem(jComboBoxItemYearCustomer);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {

				}
			}
		});
		btnSaveCustomer.setBorder(null);
		btnSaveCustomer.setBounds(12, 523, 50, 50);
		customerPanel.add(btnSaveCustomer);

		JButton btnUpdateCustomer = new JButton("Edit");
		btnUpdateCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				valueOfComboBoxDayCustomer = String.valueOf(comboBoxDayCustomer.getSelectedItem());
				valueOfComboBoxMonthCustomer = String.valueOf(comboBoxMonthCustomer.getSelectedItem());
				valueOfComboBoxYearCustomer = String.valueOf(comboBoxYearCustomer.getSelectedItem());
				
				String birthdateString = valueOfComboBoxDayCustomer + "." + valueOfComboBoxMonthCustomer + "."
						+ valueOfComboBoxDayCustomer;

				
				ArrayList<String> a = new ArrayList<>();
				a.add(tfCustomerId.getText());
				a.add(tfCustomerFirstname.getText());
				a.add(tfCustomerLastname.getText());
				a.add(tfCustomerNationality.getText());
				a.add(tfCustomerStreet.getText());
				a.add(tfCustomerZip.getText());
				a.add(tfCustomerCity.getText());
				a.add(birthdateString);
				a.add(tfCustomerTelefon.getText());
				a.add(tfCustomerEmail.getText());
				a.add(tfCustomerCountry.getText());
				a.add(tfCustomerOthers.getText());
				CustomerModel.checkCustomerDataFromGui(2, a);

			}
		});
		btnUpdateCustomer.setBorder(null);
		btnUpdateCustomer.setBounds(72, 523, 50, 50);
		customerPanel.add(btnUpdateCustomer);

		JButton btnClearCustomer = new JButton("Clear");
		btnClearCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerModel.clearTextfieldFromOutputTable();
				tfCustomerTelefon.setForeground(Color.black);
			}
		});
		btnClearCustomer.setBorder(null);
		btnClearCustomer.setBounds(132, 523, 50, 50);
		customerPanel.add(btnClearCustomer);

		tfCustomerTelefon = new JTextField("+66(0)xxxxxxxxx");
		tfCustomerTelefon.setToolTipText("+66(0)xxxxxxxxx");
		tfCustomerTelefon.setForeground(Color.lightGray);
		tfCustomerTelefon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfCustomerTelefon.setText("");
				tfCustomerTelefon.setForeground(Color.black);

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {

				}
			}
		});
		tfCustomerTelefon.setBorder(null);
		tfCustomerTelefon.setBounds(81, 360, 145, 20);
		customerPanel.add(tfCustomerTelefon);
		tfCustomerTelefon.setColumns(10);

		JLabel lblTelefon = new JLabel("Telefon");
		lblTelefon.setBounds(12, 363, 56, 14);
		customerPanel.add(lblTelefon);

		tfCustomerOthers = new JTextField();
		tfCustomerOthers.setBorder(null);
		tfCustomerOthers.setBounds(81, 479, 145, 21);
		customerPanel.add(tfCustomerOthers);
		tfCustomerOthers.setColumns(10);

		tfCustomerEmail = new JTextField();
		tfCustomerEmail.setBorder(null);
		tfCustomerEmail.setBounds(81, 399, 145, 20);
		customerPanel.add(tfCustomerEmail);
		tfCustomerEmail.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(11, 402, 68, 14);
		customerPanel.add(lblEmail);

		tfCustomerCountry = new JTextField();
		tfCustomerCountry.setBorder(null);
		tfCustomerCountry.setBounds(81, 440, 145, 20);
		customerPanel.add(tfCustomerCountry);
		tfCustomerCountry.setColumns(10);

		JLabel lblCountryOfStay = new JLabel("Country");
		lblCountryOfStay.setBounds(12, 443, 77, 14);
		customerPanel.add(lblCountryOfStay);

		JLabel lblNewLabel_2 = new JLabel("City");
		lblNewLabel_2.setBounds(11, 281, 46, 14);
		customerPanel.add(lblNewLabel_2);

		tfCustomerCity = new JTextField();
		tfCustomerCity.setBorder(null);
		tfCustomerCity.setBounds(81, 278, 145, 20);
		customerPanel.add(tfCustomerCity);
		tfCustomerCity.setColumns(10);

		JLabel lblSearch = new JLabel("Search in Database with");
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSearch.setBounds(356, 523, 164, 14);
		customerPanel.add(lblSearch);
		JComboBox comboBoxCustomerSearch = new JComboBox(searchStrings);
		comboBoxCustomerSearch.setBounds(530, 521, 86, 20);
		customerPanel.add(comboBoxCustomerSearch);

		JTextField tfCustomerSearch = new JTextField();
		tfCustomerSearch.setBorder(null);
		tfCustomerSearch.setBounds(356, 553, 158, 20);
		customerPanel.add(tfCustomerSearch);
		tfCustomerSearch.setColumns(10);

		JButton btnCustomerSearch = new JButton("Search");
		btnCustomerSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerModel.searchCustomer(tfCustomerSearch.getText().trim().toLowerCase(),comboBoxCustomerSearch.getSelectedItem());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCustomerSearch.setBorder(null);
		btnCustomerSearch.setBounds(529, 552, 87, 23);
		customerPanel.add(btnCustomerSearch);
		
		JButton btnActive = new JButton("Active");
		btnActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerModel.searchActive();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnActive.setBorder(null);
		btnActive.setBounds(192, 523, 50, 50);
		customerPanel.add(btnActive);

		JPanel financePanel = new JPanel();
		financePanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		financePanel.setBackground(new Color(245, 245, 245));
		tabs.addTab("Finance", null, financePanel, null);
		financePanel.setLayout(null);

		JLabel lblOutput = new JLabel("Output");
		lblOutput.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOutput.setForeground(new Color(0, 0, 0));
		lblOutput.setBounds(10, 11, 46, 14);
		financePanel.add(lblOutput);

		tf_output_nr = new JTextField();
		tf_output_nr.setEditable(false);
		tf_output_nr.setBorder(null);
		tf_output_nr.setBounds(112, 40, 167, 20);
		financePanel.add(tf_output_nr);
		tf_output_nr.setColumns(10);

		tf_amount = new JTextField();
		tf_amount.setBorder(null);
		tf_amount.setBounds(112, 81, 167, 20);
		financePanel.add(tf_amount);
		tf_amount.setColumns(10);

		tf_description = new JTextField();
		tf_description.setBorder(null);
		tf_description.setBounds(112, 121, 167, 20);
		financePanel.add(tf_description);
		tf_description.setColumns(10);

		tf_date = new JTextField();
		tf_date.setEditable(false);
		tf_date.setBorder(null);
		tf_date.setBounds(112, 202, 167, 20);
		financePanel.add(tf_date);
		tf_date.setColumns(10);

		JLabel lblReceiptNr = new JLabel("Receipt Nr");
		lblReceiptNr.setBounds(10, 43, 62, 14);
		financePanel.add(lblReceiptNr);

		JLabel lblAmount = new JLabel("Amount (THB)");
		lblAmount.setBounds(10, 84, 92, 14);
		financePanel.add(lblAmount);

		JLabel lblNewLabel = new JLabel("Description");
		lblNewLabel.setBounds(10, 124, 71, 14);
		financePanel.add(lblNewLabel);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(10, 164, 92, 14);
		financePanel.add(lblCategory);

		// Erstellt eine JComboBox und füllt diese gleich mit Werten aus einer Array
		String[] contentStrings = { "", "food", "business", "household", "travel", "other" };
		JComboBox comboBoxFinance = new JComboBox(contentStrings);
		comboBoxFinance.setBackground(SystemColor.controlHighlight);
		comboBoxFinance.setBorder(null);
		comboBoxFinance.setBounds(112, 161, 167, 20);
		financePanel.add(comboBoxFinance);
		comboBoxFinance.setSelectedIndex(0);

		JButton btnSaveFinance = new JButton("Save");
		btnSaveFinance.addActionListener(new ActionListener() {

			// Löst den Prozess für ein INSERT einer Quittung aus, dabei wird ein Parameter
			// übergeben um in der nächsten Funktion entscheiten zu können ob ein UPDATE
			// oder ein INSERT gemacht werden soll
			public void actionPerformed(ActionEvent e) {
				FinanceModel.checkComboboxFinance(comboBoxFinance.getSelectedItem().toString(), 1);
				comboBoxFinance.setSelectedIndex(0);
			}
		});
		btnSaveFinance.setBorderPainted(false);
		btnSaveFinance.setBorder(null);
		btnSaveFinance.setBounds(10, 247, 50, 50);
		financePanel.add(btnSaveFinance);

		JButton btnUpdateFinance = new JButton("Edit");
		btnUpdateFinance.addActionListener(new ActionListener() {

			// Löst den Prozess für ein UPDATE einer Quittung aus, dabei wird ein Parameter
			// übergeben um in der nächsten Funktion entscheiten zu können ob ein UPDATE
			// oder ein INSERT gemacht werden soll
			public void actionPerformed(ActionEvent e) {
				if (outputTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					FinanceModel.checkComboboxFinance(comboBoxFinance.getSelectedItem().toString(), 2);
					comboBoxFinance.setSelectedIndex(0);
				}
			}
		});
		btnUpdateFinance.setBorderPainted(false);
		btnUpdateFinance.setBorder(null);
		btnUpdateFinance.setBounds(70, 247, 50, 50);
		financePanel.add(btnUpdateFinance);

		JButton btnClearFinance = new JButton("Clear");
		btnClearFinance.addActionListener(new ActionListener() {

			// Textfelder werden auf null gesetzt
			public void actionPerformed(ActionEvent e) {
				FinanceModel.clearTextfieldFromOutputTable();
				comboBoxFinance.setSelectedIndex(0);
			}
		});
		btnClearFinance.setBorderPainted(false);
		btnClearFinance.setBorder(null);
		btnClearFinance.setBounds(130, 247, 50, 50);
		financePanel.add(btnClearFinance);

		JButton btnRefreshFinance = new JButton("Refresh");
		btnRefreshFinance.addActionListener(new ActionListener() {

			// Aktualisiert die "output" Tabelle indem die Daten neu aus der Datenbank
			// geholt werden
		public void actionPerformed(ActionEvent e) {
			refreshAllTables();
			}
		});
		btnRefreshFinance.setBorderPainted(false);
		btnRefreshFinance.setBorder(null);
		btnRefreshFinance.setBounds(304, 532, 50, 50);
		financePanel.add(btnRefreshFinance);

		JButton btnCloseFinance = new JButton("Exit");
		btnCloseFinance.addActionListener(new ActionListener() {

			// System wird geschlossen
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCloseFinance.setBorderPainted(false);
		btnCloseFinance.setBorder(null);
		btnCloseFinance.setBounds(991, 532, 50, 50);
		financePanel.add(btnCloseFinance);

		DefaultTableModel financeModel = new DefaultTableModel();

		outputTable = new JTable(financeModel);
		outputTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		outputTable.addMouseListener(new MouseAdapter() {
			@Override
			// Sobald eine Reihe in der Tabelle ankeklickt wird, dass werden alle Textfelder
			// mit den Daten gefüllt, die JComboBox wird über eine separate Funktion
			// aktuallisiert
			public void mouseClicked(MouseEvent e) {
				try {
					FinanceModel.getSelectedRowFromOutputTable();
					comboBoxFinance.setSelectedItem(jComboBoxItem);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		outputTable.setBounds(305, 40, 736, 514);
		financePanel.add(outputTable);

		outputTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				// c.setBackground(row % 2 == 0 ? Color.GREEN : Color.ORANGE);
				c.setBackground(isSelected ? Color.yellow : Color.WHITE);
				return c;
			}
		});

		JScrollPane financeScrollPane = new JScrollPane(outputTable);
		financeScrollPane.setBounds(304, 40, 737, 481);
		financePanel.add(financeScrollPane);

		// JPOPUPFINANCE
		JPopupMenu popupMenuFinance = new JPopupMenu();
		addPopup(outputTable, popupMenuFinance);

		JMenuItem mntmDeleteFinance = new JMenuItem("Delete");
		mntmDeleteFinance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (outputTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					try {
						FinanceModel.getDataFromGuiForDelete();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		popupMenuFinance.add(mntmDeleteFinance);

		JMenuItem mntmRefreshFinance = new JMenuItem("Refresh");
		mntmRefreshFinance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FinanceModel.getOutputTableDataFromSql();
					// JOptionPane.showMessageDialog(null, "Table Updated!");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		popupMenuFinance.add(mntmRefreshFinance);

		JLabel lblNewLabel_1 = new JLabel("Date");
		lblNewLabel_1.setBounds(10, 205, 46, 14);
		financePanel.add(lblNewLabel_1);

		JButton btnDeleteFinance = new JButton("Delete");
		btnDeleteFinance.setBorder(null);
		btnDeleteFinance.addActionListener(new ActionListener() {

			// Löscht die angeklickte Reihe in der Tabelle aus der datenbank
			public void actionPerformed(ActionEvent e) {
				if (outputTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
				} else {
					try {
						FinanceModel.getDataFromGuiForDelete();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnDeleteFinance.setBounds(364, 532, 50, 50);
		financePanel.add(btnDeleteFinance);

		/*
		 * Hier startet das ROOM Panel
		 */

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	
	public static void refreshAllTables() {
		try {
			BookingModel.refreshActiv();
		} catch (SQLException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Problem bei BookingModel.refreshActiv(); in Mainclassview");
		}
		try {
			RoomModel.refreshRoomStatus();
		} catch (SQLException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Problem bei RoomModel.refreshRoomStatus(); in Mainclassview");
		}
		try {
			FinanceModel.getOutputTableDataFromSql();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Problem bei FinanceModel.getOutputTableDataFromSql(); in Mainclassview");
		}
		try {
			BookingModel.getBookingTableDataFromSql();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Problem bei BookingModel.getBookingTableDataFromSql(); in Mainclassview");
		}
		try {
			CustomerModel.getCustomerTableDataFromSql();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Problem bei CustomerModel.getCustomerTableDataFromSql(); in Mainclassview");
		}
		try {
			RoomModel.getRoomTableDataFromSql(4);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Problem bei RoomModel.getRoomTableDataFromSql(4); in Mainclassview");
		}
		try {
			RoomModel.getRoomTableDataFromSql(5);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Problem bei RoomModel.getRoomTableDataFromSql(5); in Mainclassview");
		}
	}
	
	public static Integer getComboboxData(String[] array, String data) {
		int dataIndex = 0;
		for(int i = 0; i < array.length;i++){
			if (array[i].equals(data)){
				dataIndex = i;
			}
		}
		return dataIndex;
	}
}
