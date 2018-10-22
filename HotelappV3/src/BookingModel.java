import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/*
* @author  �zdemir Nizam
*/

public class BookingModel {
	private static ArrayList<String> list = new ArrayList<>();
	private static Controller controller;
	
	
	public void setAktiv() throws SQLException, ParseException {
		ArrayList<Boolean> al = new ArrayList<>();
		al.clear();
		//al = Controller.getAktivValues();
	}
	
	
	// Konstruktor erstellt ein Objekt der Klasse Controller
	public BookingModel() {
		controller = new Controller();
	}
	
	
	// Erh�lt Daten von Tabelle "booking" in einer ArrayList und leitet diese weiter �ber return
	public static ArrayList<String> getSelectedList() throws SQLException {
		return Controller.getDataFromMySql();
	}
	
	/*
	// Erh�lt Customer Daten von Controller f�r neue Buchung �ber CUSTOMER
	public static ArrayList<String> getCheckData() throws SQLException {
		return Controller.customerCheckData();
	}
	*/

	
	
	// Holt sich alle Daten von der Klasse Controller von der Tabelle "booking" und gibt dabei einen Parameter mit, damit im sp�teren Prozess unterschieden werden kann welche Tabelle ausgew�lt werden soll.
	public static void getBookingTableDataFromSql() throws SQLException {
		Controller.getTabledatatFromSql(1);
	}
	
	
	
	// Gettermethode f�r Buchungdaten
	public static ArrayList<String> getArrayList() {
		return list;
	}
	
	
	
	
	// Funktion hold die Buchungsdaten vom formular als Array, �berpr�ft ob ein INSERT oder ein UPDATE gemacht werden soll und l�st den Prozess aus welcher die Exceldatei erstellt
	public static void getBookingData(int parameter, ArrayList<String> parameterlist, ArrayList<String> parameterexcellist) throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		list.clear();
		list = parameterlist;
		//ArrayList<String> customerCheckList = new ArrayList<>();
		
		// Parameter 1 steht f�r UPDATE
		// Parameter 2 steht f�r UPDATE and OPEN EXCEL
		// Parameter 3 steht f�r INSERT
		// Parameter 0 steht f�r INSERT and OPEN EXCEL
		if (parameter == 0) {
			//checkCustomerData();
			Controller.insertBookingData(list);
			Excel.genereateExcelFile(parameterexcellist);
		
		} else if (parameter == 2) {
			Controller.updateBookingData(list);
			Excel.genereateExcelFile(parameterexcellist);
			
		} else if (parameter == 1) {
			Controller.updateBookingData(list);
			
		} else if (parameter == 3) {
			//checkCustomerData();
			Controller.insertBookingData(list);
			
		} else if (parameter == 5) {
			try {
				RoomModel.setRoomMap();
				list = Controller.getExceldata(getID());
				Excel.genereateExcelFile(parameterexcellist);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Keine Zeile Ausgew�hlt!");
			}
		}
	}
	
	
	
	public static int getID() {
		int i = MainclassView.bookingTable.getSelectedRow();
		TableModel model = MainclassView.bookingTable.getModel();
		if(i >= 0) {
			i = Integer.parseInt(model.getValueAt(i, 0).toString());
		}
		return i;
	}
	

	
	// Bereitet DELETE vor f�r L�schung Buchung 
	public static void getDataFromGuiForDelete(int selectedRowBookingTable) throws SQLException {
		TableModel model = MainclassView.bookingTable.getModel();
		
		// Holt booking_id von ausgew�lter Reihe
		String tempString = model.getValueAt(selectedRowBookingTable, 0).toString();
		int tempInt = Integer.parseInt(tempString);
		
		// Gibt Controller Ausl�sebefehl f�r DELETE Prozess
		Controller.deleteBookingData(tempInt);
		Controller.updateBookingNrRoom(tempInt);
		
		// Aktualisiert Tabelle
		getBookingTableDataFromSql();
	}
	
	
	
	// Erstellt ein aktuelles Datum inkl. Timestamp und gibt dieses als einen String zur�ck
	public static String getActuellDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String timeStamp = dateFormat.format(date);
		return timeStamp;
	}
	
	public static void searchActiveBooking() throws SQLException {
		Controller.getActiveBooking();
	}
	
	public static ArrayList<String> getBookingInfo(int room) throws SQLException{
		return Controller.getBookingList(room);
	}
	
	// Aktualisiert Aktive Buchungen in Table Booking und Table Customer
	public static void refreshActiv() throws SQLException, ParseException {
		// Erstellt ein aktuelles Datum
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date todayDate = new Date();
		String timeStamp = dateFormat.format(todayDate);

		ArrayList<Integer> booking_id_list = new ArrayList<>();
		ArrayList<String> booking_lastname_list = new ArrayList<>();
		booking_id_list = Controller.getBookingIdList();
		booking_lastname_list = Controller.getBookingName();
		

		for(int i = 0; i < booking_id_list.size();i++) {
			int booking_id = booking_id_list.get(i);
			int customer_nr = Controller.getCustomerId(booking_lastname_list.get(i));
			Date historyDate = Controller.getDate1(booking_id); 
			Date futureDate = Controller.getDate2(booking_id); 
		
			if(todayDate.after(historyDate) && todayDate.before(futureDate)) {
				Controller.updateBookingActiv("active", booking_id);
				Controller.updateCustomerActiv("active", customer_nr);
			} else {
				if(todayDate.before(historyDate)) {
					Controller.updateBookingActiv("open", booking_id);
					Controller.updateCustomerActiv("disabled", customer_nr);
				} if(todayDate.after(futureDate)) {
					Controller.updateBookingActiv("closed", booking_id);
					Controller.updateCustomerActiv("disabled", customer_nr);
				} /*else {
					JOptionPane.showMessageDialog(null, "Fehler in Funktion refreshActiv in Klasse BookingModel, active, closed oder open kann nicht zugeordnet werden!");
				}*/
			}
		}
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

