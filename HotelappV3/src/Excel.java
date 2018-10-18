import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	private static ArrayList<String> list = new ArrayList<>();
	private static Map<Integer, String[]> roomData = new TreeMap<Integer, String[]>();

	// Entscheidet welcher Room selektiert wurde
	public static String[] getRoomInfoData() {
		String s = list.get(12);
		String[] array = null;
		if (s.equals("101")) {
			array = roomData.get(1);
		} else if (s.equals("201")) {
			array = roomData.get(2);
		} else if (s.equals("301")) {
			array = roomData.get(3);
		}
		return array;
	}

	/*
	 * ArrayList Booking Formular INDEX 0 firstname 1 lastname 2 Nation 3 Street 4
	 * Zip 5 Country 6 Email 7 Birthday 8 From 9 To 10 Deposit 11 Room 12 Day
	 */

	// Buchungsbestätigung wird als Excelfile generiert {
	public static void genereateExcelFile(ArrayList<String> parameterList) {
		// ArrayListe wird mit Daten aus Buchungsformular gefüllt
		list.clear();
		list = parameterList;
		System.out.println("My List " + list);
		// Alle Roominformationen werden initialisiert
		roomData = RoomModel.getRoomMap();

		// Room wird selektiert und Roominformationen werden für relevanten Room
		// bereitgestellt
		String[] room = getRoomInfoData();

		// Berechnet das Total
		int deposit = Integer.parseInt(list.get(12));
		int days = Integer.parseInt(list.get(13));
		int priceRoom = Integer.parseInt(room[1]);
		int result = 0;
		result = (days * priceRoom) + deposit;

		// Woorkbook wird erstellt
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Erstelle ein blankes Sheet
		XSSFSheet sheet = workbook.createSheet("Booking");

		// Objekte werden über Map in Array eingefügt
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		data.put(1, new Object[] { "", "" });
		data.put(2, new Object[] { "", "" });
		data.put(3, new Object[] { "", "######################################################################" });
		data.put(4, new Object[] { "", "" });
		data.put(5, new Object[] { "", "", "", "Booking Confirmation SSP Villa" });
		data.put(6, new Object[] { "", "" });
		data.put(7, new Object[] { "", "", "" });
		data.put(8, new Object[] { "", "", "" });
		data.put(9, new Object[] { "", "", "", "Firstname:", "", list.get(0) });
		data.put(10, new Object[] { "", "", "", "Lastname:", "", list.get(1) });
		data.put(11, new Object[] { "", "", "", "Nation:", "", list.get(2) });
		data.put(12, new Object[] { "", "", "", "Street:", "", list.get(3) });
		data.put(13, new Object[] { "", "", "", "Zip:", "", list.get(4) });
		data.put(14, new Object[] { "", "", "", "City:", "", list.get(5) });
		data.put(15, new Object[] { "", "", "", "Country:", "", list.get(6) });
		data.put(16, new Object[] { "", "", "", "Email:", "", list.get(7) });
		data.put(17, new Object[] { "", "", "", "Birthdate:", "", list.get(8) });
		data.put(18, new Object[] { "", "", "", "Special:", "", "Keine" });
		data.put(19, new Object[] { "", "", "", "", "" });
		data.put(20, new Object[] { "", "", "", "AC:", "", "", room[0] });
		data.put(21, new Object[] { "", "", "", "Room:", "", list.get(12) });
		data.put(22, new Object[] { "", "", "", "From:", "", list.get(9) });
		data.put(23, new Object[] { "", "", "", "To:", "", list.get(10) });
		data.put(24, new Object[] { "", "", "", "Days:", "", list.get(13) });
		data.put(25, new Object[] { "", "", "", "TBH/Day:", "", room[1], "THB" });
		data.put(26, new Object[] { "", "", "", "Deposit:", "", list.get(11), "THB" });
		data.put(27, new Object[] { "", "", "", "Total:", "", result, "THB" });
		data.put(28, new Object[] { "", "", "", "" });
		data.put(29, new Object[] { "", "", "", "" });
		data.put(30, new Object[] { "", "", "", "" });
		data.put(31, new Object[] { "", "", "", "Date: ", "", BookingModel.getActuellDate() });
		data.put(32, new Object[] { "", "", "", "" });
		data.put(33, new Object[] { "", "", "", "" });
		data.put(34, new Object[] { "", "", "", "Signature: " });
		data.put(35, new Object[] { "", "", "", "" });
		data.put(36, new Object[] { "", "######################################################################" });

		// Iterate over data and write to sheet
		Set<Integer> keyset = data.keySet();
		int rownum = 0;
		for (Integer key : keyset) {

			// create a row of excelsheet
			Row row = sheet.createRow(rownum++);

			// get object array of prerticuler key
			Object[] objArr = data.get(key);
			int cellnum = 0;

			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);

				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
				}
			}
		}
		try {
			// Excelfile wird im folgendem Pfad abgelegt
			FileOutputStream out = new FileOutputStream(
					new File("C:\\Users\\bachelor\\Desktop\\Web-Projekte\\Hotelapp\\test.xlsx"));
			workbook.write(out);
			out.close();

			// Excelfile wird nach folgendem Pfad geöffnet
			Desktop.getDesktop().open(new File("C:\\Users\\bachelor\\Desktop\\Web-Projekte\\Hotelapp\\test.xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
