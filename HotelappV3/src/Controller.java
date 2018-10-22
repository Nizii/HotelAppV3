import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

/**
* Herr Wanner
* Hier im Conroller finden Sie die SQL Queryfunktionen so wie die Funktion getConnection und closeConnection.
* Bitte entschuldigen Sie meine Rechtsschreibung in den Kommentaren
*
* @author  Özdemir Nizam
*/

public class Controller {
	private static Connection con = null;
	private static String dbHost = "localhost"; // Hostname
	private static String dbPort = "3306"; // Port -- Standard: 3306
	private static String dbName = "hoteldb"; // Datenbankname
	private static String dbUser = "root"; // Datenbankuser
	private static String dbPass = ""; // Datenbankpasswort

	// Erstellt eine Verbindung zur MySQL Datenbank
	public static void getConnection() {
		try {

			// Datenbanktreiber für JDBC Schnittstellen laden.
			Class.forName("com.mysql.jdbc.Driver");

			// Verbindung zur JDBC-Datenbank herstellen.
			String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
			con = DriverManager.getConnection(url, dbUser, dbPass);

			// Falls Treiber nicht gefunden wird, dann wird eine Fehlermeldung ausgegeben
			// und der Prozess wird abgebrochen
		} catch (ClassNotFoundException e) {
			System.out.println("Treiber nicht gefunden");

			// Falls sonst irgend ein Fehler vorliegt, wird eine Fehlermeldung ausgegeben
			// und der Prozess wird abgebrochen
		} catch (SQLException e) {
			System.out.println("Verbindung nicht moglich");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}

	// Macht ein Update für eine Quittung
	// UPDATE FINANCE
	public static void updateFinanceData(int amount, String description, String category, int id) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql UPDATE Query und verarbeitet es in einem PreparedStatement
			String query = "update output set amount=?, description=?, category=? where output_nr=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten ein
			preparedStmt.setInt(1, amount);
			preparedStmt.setString(2, description);
			preparedStmt.setString(3, category);
			preparedStmt.setInt(4, id);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Update konnte nicht durgeführt werden!");
			System.err.println(e.getMessage());
		}
	}

	// Führt die Daten für eine neue Quittung in die Datenbank ein
	// INSERT FINANCE
	public static void insertFinanceData(int amount, String description, String category) {
		if (amount < 1 || description.length() == 0 || category.length() == 0) {
			JOptionPane.showMessageDialog(null, "Eingabefehler, ungültige Werte!");
		} else {
			try {
				// Erstellt die Verbindung zur Datenbank
				getConnection();

				// Setzt das Sql INSERT Query und verarbeitet es in einem PreparedStatement
				String query = " insert into output (amount, description, category) values (?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// Fügt die neuen Daten in das PreparedStatement ein
				preparedStmt.setInt(1, amount);
				preparedStmt.setString(2, description);
				preparedStmt.setString(3, category);
				preparedStmt.execute();

				// Schliesst die Verbindung
				closeConnection();

				// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
				// Fehlermeldung wird ausgegeben
			} catch (Exception e) {
				System.err.println("Fehler SQL INSERT konnte nicht durgeführt werden!");
				System.err.println(e.getMessage());
			}
		}
	}

	// Löscht eine ausgewählte Quittung
	// DELETE FINANCE
	public static void deleteFinanceData(int id) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql DELETE Query und verarbeitet es in einem PreparedStatement
			String query = "DELETE FROM output WHERE output_nr = " + id + ";";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL DELETE konnte nicht durgeführt werden!");
			System.err.println(e.getMessage());
		}
	}

	// Holt daten für BOOKING TABLE
	// SELECT BOOKING
	public static void getTabledatatFromSql(int p) throws SQLException {
		int parameter = p;
		String query = null;
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Überprüft via mitgegebenen Parameter, welches Query verwendet werden soll
			if (parameter == 1) {
				query = "select * from booking order by status asc";
			} else if (parameter == 2) {
				query = "select * from output order by output_date desc";
			} else if (parameter == 3) {
				query = "select * from customer order by firstname asc";
			} else if (parameter == 4) {
				query = "select room_nr, price, ac, status from room order by room_nr asc";
			} else if (parameter == 5) {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				System.out.println(dateFormat.format(date));
				query = "select booking_id, room, startDate from booking where startDate >='"+dateFormat.format(date)+"' order by startDate asc";
			}

			PreparedStatement ps;
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			// Überprüft via mitgegebenen Parameter, zu welcher Tabelle die Daten geschickt
			// werden sollen und versendet diese auch gleich
			// Hier wird vom Controller auf View gelinkt und die Klasse FinanceModel
			// überbrückt, bei Gelegenheit verbessern!!!
			if (parameter == 1) {
				MainclassView.bookingTable.setModel(DbUtils.resultSetToTableModel(rs));
			} else if (parameter == 2) {
				MainclassView.outputTable.setModel(DbUtils.resultSetToTableModel(rs));
			} else if (parameter == 3) {
				MainclassView.customerTable.setModel(DbUtils.resultSetToTableModel(rs));
			} else if (parameter == 4) {
				MainclassView.roomTable.setModel(DbUtils.resultSetToTableModel(rs));
			} else if (parameter == 5) {
				MainclassView.nextCustomerTable.setModel(DbUtils.resultSetToTableModel(rs));
			}

		} finally {
		}

		// Schliesst die Verbindung
		closeConnection();
	}

	// Holt daten für EXCEL Datei
	// SELECT BOOKING
	public static ArrayList<String> getExceldata(int booking_id) throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		String query = null;
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();
			query = "select * from booking where booking_id =" + booking_id;

			PreparedStatement ps;
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("firstname"));
				list.add(rs.getString("lastname"));
				list.add(rs.getString("nationality"));
				list.add(rs.getString("street"));
				list.add(rs.getString("zip"));
				list.add(rs.getString("city"));
				list.add(rs.getString("country"));
				list.add(rs.getString("email"));
				list.add(rs.getString("birthdate"));
				list.add(rs.getString("startDate"));
				list.add(rs.getString("endDate"));
				list.add(rs.getString("deposit"));
				list.add(rs.getString("room"));
				//list.add(rs.getString("days"));
			}

		} finally {
		}

		// Schliesst die Verbindung
		closeConnection();
		return list;
	}

	/*
	 * ArrayList Booking Formular INDEX 0 firstname 1 lastname 2 Nation 3 Street 4
	 * Zip 5 City 6 Country 7 Email 8 Birthday 9 From 10 To 11 Deposit 12 Room 13
	 * Day
	 */

	// Führt die Daten für eine neue Buchung in die Datenbank ein über einen insert
	// Befehl
	// INSERT BOOKING
	public static void insertBookingData(ArrayList<String> bookingDataList) {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		list = bookingDataList;
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql INSERT Query und verarbeitet es in einem PreparedStatement
			String query = " insert into booking (firstname, lastname, nationality, street, zip, city, country, email, birthdate, room, deposit, startdate, enddate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten in das PreparedStatement ein
			preparedStmt.setString(1, list.get(0));
			preparedStmt.setString(2, list.get(1));
			preparedStmt.setString(3, list.get(2));
			preparedStmt.setString(4, list.get(3));
			preparedStmt.setString(5, list.get(4));
			preparedStmt.setString(6, list.get(5));
			preparedStmt.setString(7, list.get(6));
			preparedStmt.setString(8, list.get(7));

			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				String dateInString = list.get(8);
				java.util.Date parsed = format.parse(dateInString);
				java.sql.Date date = new java.sql.Date(parsed.getTime());
				preparedStmt.setDate(9, date);

			} finally {

			}


			preparedStmt.setString(10, list.get(12));

			int deposit_int = Integer.parseInt(list.get(11));
			preparedStmt.setInt(11, deposit_int);

			try {

				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				String dateInString = list.get(9);
				java.util.Date parsed = format.parse(dateInString);
				java.sql.Date date = new java.sql.Date(parsed.getTime());
				preparedStmt.setDate(12, date);

			} finally {

			}

			try {

				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				String dateInString = list.get(10);
				java.util.Date parsed = format.parse(dateInString);
				java.sql.Date date = new java.sql.Date(parsed.getTime());
				preparedStmt.setDate(13, date);

			} finally {

			}
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Insert konnte nicht durgeführt werden! (Controller.insertBookingData())");
			System.err.println(e.getMessage());
		}
	}

	// Macht ein Update für eine Buchung, erstellt eine Verbindung zur Datenbank und
	// legt die neuen Daten ab
	// UPDATE BOOKING
	public static void updateBookingData(ArrayList<String> bookingDataList) {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		list = bookingDataList;

		try {

			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql INSERT Query und verarbeitet es in einem PreparedStatement
			String query = " update booking set firstname=?, lastname=?, nationality=?, street=?, zip=?, city=?, country=?, email=?, deposit=?, birthdate=?, room=?, startdate=?, enddate=? where booking_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten in das PreparedStatement ein
			preparedStmt.setString(1, list.get(0));
			preparedStmt.setString(2, list.get(1));
			preparedStmt.setString(3, list.get(2));
			preparedStmt.setString(4, list.get(3));
			preparedStmt.setString(5, list.get(4));
			preparedStmt.setString(6, list.get(5));
			preparedStmt.setString(7, list.get(6));
			preparedStmt.setString(8, list.get(7));

			int deposit_int = Integer.parseInt(list.get(11));
			preparedStmt.setInt(9, deposit_int);

			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				String dateInString = list.get(8);
				java.util.Date parsed = format.parse(dateInString);
				java.sql.Date date = new java.sql.Date(parsed.getTime());
				preparedStmt.setDate(10, date);

			} catch (SQLException e) {
				System.out.println(e.getMessage()+" Probleme mit vermutlich mit SimpleDateFormat, Controller.updateBookingData()");
				{
				}
			}


			preparedStmt.setString(11, list.get(12));

			try {
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				String dateInString = list.get(9);
				java.util.Date parsed = format.parse(dateInString);
				java.sql.Date date = new java.sql.Date(parsed.getTime());
				preparedStmt.setDate(12, date);

			} catch (SQLException e) {
				System.out.println(e.getMessage()+" Probleme mit vermutlich mit SimpleDateFormat, Controller.updateBookingData()");
				{
				}
			}

			try {
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				String dateInString = list.get(10);
				java.util.Date parsed = format.parse(dateInString);
				java.sql.Date date = new java.sql.Date(parsed.getTime());
				preparedStmt.setDate(13, date);

			} catch (SQLException e) {
				System.out.println(e.getMessage()+" Probleme mit vermutlich mit SimpleDateFormat, Controller.updateBookingData()");
				{
				}
			}
			
			String tempString = list.get(13);
			int booking_id_int = Integer.parseInt(tempString);
			preparedStmt.setInt(14, booking_id_int);

			preparedStmt.execute();
			
		
			// Schliesst die Verbindung
			closeConnection();

			// Aktualisiert die Tabelle
			BookingModel.getBookingTableDataFromSql();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Update konnte nicht durgeführt werden! (Controller.updateBookingData())");
			System.err.println(e.getMessage());
		}
	}

	// Holt Daten aus der Tabelle "booking" und leitet diese weiter über eine
	// ArrayList
	public static ArrayList<String> getDataFromMySql() throws SQLException {
		ArrayList<String> aList = new ArrayList<>();
		aList.clear();

		// Selektierte Row in der Tabelle "booking" wir ausgewählt
		int i = MainclassView.bookingTable.getSelectedRow();
		if (i < 0) {
			JOptionPane.showMessageDialog(null, "Keine Zeile Ausgewählt!");
		} else {
			TableModel model = MainclassView.bookingTable.getModel();

			// Die booking_id der selektierten Row wird zunächst in einem String gespeichert
			String bookingTableIdString = (model.getValueAt(i, 0).toString());

			// Die booking_id der selektierten Row wird in einen Int Wert konverntiert
			int bookingTableIdInt = Integer.parseInt(bookingTableIdString);

			// Verbindung zur Datenbank wird aufgebaut
			getConnection();
			Statement statement = null;

			// SQL Query wird vorbereitet mit Referenz auf die booking_id
			String tableOutput = "SELECT * from booking where booking_id =" + bookingTableIdInt;
			try {
				statement = con.createStatement();
				ResultSet rs = statement.executeQuery(tableOutput);

				// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
				while (rs.next()) {
					String bookingId = rs.getString("booking_id");
					String firstname = rs.getString("firstname");
					String lastname = rs.getString("lastname");
					String nationality = rs.getString("nationality");
					String street = rs.getString("street");
					String zip = rs.getString("zip");
					String city = rs.getString("city");
					String country = rs.getString("country");
					String email = rs.getString("email");
					String deposit = rs.getString("deposit");
					String birthdate = rs.getString("birthdate");
					String room = rs.getString("room");
					String startDate = rs.getString("startDate");
					String endDate = rs.getString("endDate");

					// Stringwerte werden in ArrayList gespeichert
					aList.add(bookingId);
					aList.add(firstname);
					aList.add(lastname);
					aList.add(nationality);
					aList.add(street);
					aList.add(zip);
					aList.add(city);
					aList.add(country);
					aList.add(email);
					aList.add(deposit);
					aList.add(birthdate);
					aList.add(room);
					aList.add(startDate);
					aList.add(endDate);

				}
			} catch (SQLException e) {
				System.out.println(e.getMessage()+" Conroller.getDataFromMySql()");
			} finally {
				if (statement != null) {
					statement.close();
				}
			}

			// Verbindung wird getrennt
			closeConnection();
		}
		return aList;
	}


	// Löscht eine ausgewählte Buchung
	public static void deleteBookingData(int id) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql DELETE Query und verarbeitet es in einem PreparedStatement
			String query = "DELETE FROM booking WHERE booking_id = " + id + ";";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL DELETE konnte nicht durgeführt werden! (Conroller.deleteBookingData())");
			System.err.println(e.getMessage());
		}
	}

	// Holt Booking_id und returned ArrayList
	@SuppressWarnings("finally")
	public static ArrayList<Integer> getBookingIdList() throws SQLException, ParseException {
		ArrayList<Integer> a = new ArrayList<Integer>();

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "SELECT booking_id from booking";
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);

			// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
			while (rs.next()) {
				a.add(rs.getInt("booking_id"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+" Conroller.getBookingIdList()");
		} finally {
			if (statement != null) {
				statement.close();
			}
			return a;
		}
	}

	// Holt firstname und lastname und returned ArrayList
	@SuppressWarnings("finally")
	public static ArrayList<String> getBookingName() throws SQLException, ParseException {
		ArrayList<String> a = new ArrayList<>();

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "SELECT lastname from booking";
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);

			// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
			while (rs.next()) {
				a.add(rs.getString("lastname"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+" Conroller.getBookingName()");
		} finally {
			if (statement != null) {
				statement.close();
			}
			return a;
		}
	}

	// Holt room_nr aus Booking und returned ArrayList
	@SuppressWarnings("finally")
	public static ArrayList<String> getRoomNrList() throws SQLException, ParseException {
		ArrayList<String> room_nr_list = new ArrayList<String>();

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "SELECT room from booking";
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);

			// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
			while (rs.next()) {
				room_nr_list.add(rs.getString("room"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+" Conroller.getRoomNrList()");
		} finally {
			if (statement != null) {
				statement.close();
			}
			return room_nr_list;
		}
	}

	// Holt room_nr aus Room und returned ArrayList
	@SuppressWarnings("finally")
	public static ArrayList<String> getRoomNrListFromRoom() throws SQLException, ParseException {
		ArrayList<String> room_nr_list = new ArrayList<String>();

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "SELECT room_nr from room";
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);

			// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
			while (rs.next()) {
				room_nr_list.add(rs.getString("room_nr"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+" Controller.getRoomNrListFromRoom()");
		} finally {
			if (statement != null) {
				statement.close();
			}
			return room_nr_list;
		}
	}


	// Holt status aus einem spezifischem room und returned
	@SuppressWarnings({ "finally", "null" })
	public static String getRoomStatus(String room_nr) throws SQLException, ParseException {
		String status = null;
		String color = null;

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "SELECT status from room where room_nr='" + room_nr + "'";
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);

			// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
			while (rs.next()) {
				status = rs.getString("status");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+" Controller.getRoomStatus()");
		} finally {
			if (statement != null) {
				statement.close();
			}

			if (status.equals("occupied")) {
				color = "red";
			} else {
				color = "green";
			}
			return color;
		}
	}

	// Macht ein Update für ein Room status
	// UPDATE Room status
	public static void setFreeRoom(String room_status, String room_nr) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql UPDATE Query und verarbeitet es in einem PreparedStatement
			String query = "update room set status=? where room_nr=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten ein
			preparedStmt.setString(1, room_status);
			preparedStmt.setString(2, room_nr);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Update konnte nicht durgeführt werden!(Controller.setFreeRoom())");
			System.err.println(e.getMessage());
		}
	}

	// Holt Date1 Table Booking und returned den Wert
	@SuppressWarnings("finally")
	public static Date getDate1(int id) throws SQLException, ParseException {
		Date d = null;

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "SELECT startdate from booking where booking_id =" + id + ";";
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);

			// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
			while (rs.next()) {
				d = (rs.getDate("startdate"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+" Controller.getDate1()");
		} finally {
			if (statement != null) {
				statement.close();
			}
			return d;
		}
	}

	// Holt Date2 Table Booking und returned den Wert
	@SuppressWarnings("finally")
	public static Date getDate2(int id) throws SQLException, ParseException {
		Date d = null;

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "SELECT enddate from booking where booking_id =" + id + ";";
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);

			// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
			while (rs.next()) {
				d = (rs.getDate("enddate"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+" Controller.getDate2()");
		} finally {
			if (statement != null) {
				statement.close();
			}
			return d;
		}
	}

	// Setzt Status der Buchung
	@SuppressWarnings("finally")
	public static void updateBookingActiv(String parameter, int id) throws SQLException, ParseException {

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "UPDATE booking SET status = ? WHERE booking_id = " + id + ";";
		PreparedStatement preparedStmt = con.prepareStatement(query);

		// Fügt die neuen Daten in das PreparedStatement ein
		preparedStmt.setString(1, parameter);

		preparedStmt.execute();
		closeConnection();
	}

	// Setzt Status bei Customer
	@SuppressWarnings("finally")
	public static void updateCustomerActiv(String parameter, int id) throws SQLException, ParseException {

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "UPDATE customer SET status = ? WHERE customer_nr = " + id + ";";
		PreparedStatement preparedStmt = con.prepareStatement(query);

		// Fügt die neuen Daten in das PreparedStatement ein
		preparedStmt.setString(1, parameter);

		preparedStmt.execute();
		closeConnection();
	}

	/*
	 * t Booking Formular INDEX 0 firstname 1 lastname 2 Nation 3 Street 4 Zip 5
	 * Country 6 Email 7 Birthday 8 From 9 To 10 Deposit 11 Room 12 Day
	 */

	// Führt die Daten für einen neuen Kunden ein
	// INSERT CUSTOMER
	public static void insertCustomerData(ArrayList<String> customerDataList) {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		list = customerDataList;
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql INSERT Query und verarbeitet es in einem PreparedStatement
			String query = " insert into customer (firstname, lastname, nationality, street, zip, city, birthdate, telefon, email, country, others, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten in das PreparedStatement ein
			preparedStmt.setString(1, list.get(1));
			preparedStmt.setString(2, list.get(2));
			preparedStmt.setString(3, list.get(3));
			preparedStmt.setString(4, list.get(4));
			preparedStmt.setString(5, list.get(5));
			preparedStmt.setString(6, list.get(6));
			preparedStmt.setString(7, list.get(7));
			preparedStmt.setString(8, list.get(8));
			preparedStmt.setString(9, list.get(9));
			preparedStmt.setString(10, list.get(10));
			preparedStmt.setString(11, list.get(11));
			preparedStmt.setString(12, list.get(12));
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();
			getTabledatatFromSql(3);
			CustomerModel.clearTextfieldFromOutputTable();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Insert konnte nicht durgeführt werden! (Controller.insertCustomerData())");
			System.err.println(e.getMessage());
		}
	}

	// Führt die UPDATE Daten für einen neuen Kunden ein
	// UPDATE CUSTOMER
	public static void updateCustomerData(ArrayList<String> customerDataList) {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		list = customerDataList;
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql INSERT Query und verarbeitet es in einem PreparedStatement
			String query = " update customer set firstname=?, lastname=?, nationality=?, street=?, zip=?, city=?, birthdate=?, telefon=?, email=?, country=?, others=? where customer_nr=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten in das PreparedStatement ein
			preparedStmt.setString(1, list.get(1));
			preparedStmt.setString(2, list.get(2));
			preparedStmt.setString(3, list.get(3));
			preparedStmt.setString(4, list.get(4));
			preparedStmt.setString(5, list.get(5));
			preparedStmt.setString(6, list.get(6));
			preparedStmt.setString(7, list.get(7));
			preparedStmt.setString(8, list.get(8));
			preparedStmt.setString(9, list.get(9));
			preparedStmt.setString(10, list.get(10));
			preparedStmt.setString(11, list.get(11));
			preparedStmt.setString(12, list.get(0));
			preparedStmt.execute();

			// Schliesst die Verbindung und aktualisiert Tabelle
			closeConnection();
			getTabledatatFromSql(3);
			CustomerModel.clearTextfieldFromOutputTable();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Insert konnte nicht durgeführt werden! (Controller.updateCustomerData())");
			System.err.println(e.getMessage());
		}
	}

	// Holt Cutomer Daten für Überprüfung
	// SELECT CUSTOMER firstname and Lastname
	public static ArrayList<String> customerCheckData(String pfirstname, String plastname) throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		String query = null;

		// Erstellt die Verbindung zur Datenbank
		getConnection();

		query = "select firstname, lastname from customer where firstname='" + pfirstname + "' and lastname='"
				+ plastname + "'";
		PreparedStatement ps;
		ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(rs.getString("firstname"));
			list.add(rs.getString("lastname"));
		}

		// Schliesst die Verbindung
		closeConnection();
		return list;
	}

	// Holt Cutomer Daten mit ID
	// SELECT CUSTOMER mit ID
	public static ArrayList<String> getCustomerDataWithId(int customer_nr) throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		String query = null;

		// Erstellt die Verbindung zur Datenbank
		getConnection();

		query = "select * from customer where customer_nr=" + customer_nr;
		PreparedStatement ps;
		ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(rs.getString("customer_nr"));
			list.add(rs.getString("firstname"));
			list.add(rs.getString("lastname"));
			list.add(rs.getString("nationality"));
			list.add(rs.getString("street"));
			list.add(rs.getString("zip"));
			list.add(rs.getString("city"));
			list.add(rs.getString("country"));
			list.add(rs.getString("email"));
			list.add(rs.getString("birthdate"));
			list.add(rs.getString("telefon"));
			list.add(rs.getString("others"));
		}

		// Schliesst die Verbindung
		closeConnection();

		return list;
	}

	// Holt Cutomer Id mit name und vorname
	// SELECT CUSTOMER mit ID
	public static int getCustomerId(String nachname) throws SQLException {
		int id = 0;
		String query = null;

		// Erstellt die Verbindung zur Datenbank
		getConnection();

		query = "select customer_nr from customer where lastname='" + nachname + "'";
		PreparedStatement ps;
		ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String s = rs.getString("customer_nr");
			id = Integer.parseInt(s);
		}

		// Schliesst die Verbindung
		closeConnection();
		return id;
	}

	// Löscht einen ausgewählten Kunden
	// DELETE CUSTOMER
	public static void deleteCustomerData(int id) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql DELETE Query und verarbeitet es in einem PreparedStatement
			String query = "DELETE FROM customer WHERE customer_nr = " + id + ";";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL DELETE konnte nicht durgeführt werden! (Controller.deleteCustomerData())");
			System.err.println(e.getMessage());
		}
	}

	// SELECT CUSTOMER aus Suchfeld
	public static void getCustomer(String name, Object o) throws SQLException {
		String query = null;

		// Erstellt die Verbindung zur Datenbank
		getConnection();

		query = "select * from customer where " + o.toString() + " LIKE '" + name + "%'";
		PreparedStatement ps;
		ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		MainclassView.customerTable.setModel(DbUtils.resultSetToTableModel(rs));

		// Schliesst die Verbindung
		closeConnection();
	}

	// SELECT COSTUMER where status = aus Combobox
	public static void getStatus() throws SQLException {
		String query = null;

		// Erstellt die Verbindung zur Datenbank
		getConnection();

		query = "select * from customer where status='active'";
		PreparedStatement ps;
		ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		MainclassView.customerTable.setModel(DbUtils.resultSetToTableModel(rs));

		// Schliesst die Verbindung
		closeConnection();
	}
	
	// SELECT BOOKING where status = Active
	public static void getActiveBooking() throws SQLException {
		String query = null;

		// Erstellt die Verbindung zur Datenbank
		getConnection();

		query = "select * from booking where status='active'";
		PreparedStatement ps;
		ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		MainclassView.bookingTable.setModel(DbUtils.resultSetToTableModel(rs));

		// Schliesst die Verbindung
		closeConnection();
	}

	// Löscht booking_id aus Room
	public static void updateBookingNrRoom(int booking_id) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql DELETE Query und verarbeitet es in einem PreparedStatement
			String query = "update room set booking_id=? where booking_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten ein
			preparedStmt.setNull(1, java.sql.Types.INTEGER);
			preparedStmt.setInt(2, booking_id);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL DELETE konnte nicht durgeführt werden!(Controller.deleteBookingNrRoom())");
			System.err.println(e.getMessage());
		}
	}

	// Macht ein Update für ein Room status
	// UPDATE Room status
	public static void updateRoomStatus(String room_status, String room_nr, int booking_id) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql UPDATE Query und verarbeitet es in einem PreparedStatement
			String query = "update room set status=?, booking_id=? where room_nr=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten ein
			preparedStmt.setString(1, room_status);
			preparedStmt.setInt(2, booking_id);
			preparedStmt.setString(3, room_nr);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Update konnte nicht durgeführt werden!(Controller.updateRoomStatus())");
			System.err.println(e.getMessage());
		}
	}

	// Macht ein Update für ein Room
	// UPDATE Room
	public static void updateRoomData(String room_nr, int price, String status, String aircon) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql UPDATE Query und verarbeitet es in einem PreparedStatement
			String query = "update room set room_nr=?, price=?, status=?, ac=? where room_nr=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten ein
			preparedStmt.setString(1, room_nr);
			preparedStmt.setInt(2, price);
			preparedStmt.setString(3, status);
			preparedStmt.setString(4, aircon);
			preparedStmt.setString(5, room_nr);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Update konnte nicht durgeführt werden!(Controller.updateRoomData())");
			System.err.println(e.getMessage());
		}
	}

	// SELECT ROOM
	public static ArrayList<String> getRoom(int p) throws SQLException {
		int parameter = p;
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		String query = null;
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();
			query = "select * from room where room_nr = " + p;

			PreparedStatement ps;
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("room_nr"));
				list.add(rs.getString("booking_id"));
				list.add(rs.getString("price"));
				list.add(rs.getString("ac"));
				list.add(rs.getString("status"));
			}

		} finally {
		}

		// Schliesst die Verbindung
		closeConnection();
		return list;
	}
	
	// SELECT ROOM
	public static ArrayList<String> getRoomList(int room) throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		String query = null;
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();
			query = "select * from room where room_nr = " + room;

			PreparedStatement ps;
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("room_nr"));
				list.add(rs.getString("booking_id"));
				list.add(rs.getString("price"));
				list.add(rs.getString("ac"));
				list.add(rs.getString("status"));
			}

		} finally {
		}

		// Schliesst die Verbindung
		closeConnection();
		return list;
	}
	
	// SELECT Booking
	public static ArrayList<String> getBookingList(int room) throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		String query = null;
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();
			query = "select * from booking where room=" + room;

			PreparedStatement ps;
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("firstname"));
				list.add(rs.getString("lastname"));
				list.add(rs.getString("nationality"));
				list.add(rs.getString("street"));
				list.add(rs.getString("zip"));
				list.add(rs.getString("city"));
				list.add(rs.getString("country"));
				list.add(rs.getString("email"));
				list.add(rs.getString("birthdate"));
				list.add(rs.getString("startDate"));
				list.add(rs.getString("endDate"));
				list.add(rs.getString("deposit"));
				list.add(rs.getString("room"));
			}

		} finally {
		}

		// Schliesst die Verbindung
		closeConnection();
		return list;
	}

	// Löscht eine ausgewählte Buchung
	public static void deleteRoomData(int id) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql DELETE Query und verarbeitet es in einem PreparedStatement
			String query = "DELETE FROM room WHERE room_nr = " + id + ";";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.execute();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL DELETE konnte nicht durgeführt werden!(Controller.deleteRoomData())");
			System.err.println(e.getMessage());
		}
	}

	// Führt die Daten für eine neue Quittung in die Datenbank ein
	// INSERT FINANCE
	public static void insertRoomData(String room_nr, int price, String status, String aircon) {

		if (price < 1 || room_nr.length() == 0 || aircon.length() == 0) {
			JOptionPane.showMessageDialog(null, "Eingabefehler, ungültige Werte!");
		} else {
			try {
				// Erstellt die Verbindung zur Datenbank
				getConnection();

				// Setzt das Sql INSERT Query und verarbeitet es in einem PreparedStatement
				String query = " insert into room (room_nr, price, status, ac) values (?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// Fügt die neuen Daten in das PreparedStatement ein
				preparedStmt.setString(1, room_nr);
				preparedStmt.setInt(2, price);
				preparedStmt.setString(3, status);
				preparedStmt.setString(4, aircon);
				preparedStmt.execute();

				// Schliesst die Verbindung
				closeConnection();

				// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
				// Fehlermeldung wird ausgegeben
			} catch (Exception e) {
				System.err.println("Fehler SQL INSERT konnte nicht durgeführt werden!(Controller.insertRoomData())");
				System.err.println(e.getMessage());
			}
		}
	}

	public static void updateBookingToRoom(String status, String room, int booking_id) {
		try {
			// Erstellt die Verbindung zur Datenbank
			getConnection();

			// Setzt das Sql INSERT Query und verarbeitet es in einem PreparedStatement
			String query = "update room set status=?, booking_id=? where room_nr=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// Fügt die neuen Daten in das PreparedStatement ein
			preparedStmt.setString(1, status);
			preparedStmt.setInt(2, booking_id);
			preparedStmt.setString(3, room);

			// execute the java preparedstatement
			preparedStmt.executeUpdate();

			// Schliesst die Verbindung
			closeConnection();

			// Falls ein Fehler vorhanden ist wird der Prozess abgebrochen und eine
			// Fehlermeldung wird ausgegeben
		} catch (Exception e) {
			System.err.println("Fehler SQL Update konnte nicht durgeführt werden! (Controller/insertBookingToRoom())");
			System.err.println(e.getMessage());
		}

	}

	// Hohlt Booking_id
	@SuppressWarnings("finally")
	public static int getBookingId(String firstname, String fromDate, String toDate)
			throws SQLException, ParseException {

		// Parst String to Date
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String fromDateString = fromDate;
		Date date1 = formatter.parse(fromDateString);
		java.sql.Date datefrom = new java.sql.Date(date1.getTime());

		// Parst String to Date
		String toDateString = toDate;
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd.MM.yyyy");
		Date date2 = formatter2.parse(toDateString);
		java.sql.Date dateto = new java.sql.Date(date2.getTime());

		int booking_id = 0;

		// Verbindung zur Datenbank wird aufgebaut
		getConnection();
		Statement statement = null;

		// SQL Query wird vorbereitet mit Referenz auf die booking_id
		String query = "SELECT booking_id from booking where firstname='" + firstname + "' and startdate='" + datefrom
				+ "' and enddate='" + dateto + "'";

		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);

			// Werte werden aus ResultSet rs in verschiedene Strings abgelegt
			while (rs.next()) {
				booking_id = rs.getInt("booking_id");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+" Controller.getBookingId()");
		} finally {
			if (statement != null) {
				statement.close();
			}
			return booking_id;
		}
	}

	// Die Funktion schliesst die Verbindung zur Datenbank
	public static void closeConnection() {
		try {
			// Schliesst Verbindung
			con.close();

			// Falls die Verbindung nicht getrennt werden kann wird eine Fehlermeldung
			// ausgegeben
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(
					"Verbindung zur Datenbank kann nicht geschlossen werden!!! (Controller.closeConnection())");
		}
	}
}