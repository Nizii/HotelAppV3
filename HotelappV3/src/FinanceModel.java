import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/*
* @author  Özdemir Nizam
*/

public class FinanceModel {
	private static Controller controller;
	private static String categoryString;

	// Konstruktor erstellt ein Objekt der Klasse Controller
	public FinanceModel() {
		controller = new Controller();
	}

	// Setzt von der Selected Row, die Daten in die Textfelder der Mainclass
	// Finance.
	public static void getSelectedRowFromOutputTable() throws SQLException {
		int i = MainclassView.outputTable.getSelectedRow();
		TableModel model = MainclassView.outputTable.getModel();
		if (i < 0) {
			System.out.println("Keine Reihe ausgewählt");
		} else {
			MainclassView.setTfOutputNr(model.getValueAt(i, 0).toString());
			MainclassView.setTfAmount(model.getValueAt(i, 1).toString());
			MainclassView.setTfDescription(model.getValueAt(i, 2).toString());
			MainclassView.setjComboBoxItemFinance(model.getValueAt(i, 3).toString());
			MainclassView.setTfDate(model.getValueAt(i, 4).toString());
		}
	}

	// Holt sich alle Daten von der Klasse Controller von der Tabelle "output" und
	// gibt dabei einen Parameter mit, damit im späteren Prozess unterschieden
	// werden kann welche Tabelle ausgewält werden soll.
	public static void getOutputTableDataFromSql() throws SQLException {
		int financeParameter = 2;
		Controller.getTabledatatFromSql(financeParameter);
	}

	// Setzt alle Textfelder in Mainclass Finance auf leer
	public static void clearTextfieldFromOutputTable() {
		MainclassView.setTfOutputNr("");
		MainclassView.setTfAmount("");
		MainclassView.setTfDescription("");
		MainclassView.setTfCategory("");
		MainclassView.setTfDate("");
	}

	public static void getDataFromMainclassViewForInsert() throws SQLException {
		Controller.insertFinanceData(MainclassView.getTfAmount(), MainclassView.getTfDescription(), categoryString);
		clearTextfieldFromOutputTable();
		getOutputTableDataFromSql();
	}

	public static void getDataFromMainclassViewForUpdate() throws SQLException {
		Controller.updateFinanceData(MainclassView.getTfAmount(), MainclassView.getTfDescription(), categoryString,
				MainclassView.getTfOutputNr());
		clearTextfieldFromOutputTable();
		getOutputTableDataFromSql();
	}

	public static void getDataFromGuiForDelete() throws SQLException {
		Controller.deleteFinanceData(MainclassView.getTfOutputNr());
		clearTextfieldFromOutputTable();
		getOutputTableDataFromSql();
	}

	/*
	 * Eingabe wird auf Fehler überprüft, falls die Eingabe falsch ist, wird eine
	 * Fehlermeldung über ein Textfield ausgegeben. Es wird in jedem Fall einen
	 * Booleanwert zurück gegeben, anhand von diesem Wert wird entschieden ob der
	 * Prozess weiter laufen kann oder abgebrochen wird. Erst wenn alle drei
	 * Eingabefelder mit einem Booleanwert überprüft worden sind, wird der
	 * Hauptbooleanwert auf true gesetzt.
	 */
	public static boolean checkFinanceInputFields() {
		boolean result = false;
		int checkAmount = MainclassView.getTfAmount();
		String checkDescription = MainclassView.getTfDescription();
		String checkCategory = categoryString;

		// Überprüft ob der Eingabewert Amount negativ ist
		if (checkAmount > 0 && checkDescription.trim().length() > 0 && checkCategory.trim().length() > 0) {
			result = true;
		}
		return result;
	}

	// Erhält von MainclassView eine JComboBox und weisst der Variable
	// categoryString einen Stringwert durch den selected Index zu
	// Danach wird überprüft ob die Daten für ein INSERT oder ein UPDATE verwendet
	// werden
	public static void checkComboboxFinance(String s, int indexParameter) {
		int index = indexParameter;
		categoryString = s;

		// Hier wird über einen mitgegebenen Parameter überprüft ob die Daten für einen
		// INSERT == 1 oder UPDATE == 2 verwendet werden
		if (index == 1) {
			try {
				getDataFromMainclassViewForInsert();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (index == 2) {
			boolean b = false;
			b = checkFinanceInputFields();
			if (b == true) {
				try {
					getDataFromMainclassViewForUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Eingabefehler, ungültige Werte!");
			}
		}
	}
}
