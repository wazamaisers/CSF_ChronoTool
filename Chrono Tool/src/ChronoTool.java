import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.sql.*;
import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import Drive.*;
import Drive.Database.DatabaseSnapshotEntry;
import Skype.Skype;

public class ChronoTool
{

	public static void main( String args[] ) throws SQLException
	{

		// Simulação de username
		String path_drive = "C:\\Users\\"+ "Tiago\\AppData\\Local\\Google\\Drive";
		String path_skype = "C:\\Users\\" + "Tiago\\AppData\\Roaming\\Skype\\tiago.mousinho.martins";
		System.out.println(path_drive);
		System.out.println(path_skype);
		// Importação da base de dados do ficheiro BD para o programa
		Drive drive = new Drive(path_drive);
		Skype skype = new Skype(path_skype);

		String docxFile = "C:/sei la.docx";
		String docFile = "C:/report.doc";
		String xlsFile = "C:/Futebol.xls";
		String xlsxFile = "C:/ubr.xlsx";
		String confFile = "C:/ripd.conf";
		String datFile = "C:/3-labeled.dat";
		String imageFile = "C:/image.jpg";


		String path = imageFile;

		String file_extension = FilenameUtils.getExtension(path);
		if(file_extension.equals("docx")){
			String result = drive.readDocx(path);
			System.out.println(result);
		}
		else if(file_extension.equals("doc")){
			String result = drive.readDoc(path);
			System.out.println(result);

		}
		else if(file_extension.equals("xls")){
			ArrayList<String> result = drive.readExcel(path,"xls");
			for(String s: result){
				System.out.println(s);
			}
		}
		else if(file_extension.equals("xlsx")){
			ArrayList<String> result = drive.readExcel(path,"xlsx");
			for(String s: result){
				System.out.println(s);
			}
		}
		else{
			try {
				String result = drive.readFile(path);
				System.out.println(result);
				System.out.println("YAYA");
			} catch (Exception e) {
				System.out.println("not readable");
				// TODO: handle exception
			}
		}







		////////////// LER DOCX ////////
		//		try {
		//			File file = new File("C:/sei la.docx");
		//			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		//
		//			XWPFDocument document = new XWPFDocument(fis);
		//
		//			List<XWPFParagraph> paragraphs = document.getParagraphs();
		//
		//
		//			for (XWPFParagraph para : paragraphs) {
		//				System.out.println(para.getText());
		//			}
		//			fis.close();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//
		//		//////////////LER DOC ////////
		//		try {
		//			File file = new File("C:/report.doc");
		//			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		//
		//			HWPFDocument document = new HWPFDocument(fis);
		//			WordExtractor extract = new WordExtractor(document);
		//	        System.out.println(extract.getText());
		//			fis.close();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//
		//		///////////// LER XLSX ///////////////
		//		try{
		//			File myFile = new File("C://ubr.xlsx"); 
		//			FileInputStream fis = new FileInputStream(myFile); // Finds the workbook instance for XLSX file 
		//			XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); // Return first sheet from the XLSX workbook 
		//			XSSFSheet mySheet = myWorkBook.getSheetAt(0); // Get iterator to all the rows in current sheet 
		//			Iterator<Row> rowIterator = mySheet.iterator(); // Traversing over each row of XLSX file 
		//			while (rowIterator.hasNext()) { 
		//				Row row = rowIterator.next(); // For each row, iterate through each columns 
		//				Iterator<Cell> cellIterator = row.cellIterator(); 
		//				while (cellIterator.hasNext()) { 
		//					Cell cell = cellIterator.next(); 
		//
		//					switch (cell.getCellType()) { 
		//
		//					case Cell.CELL_TYPE_STRING: 
		//
		//						System.out.print(cell.getStringCellValue() + "\t"); 
		//						break; 
		//
		//					case Cell.CELL_TYPE_NUMERIC: 
		//						System.out.print(cell.getNumericCellValue() + "\t"); 
		//						break; 
		//
		//					case Cell.CELL_TYPE_BOOLEAN: 
		//						System.out.print(cell.getBooleanCellValue() + "\t"); 
		//						break; 
		//
		//					default : 
		//
		//					} 
		//				} 
		//				System.out.println(""); 
		//			}
		//		}
		//		catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//		////// LER XLS///////
		//		try{
		//			File myFile = new File("C://Futebol.xls"); 
		//			FileInputStream fis = new FileInputStream(myFile); // Finds the workbook instance for XLSX file 
		//			HSSFWorkbook myWorkBook = new HSSFWorkbook (fis); // Return first sheet from the XLSX workbook 
		//			HSSFSheet mySheet = myWorkBook.getSheetAt(0); // Get iterator to all the rows in current sheet 
		//			Iterator<Row> rowIterator = mySheet.iterator(); // Traversing over each row of XLSX file 
		//			while (rowIterator.hasNext()) { 
		//				Row row = rowIterator.next(); // For each row, iterate through each columns 
		//				Iterator<Cell> cellIterator = row.cellIterator(); 
		//				while (cellIterator.hasNext()) { 
		//					Cell cell = cellIterator.next(); 
		//
		//					switch (cell.getCellType()) { 
		//
		//					case Cell.CELL_TYPE_STRING: 
		//
		//						System.out.print(cell.getStringCellValue() + "\t"); 
		//						break; 
		//
		//					case Cell.CELL_TYPE_NUMERIC: 
		//						System.out.print(cell.getNumericCellValue() + "\t"); 
		//						break; 
		//
		//					case Cell.CELL_TYPE_BOOLEAN: 
		//						System.out.print(cell.getBooleanCellValue() + "\t"); 
		//						break; 
		//
		//					default : 
		//
		//					} 
		//				} 
		//				System.out.println(""); 
		//			}
		//		}
		//		catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//
		//	}
		//
		//	public static Workbook create(InputStream inp) throws IOException, InvalidFormatException {
		//		// If clearly doesn't do mark/reset, wrap up
		//		if(! inp.markSupported()) {
		//			inp = new PushbackInputStream(inp, 8);
		//		}
		//
		//		if(POIFSFileSystem.hasPOIFSHeader(inp)) {
		//			return new HSSFWorkbook(inp);
		//		}
		//		if(POIXMLDocument.hasOOXMLHeader(inp)) {
		//			return new XSSFWorkbook(OPCPackage.open(inp));
		//		}
		//		throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
		//	}



		//		String keyWord = "ML0";
		//		ArrayList<String> database_entrys = new ArrayList<String>();
		//		database_entrys.add("PkGo");
		//		database_entrys.add("ML3");
		//		database_entrys.add("ML000000002");
		//		database_entrys.add("MLas");
		//		database_entrys.add("nova imagem.bmp");
		//		database_entrys.add("ML00000002");
		//		database_entrys.add("Uis7ML0da");
		//		
		//		ArrayList<String> finalList = new ArrayList<String>();
		//		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		//		for (String entry: database_entrys){
		//			int e = 0;
		//			char[] fileChar = entry.toLowerCase().toCharArray();
		//			for(int i = 0; i < fileChar.length; i++){
		//				System.out.println("Ele: " + fileChar[i]);
		//				System.out.println("Eu: " + keyWordChar[e] + "\n");
		//				if (fileChar[i] == keyWordChar[e]){
		//					e++;
		//				}
		//				else{
		//					e = 0;
		//				}
		//				System.out.println("Valor do E: " + e);
		//				if (e == keyWordChar.length){
		//					finalList.add(entry);
		//					break;
		//				}
		//			}
		//		}
		//		System.out.println(finalList);




		/*
		// Detalhes do útilizador
		System.out.println("The user e-mail is " + drive.getUserMail() + " and the drive version is " +drive.getDriveVersion() + ".");
		System.out.println("");

		// Percentagem de ficheiros modificados pela última vez a cada altura do dia
		drive.getTimes();
		System.out.println("");

		// Ver o nome dos ficheiros e/ou pastas numa determinada pasta

		String path = "root/CSF";
		System.out.println("Ficheiros dentro da pasta " + path);
		List<DatabaseSnapshotEntry> files = drive.getFilesWithinFolder(path);

		for(DatabaseSnapshotEntry entry: files){
			System.out.println("Type: " + entry.getResourceType() + " -> Name: " + entry.getFilename());
		}
		System.out.println("");

		// Ver as extensões de todos os ficheiros que se encontram numa dada pasta e suas filhas

		System.out.println("Extensão de todos os ficheiros dentro da pasta " + path);
		HashMap <String, Integer> hm = drive.getExtensions(path);

		for(HashMap.Entry<String, Integer> entry: hm.entrySet()){
			System.out.println("Extension: ." + entry.getKey() + " -> Quantity: " + entry.getValue());
		}
		System.out.println("");

		// Ver as extensões de todos os ficheiros que se encontram numa dada pasta e suas filhas

		System.out.println("Extensão de todos os ficheiros dentro da pasta " + path + ", incluindo em pastas filhas");
		hm = drive.getChildrenExtensions(path);

		for(HashMap.Entry<String, Integer> entry: hm.entrySet()){
			System.out.println("Extension: ." + entry.getKey() + " -> Quantity: " + entry.getValue());
		}





		//////////////////////////////////////////////////////////////////////////////////////////////
		//DROPBOX

		skype.testSkype();
		 */
	}
}