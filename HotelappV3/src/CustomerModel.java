import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class CustomerModel {
	private static Controller controller;

	// Konstruktor erstellt ein Objekt der Klasse Controller
	public CustomerModel() {
		controller = new Controller();
	}

	public static void searchCustomer(String name, Object o) throws SQLException {
		Controller.getCustomer(name, o);
	}
	
	public static void searchActive() throws SQLException {
		Controller.getStatus();
	}

	// Prüft auf doppelte Customer Einträge
	public static Boolean checkDoubleCostumer(String firstname, String lastname) throws SQLException {
		ArrayList<String> checkList = new ArrayList<String>();
		Boolean b = null;
		checkList = Controller.customerCheckData(firstname, lastname);
		if (checkList.size() == 1) {
			b = true;
		} else {
			b = false;
		}
		return b;
	}

	public static int getID() {
		int i = MainclassView.customerTable.getSelectedRow();
		TableModel model = MainclassView.customerTable.getModel();
		if (i < 0) {
			System.out.println("Keine Reihe ausgewählt");
		}
		return Integer.parseInt(model.getValueAt(i, 0).toString());
	}

	// Setzt von der Selected Row, die Daten in die Textfelder der Mainclass
	// Customer.
	public static void getSelectedRowFromOutputTable() throws SQLException {
		int i = MainclassView.customerTable.getSelectedRow();
		TableModel model = MainclassView.customerTable.getModel();
		if (i < 0) {
			System.out.println("Keine Reihe ausgewählt");
		} else {
			
			MainclassView.setTfCustomerId(model.getValueAt(i, 0).toString());
			MainclassView.setTfCustomerFirstname(model.getValueAt(i, 1).toString());
			MainclassView.setTfCustomerLastname(model.getValueAt(i, 2).toString());
			MainclassView.setTfCustomerNationality(model.getValueAt(i, 3).toString());
			MainclassView.setTfCustomerCity(model.getValueAt(i, 4).toString());
			MainclassView.setTfCustomerStreet(model.getValueAt(i, 5).toString());
			MainclassView.setTfCustomerCountry(model.getValueAt(i, 7).toString());
			MainclassView.setTfCustomerZip(model.getValueAt(i, 6).toString());
			MainclassView.setTfCustomerEmail(model.getValueAt(i, 8).toString());
			
			String date = model.getValueAt(i, 9).toString();
			String[] dateParts = date.split("-");
			String year = dateParts[0]; 
			String day = dateParts[2];
			String month = dateParts[1]; 
			
			MainclassView.setjComboBoxItemDayCustomer(day);
			MainclassView.setjComboBoxItemMonthCustomer(month);
			MainclassView.setjComboBoxItemYearCustomer(year);
			
			MainclassView.setTfCustomerTelefon(model.getValueAt(i, 10).toString());
			MainclassView.setTfCustomerOthers(model.getValueAt(i, 11).toString());
		}
	}

	public static void getDataFromGuiForDelete() throws SQLException {
		Controller.deleteCustomerData(MainclassView.getTfCustomerId());
		clearTextfieldFromOutputTable();
		getCustomerTableDataFromSql();
	}

	// Setzt alle Textfelder in Mainclass Finance auf leer
	public static void clearTextfieldFromOutputTable() {
		MainclassView.setTfCustomerId("");
		MainclassView.setTfCustomerFirstname("");
		MainclassView.setTfCustomerLastname("");
		MainclassView.setTfCustomerNationality("");
		MainclassView.setTfCustomerStreet("");
		MainclassView.setTfCustomerCity("");
		MainclassView.setTfCustomerCountry("");
		MainclassView.setTfCustomerZip("");
		MainclassView.setTfCustomerEmail("");
		MainclassView.setTfCustomerTelefon("");
		MainclassView.setTfCustomerOthers("");
	}

	// Holt sich alle Daten von der Klasse Controller von der Tabelle
	// "customerTable"
	public static void getCustomerTableDataFromSql() throws SQLException {
		Controller.getTabledatatFromSql(3);
	}

	// Customer Inputfelder werden auf Inhalt überprüft
	public static boolean checkCustomerInputFields(ArrayList<String> a) {
		boolean mainresult = false;

		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).trim().length() == 0) {
				mainresult = false;
			} else {
				mainresult = true;
			}
		}
		return mainresult;
	}

	// Hier wird über einen mitgegebenen Parameter überprüft ob die Daten für einen
	// INSERT == 1 oder UPDATE == 2 verwendet werden
	public static void checkCustomerDataFromGui(int index, ArrayList<String> a) {
		boolean endResult = false;
		if (index == 1) {
			endResult = checkCustomerInputFields(a);
			if (endResult == true) {
				Controller.insertCustomerData(a);
			} else {
				JOptionPane.showMessageDialog(null, "Eingabefehler, ungültige oder fehlende Werte!");
			}

		} else if (index == 2) {
			endResult = checkCustomerInputFields(a);
			if (endResult == true) {
				Controller.updateCustomerData(a);
			} else {
				JOptionPane.showMessageDialog(null, "Eingabefehler, ungültige oder fehlende Werte!");
			}
		}
	}
}
