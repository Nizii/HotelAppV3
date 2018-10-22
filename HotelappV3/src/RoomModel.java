import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/*
* @author  Özdemir Nizam
*/

public class RoomModel {
	private static Map<Integer, String[]> roomData = new TreeMap<Integer, String[]>();

	// Holt Roominfo
	public static ArrayList<String> getRoom() throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		list = Controller.getRoom(202);
		return list;
	}

	// Roominformationen werden erfasst
	// Array
	// 1 = AC
	// 2 = Preis
	public static void setRoomMap() {
		roomData.put(1, new String[] { "Yes", "500" });
		roomData.put(2, new String[] { "No", "350" });
		roomData.put(3, new String[] { "Yes", "400" });
	}

	// Holt sich alle Daten von der Klasse Controller von der Tabelle "roomTable"
	public static void getRoomTableDataFromSql(int i) throws SQLException {
		Controller.getTabledatatFromSql(i);
	}

	// Bereitet DELETE vor für Löschung Room
	public static void getDataFromGuiForDelete(int selectedRowRoomTable) throws SQLException {
		TableModel model = MainclassView.roomTable.getModel();

		// Holt booking_id von ausgewälter Reihe
		String tempString = model.getValueAt(selectedRowRoomTable, 0).toString();
		int tempInt = Integer.parseInt(tempString);

		// Gibt Controller Auslösebefehl für DELETE Prozess
		Controller.deleteRoomData(tempInt);

		// Aktualisiert Tabelle
		Controller.getTabledatatFromSql(4);
	}

	/*
	 * Eingabe wird auf Fehler überprüft, falls die Eingabe falsch ist, wird eine
	 * Fehlermeldung über ein Textfield ausgegeben. Es wird in jedem Fall einen
	 * Booleanwert zurück gegeben, anhand von diesem Wert wird entschieden ob der
	 * Prozess weiter laufen kann oder abgebrochen wird. Erst wenn alle drei
	 * Eingabefelder mit einem Booleanwert überprüft worden sind, wird der
	 * Hauptbooleanwert auf true gesetzt.
	 */
	public static boolean checkRoomInputFields(String airconStringRoom) {
		boolean result = false;
		int checkPrice = MainclassView.getTfPrice();
		String checkRoomNr = MainclassView.getTfRoomNr();
		String checkAircon = airconStringRoom;

		// Überprüft ob der Eingabewert Amount negativ ist
		if (checkPrice > 0 && checkRoomNr.trim().length() > 0 && checkAircon.trim().length() > 0) {
			result = true;
		}
		return result;
	}
	
	public static ArrayList<String> getRoomInfo(int room) throws SQLException{
		return Controller.getRoomList(room);
	}

	// Setzt von der Selected Row, die Daten in die Textfelder der Mainclass Room.
	public static void getSelectedRowFromRoomTable() throws SQLException {
		int i = MainclassView.roomTable.getSelectedRow();
		TableModel model = MainclassView.roomTable.getModel();
		if (i < 0) {
			System.out.println("Keine Reihe ausgewählt");
		} else {
			MainclassView.setTfRoomNr(model.getValueAt(i, 0).toString());
			MainclassView.setTfPrice(model.getValueAt(i, 1).toString());
			MainclassView.setjComboBoxItemRoom(model.getValueAt(i, 2).toString());
			MainclassView.setjComboBoxItemRoomStatus(model.getValueAt(i, 3).toString());
		}
	}

	// Setzt alle Textfelder in Mainclass Room auf leer
	public static void clearTextfieldFromRoomTable() {
		MainclassView.setTfRoomNr("");
		MainclassView.setTfPrice("");
	}

	// Insert Room mit 1
	// Update Room mit 2
	public static void processUpdateInsert(int i, String airconStringRoom, String roomStatus) throws SQLException {

		if (i == 2) {
			Controller.updateRoomData(MainclassView.getTfRoomNr(), MainclassView.getTfPrice(), roomStatus,
					airconStringRoom);
		} else if (i == 1) {
			Controller.insertRoomData(MainclassView.getTfRoomNr(), MainclassView.getTfPrice(), roomStatus,
					airconStringRoom);
		}
		clearTextfieldFromRoomTable();
		getRoomTableDataFromSql(4);
	}

	// Erhält von MainclassView eine JComboBox und weisst der Variable
	// categoryString einen Stringwert durch den selected Index zu
	// Danach wird überprüft ob die Daten für ein INSERT oder ein UPDATE verwendet
	// werden
	// processUpdateInsert()
	// 1 = Insert
	// 2 = Update
	public static void checkComboboxRoom(String s, String s2, int indexParameter) {
		int index = indexParameter;
		String airconStringRoom = s;
		String statusStringRoom = s2;

		// Hier wird über einen mitgegebenen Parameter überprüft ob die Daten für einen
		// INSERT == 1 oder UPDATE == 2 verwendet werden
		if (index == 1) {
			try {
				// Insert
				processUpdateInsert(1, airconStringRoom, statusStringRoom);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (index == 2) {
			boolean b = false;
			b = checkRoomInputFields(airconStringRoom);
			if (b == true) {
				try {

					// Update
					processUpdateInsert(2, airconStringRoom, statusStringRoom);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Eingabefehler, ungültige Werte!");
			}
		}
	}

	// Gettermethode für Roomdaten
	public static Map<Integer, String[]> getRoomMap() {
		return roomData;
	}

	// Holt Customerdaten
	public static void getCustomer(int id) throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		list = Controller.getCustomerDataWithId(id);
	}

	// Setzt neue Buchung auf Room
	public static void setNewBooking(int customer_id, int room_nr) {

	}

	// Aktualisiert Free Occupied Rooms in Table Room
	public static void refreshRoomStatus() throws SQLException, ParseException {
		Date todayDate = new Date();
		ArrayList<Integer> booking_id_list = new ArrayList<>();
		ArrayList<String> room_nr_list_from_booking = new ArrayList<>();
		// ArrayList<String> status_list = new ArrayList<>();
		// ArrayList<String> room_nr_list_from_room = new ArrayList<>();

		booking_id_list = Controller.getBookingIdList();
		room_nr_list_from_booking = Controller.getRoomNrList();
		// room_nr_list_from_room = Controller.getRoomNrListFromRoom();
		// status_list = Controller.getRoomStatus();

		for (int i = 0; i < booking_id_list.size(); i++) {

			int booking_id = booking_id_list.get(i);
			String room_nr = room_nr_list_from_booking.get(i);

			Date historyDate = Controller.getDate1(booking_id);
			Date futureDate = Controller.getDate2(booking_id);
			
			if (todayDate.after(historyDate) && todayDate.before(futureDate)) {
				Controller.updateRoomStatus("occupied", room_nr, booking_id);
			} else {
				Controller.updateRoomStatus("free", room_nr, booking_id);
			}
			;
		}
		/*
		 * for(int i = 0; i < status_list.size(); i++) { String status =
		 * status_list.get(i);
		 * 
		 * if (status != null &&
		 * !status_list.get(i).isEmpty()||status.equals("occupied")) { } else {
		 * Controller.setFreeRoom("free", room_nr_list_from_room.get(i)); } }
		 */
	}

	// Vergleicht Buchungsdatum mit aktuellem Datum
	public static void setFree(String room_nr, String firstname, String fromDate, String toDate)
			throws SQLException, ParseException {
		Date todayDate = new Date();
		int booking_id = Controller.getBookingId(firstname, fromDate, toDate);
		Date historyDate = Controller.getDate1(booking_id);
		Date futureDate = Controller.getDate2(booking_id);

		if (!historyDate.after(todayDate) && !futureDate.before(todayDate)) {
			Controller.updateBookingToRoom("occupied", room_nr, booking_id);
		} else {
			Controller.updateBookingToRoom("free", room_nr, booking_id);
		}
	}

}
