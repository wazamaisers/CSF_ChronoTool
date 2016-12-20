package Drive;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import Drive.Database.DatabaseSnapshotEntry;
import Drive.Database.DatabaseSyncConfigEntry;
import Drive.Database.PopulateDatabase;

public class Drive {

	boolean _databaseLoaded = false;
	private HashMap<String,ArrayList<DatabaseSnapshotEntry>> _database_schema = new HashMap<String, ArrayList<DatabaseSnapshotEntry>>();
	private ArrayList<DatabaseSnapshotEntry> _database_entrys = new ArrayList<DatabaseSnapshotEntry>();
	private DatabaseSyncConfigEntry _user_details = null;
	private ArrayList<DatabaseSnapshotEntry> _final_list = new ArrayList<DatabaseSnapshotEntry>();

	//Function to populate the database entrys and save them in java structures
	public Drive(String path){

		PopulateDatabase db = new PopulateDatabase(path);
		_databaseLoaded = db.getDbCreated();
		_database_schema = db.getDatabaseSchema();
		_database_entrys = db.getDatabaseEntrys();
		_user_details = db.getUserDetails();
	}

	//Function to alert the application that the database is loaded
	public boolean getDatabaseLoaded(){
		return _databaseLoaded;
	}

	//Function to get extensions given a path
	public HashMap<String, Integer> getExtensions(String path){

		String doc_id = getDirectoryDocIdByPath(path);
		HashMap<String, Integer> extensions = new HashMap<String, Integer>();

		for(DatabaseSnapshotEntry entry: _database_schema.get(doc_id)){

			if(entry.getResourceType().equals("file")){
				String filename = entry.getFilename();
				String extension = FilenameUtils.getExtension(filename);
				if(!extension.equals("")){
					if(extensions.containsKey(extension)){
						extensions.put(extension, extensions.get(extension)+1);
					}
					else{
						extensions.put(extension, 1);
					}
				}
			}
		}
		return extensions;
	}

	//Function to get the files of a certain extension given a path
	public ArrayList<DatabaseSnapshotEntry> getFilesByExtensionGivingPath(String path, String extension){

		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> extensions = new ArrayList<DatabaseSnapshotEntry>();

		for(DatabaseSnapshotEntry entry: _database_schema.get(doc_id)){

			if(entry.getResourceType().equals("file")){
				String filename = entry.getFilename();
				String file_extension = FilenameUtils.getExtension(filename);			
				if((extension.toLowerCase()).equals(file_extension)){
					extensions.add(entry);
				}
			}
		}
		return extensions;
	}

	//Function to get the number of each extension in a folder and its children
	public HashMap<String, Integer> getChildrenExtensions(String path){

		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> children = getArrayOfChildren(doc_id);

		HashMap<String, Integer> extensions = new HashMap<String, Integer>();

		for(DatabaseSnapshotEntry entry: children){

			if(entry.getResourceType().equals("file")){
				String filename = entry.getFilename();
				String extension = FilenameUtils.getExtension(filename);
				if(!extension.equals("")){
					if(extensions.containsKey(extension)){
						extensions.put(extension, extensions.get(extension)+1);
					}
					else{
						extensions.put(extension, 1);
					}
				}
			}
		}
		return extensions;
	}

	//Function to get extensions given a path in that folder and its children
	public ArrayList<DatabaseSnapshotEntry> getFilesByChildrenExtensionGivingPath(String path, String extension){

		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> extensions = new ArrayList<DatabaseSnapshotEntry>();
		ArrayList<DatabaseSnapshotEntry> children = getArrayOfChildren(doc_id);

		for(DatabaseSnapshotEntry entry: children){

			if(entry.getResourceType().equals("file")){
				String filename = entry.getFilename();
				String file_extension = FilenameUtils.getExtension(filename);			
				if((extension.toLowerCase()).equals(file_extension)){
					extensions.add(entry);
				}
			}
		}
		return extensions;
	}

	//Function to get all entries that are children of a certain entry by its doc_id
	public ArrayList<DatabaseSnapshotEntry> getArrayOfChildren (String parent_doc_id){

		ArrayList<DatabaseSnapshotEntry> empty_list = new ArrayList<DatabaseSnapshotEntry>();
		_final_list = empty_list;
		getArrayOfChildrenAux(parent_doc_id);
		return _final_list;
	}

	//Auxiliar recursive function of the previous one
	public void getArrayOfChildrenAux (String parent_doc_id){

		List<DatabaseSnapshotEntry> initial_list = _database_schema.get(parent_doc_id);
		for(DatabaseSnapshotEntry entry: initial_list){
			if(_database_schema.containsKey(entry.getDocId())){
				getArrayOfChildrenAux(entry.getDocId());
			}
			else{
				_final_list.add(entry);
			}
		}
		return;
	}

	//Function to get an entry by its doc_id
	public DatabaseSnapshotEntry getEntryByDocId(String doc_id){

		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(doc_id.equals(entry.getDocId())){
				return entry;
			}
		}
		return null;
	}

	//Function to get a folder doc_id by its path
	public String getDirectoryDocIdByPath(String path){
		String[] splited = path.split("/");
		List<DatabaseSnapshotEntry> possible_entrys = new ArrayList<DatabaseSnapshotEntry>();

		if(path.equals("root")){
			return "root";
		}

		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(entry.getFilename().equals(splited[splited.length-1])){
				possible_entrys.add(entry);
			}
		}

		for(DatabaseSnapshotEntry entry1: possible_entrys){
			int i = splited.length - 1;
			DatabaseSnapshotEntry entry1_parent_doc = getEntryByDocId(entry1.getParentDocId());
			while(i>0){
				if(splited[i-1].equals(entry1_parent_doc.getFilename())){
					entry1_parent_doc = getEntryByDocId(entry1_parent_doc.getParentDocId());
					i--;
				}
				else{
					break;
				}
			}
			if(i==0){
				return entry1.getDocId();
			}
		}
		return null;
	}

	//Function to get the path of an entry by its doc_id
	public String getPathByDocId(String docId){
		String path = "";
		String parent = "";
		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(entry.getDocId().equals(docId)){
				path = entry.getFilename();
				parent = entry.getParentDocId();
				while(!(parent.equals("root"))){
					for(DatabaseSnapshotEntry entry_1: _database_entrys){
						if(entry_1.getDocId().equals(parent)){
							path = entry_1.getFilename() + "/" + path;
							parent = entry_1.getParentDocId();
							break;
						}
					}
				}
			}
		}

		path = "root/" + path;

		return path;
	}

	//Function to get files within a folder given the path to that folder
	public List<DatabaseSnapshotEntry> getFilesWithinFolder(String path){
		String doc_id = getDirectoryDocIdByPath(path);
		List<DatabaseSnapshotEntry> files_list = _database_schema.get(doc_id);
		return files_list;
	}

	//Function to get the user mail of the Drive owner
	public String getUserMail(){
		return _user_details.getUserEmail();
	}

	//Function to get the Drive version of the Drive owner
	public String getDriveVersion(){
		return _user_details.getVersion();
	}

	//Function to get a percentage
	public float getPercentage(int questions, int correct) {
		float proportionCorrect = ((float) correct) / ((float) questions);
		return Math.round(proportionCorrect * 100);
	}

	//Function to get Drive activity by the times of the day
	public HashMap<String, Integer> getTimes(){

		ArrayList<Integer> timestamps = new ArrayList<Integer>();
		HashMap<String,Integer> _times = new HashMap<String, Integer>();

		for(DatabaseSnapshotEntry entry: _database_entrys){
			timestamps.add(entry.getModified());
		}

		_times.put("Dawn",0);  //2h - 6h
		_times.put("Morning",0);  //6h - 12h
		_times.put("Afternoon",0); //12h - 17h
		_times.put("Evening",0); // 17h - 20h
		_times.put("Night",0); //20h - 2h

		int total = 0;
		for (Integer item : timestamps) {
			Date date = new Date(item*1000L);
			SimpleDateFormat hour = new SimpleDateFormat("H");

			if ((Integer.parseInt(hour.format(date)) >= 2) && (Integer.parseInt(hour.format(date))) < 6){
				_times.put("Dawn", _times.get("Dawn")+1);
			}

			if ((Integer.parseInt(hour.format(date)) >= 6) && (Integer.parseInt(hour.format(date))) < 12){
				_times.put("Morning", _times.get("Morning")+1);
			}

			if ((Integer.parseInt(hour.format(date)) >= 12) && (Integer.parseInt(hour.format(date))) < 17){
				_times.put("Afternoon", _times.get("Afternoon")+1);
			}

			if ((Integer.parseInt(hour.format(date)) >= 17) && (Integer.parseInt(hour.format(date))) < 20){
				_times.put("Evening", _times.get("Evening")+1);
			}

			if ((Integer.parseInt(hour.format(date)) >= 20) && (Integer.parseInt(hour.format(date))) < 24
					|| (Integer.parseInt(hour.format(date)) >= 0) && (Integer.parseInt(hour.format(date))) < 2){
				_times.put("Night", _times.get("Night")+1);
			}	
		}

		total = _times.get("Dawn") + _times.get("Morning") + _times.get("Afternoon") + _times.get("Evening") +
				_times.get("Night");

		System.out.println("Dawn :" + getPercentage(total, _times.get("Dawn")) + "%");
		System.out.println("Morning :" + getPercentage(total, _times.get("Morning")) + "%");
		System.out.println("Afternoon :" + getPercentage(total, _times.get("Afternoon")) + "%");
		System.out.println("Evening :" + getPercentage(total, _times.get("Evening")) + "%");
		System.out.println("Night :" + getPercentage(total, _times.get("Night")) + "%");

		return _times;
	}

	//Function to get a path of an entry clicked twice by the user in the Drive Menu
	public String getPathFromTreePath(TreePath tp){

		String list = tp.toString();
		char[] list_ch = list.toCharArray();
		List<Character> path_ch = new ArrayList<Character>();
		int i = 0;
		int n = list_ch.length;
		while (i < n) {
			if(list_ch[i] == ','){
				path_ch.add('/');
				i++;
			}
			else if(list_ch[i] == '['){
				i++;
				continue;
			}
			else if(list_ch[i] == ']'){
				break;
			}
			else{
				path_ch.add(list_ch[i]);
			}
			i++;
		}
		StringBuilder builder = new StringBuilder(path_ch.size());
		for(Character ch: path_ch)
		{
			builder.append(ch);
		}
		String path = builder.toString();
		return path;
	}

	//Function to build the tree presented in the Drive Menu
	public DefaultMutableTreeNode buildTree(){

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		return buildTreeAux("root", root);
	}
	
	//Auxiliar recursive function of the previous one
	public DefaultMutableTreeNode buildTreeAux (String parent_doc_id, 
			DefaultMutableTreeNode node){

		List<DatabaseSnapshotEntry> initial_list = _database_schema.get(parent_doc_id);
		for(DatabaseSnapshotEntry entry: initial_list){
			if(_database_schema.containsKey(entry.getDocId())){
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				buildTreeAux(entry.getDocId(),parent);
				node.add(parent);
			}
			else{
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				node.add(parent);
			}
		}
		return node;
	}

	//Function to build the tree of all folders to present in the menus where the
	//choice is made by path
	public DefaultMutableTreeNode buildTreeOfDirs(){

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		return buildTreeOfDirsAux("root", root);
	}

	//Auxiliar recursive function of the previous one
	public DefaultMutableTreeNode buildTreeOfDirsAux (String parent_doc_id, 
			DefaultMutableTreeNode node){

		List<DatabaseSnapshotEntry> initial_list = _database_schema.get(parent_doc_id);
		for(DatabaseSnapshotEntry entry: initial_list){
			if(_database_schema.containsKey(entry.getDocId())){
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				buildTreeOfDirsAux(entry.getDocId(),parent);
				node.add(parent);
			}
			else if (entry.getResourceType().equals("folder")){
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				node.add(parent);
			}
		}
		return node;
	}

	//Function to get the total size of the Drive
	public String getLocalDriveSize(){
		long bytes = 0L;

		for(DatabaseSnapshotEntry entry: _database_entrys){
			bytes = bytes + entry.getSize();
		}
		return getSizeCorrect(bytes);
	}

	//Function to transform the size from bytes to Kb, Mb or Gb
	public String getSizeCorrect(long bytes){
		long kilo = 1024L;
		long mega = 1048576L;
		double giga = 1073741824L;
		String size = null;
		if(bytes < 1024){
			size = "" + bytes + " bytes";
		}
		else if((kilo < bytes) && (bytes < mega)){
			size = "" + (bytes/1024) + " Kbytes";
		}
		else if((mega < bytes) && (bytes < giga)){
			size = "" + (bytes/(1024*1024)) + " Mbytes";
		}
		else{
			size = "" + (float) (bytes/(1024*1024))/1000 + " Gbytes";
		}
		return size;
	}

	//Function to get the total number of files, documents and folder that are or not shared
	public String getFileCount(String type, boolean sharedFlag){
		String filecount = null;
		long files = 0L;
		long dirs = 0L;
		long docs = 0L;
		for(DatabaseSnapshotEntry entry: _database_entrys){
			if (sharedFlag == false){
				if (entry.getResourceType().equals("folder")){
					dirs++;
				}
				else if (entry.getResourceType().equals("document")){
					docs++;
				}
				else if (entry.getResourceType().equals("file")){
					files++;
				}
			}
			else{
				if (entry.getResourceType().equals("folder") && entry.getShared() == 1){
					dirs++;
				}
				else if (entry.getResourceType().equals("document") && entry.getShared() == 1){
					docs++;
				}
				else if (entry.getResourceType().equals("file") && entry.getShared() == 1){
					files++;
				}
			}

		}
		if (type.equals("Files")){
			filecount = "" + files;
		}
		else if (type.equals("Folders")){
			filecount = "" + dirs;
		}
		else if (type.equals("Docs")){
			filecount = "" + docs;
		}
		return filecount;
	}

	//Function to get the statistics of the top3 extensions in the Drive
	public HashMap <String,Integer> getChildrenExtensionsStatistics(String path){
		HashMap <String,Integer> top3 = new HashMap <String,Integer>();
		HashMap <String,Integer> extensions =  getChildrenExtensions("root");
		String first = "";
		String second = "";
		String third = "";
		Integer fst = 0;
		Integer scd = 0;
		Integer trd = 0;
		Integer total = 0;
		Iterator<Entry<String, Integer>> it =extensions.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			total = total + (Integer) pair.getValue();
			if (((Integer) pair.getValue())>fst){
				third = second;
				second = first;
				trd = scd;
				scd = fst;
				first = (String) pair.getKey();
				fst = (Integer) pair.getValue();
			}
			else if (((Integer) pair.getValue())<fst && ((Integer) pair.getValue())>scd){
				third = second;
				trd = scd;
				second = (String) pair.getKey();
				scd = (Integer) pair.getValue();
			}
			else if (((Integer) pair.getValue())<scd && ((Integer) pair.getValue())>trd){
				third = (String) pair.getKey();
				trd = (Integer) pair.getValue();
			}
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
		top3.put(first, fst);
		top3.put(second, scd);
		top3.put(third, trd);
		top3.put("total", total);
		System.out.println(first + " = " + fst);
		System.out.println(second + " = " + scd);
		System.out.println(third + " = " + trd);
		System.out.println("total = " + total);
		return top3;
	}

	//Function to get entries by keyWord
	public ArrayList<DatabaseSnapshotEntry> getFilesByKeyWord(String keyWord){

		ArrayList<DatabaseSnapshotEntry> finalList = new ArrayList<DatabaseSnapshotEntry>();
		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		for (DatabaseSnapshotEntry entry: _database_entrys){
			int e = 0;
			char[] fileChar = entry.getFilename().toLowerCase().toCharArray();
			for(int i = 0; i < fileChar.length; i++){
				if (fileChar[i] == keyWordChar[e]){
					e++;
				}
				else{
					e = 0;
				}
				if (e == keyWordChar.length){
					finalList.add(entry);
					break;
				}
			}
		}
		return finalList;
	}

	//Function to get entries by keyWord in a certain path
	public ArrayList<DatabaseSnapshotEntry> getFilesByKeyWordGivePath(String keyWord, String path){

		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> finalList = new ArrayList<DatabaseSnapshotEntry>();
		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		for (DatabaseSnapshotEntry entry: _database_schema.get(doc_id)){
			int e = 0;
			char[] fileChar = entry.getFilename().toLowerCase().toCharArray();
			for(int i = 0; i < fileChar.length; i++){
				if (fileChar[i] == keyWordChar[e]){
					e++;
				}
				else{
					e = 0;
				}
				if (e == keyWordChar.length){
					finalList.add(entry);
					break;
				}
			}
		}
		return finalList;
	}

	//Function to get entries by keyWord in a certain path and its children folders and files
	public ArrayList<DatabaseSnapshotEntry> getFilesByKeyWordChildrenGivePath(String keyWord, String path){

		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> children = getArrayOfChildren(doc_id);
		ArrayList<DatabaseSnapshotEntry> finalList = new ArrayList<DatabaseSnapshotEntry>();
		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		for (DatabaseSnapshotEntry entry: children){
			int e = 0;
			char[] fileChar = entry.getFilename().toLowerCase().toCharArray();
			for(int i = 0; i < fileChar.length; i++){
				if (fileChar[i] == keyWordChar[e]){
					e++;
				}
				else{
					e = 0;
				}
				if (e == keyWordChar.length){
					finalList.add(entry);
					break;
				}
			}
		}
		return finalList;
	}

	//Function to get the time of the first entry (root folder) of the Drive
	public Integer getLastTime(){
		Integer last = 2147483647;
		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(last>entry.getModified() && !entry.getDocId().equals("root")){
				last = entry.getModified();
			}
		}
		return last;
	}

	//Function to build the tree of the chrono tool mode
	public DefaultMutableTreeNode buildTimedTree(Long timestamp, DefaultMutableTreeNode root1){

		DefaultMutableTreeNode root = root1;
		return buildTimedTreeAux("root", root, timestamp);
	}

	//Auxiliar recursive function of the previous one
	public DefaultMutableTreeNode buildTimedTreeAux (String parent_doc_id, 
			DefaultMutableTreeNode node, Long timestamp){

		List<DatabaseSnapshotEntry> initial_list = _database_schema.get(parent_doc_id);
		for(DatabaseSnapshotEntry entry: initial_list){
			if(_database_schema.containsKey(entry.getDocId()) && (long) entry.getModified() < timestamp){
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				buildTreeAux(entry.getDocId(),parent);
				node.add(parent);
			}
			else if((long) entry.getModified() < timestamp){
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				node.add(parent);
			}
		}
		return node;
	}

	//Function to read .doc files given a path
	public static String readDoc(String path){
		String result = "";
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			HWPFDocument document = new HWPFDocument(fis);
			WordExtractor extract = new WordExtractor(document);
			result = extract.getText();
			fis.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

		return result;
	}

	//Function to read .docx files given a path
	public static String readDocx(String path){
		String result = "";
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);

			List<XWPFParagraph> paragraphs = document.getParagraphs();


			for (XWPFParagraph para : paragraphs) {
				result = result + para.getText() + "\n";
			}
			fis.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

		return result;
	}

	//Function to read .xls or .xlsx files given a path
	public static ArrayList<String> readExcel(String path, String type){
		ArrayList<String> result = new ArrayList<String>();
		Iterator<Row> rowIterator;
		try{
			File myFile = new File(path); 
			FileInputStream fis = new FileInputStream(myFile);
			if(type.equals("xlsx")){
				XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); 
				XSSFSheet mySheet = myWorkBook.getSheetAt(0);
				rowIterator = mySheet.iterator();
			}
			else{
				HSSFWorkbook myWorkBook = new HSSFWorkbook (fis);
				HSSFSheet mySheet = myWorkBook.getSheetAt(0);
				rowIterator = mySheet.iterator();
			}
 
			while (rowIterator.hasNext()) { 
				Row row = rowIterator.next(); // For each row, iterate through each columns 
				Iterator<Cell> cellIterator = row.cellIterator();
				String line = "";
				while (cellIterator.hasNext()) { 
					Cell cell = cellIterator.next(); 

					switch (cell.getCellType()) { 

					case Cell.CELL_TYPE_STRING: 

						line = line + cell.getStringCellValue() + "\t"; 
						break; 

					case Cell.CELL_TYPE_NUMERIC: 
						line = line + cell.getNumericCellValue() + "\t"; 
						break; 

					case Cell.CELL_TYPE_BOOLEAN: 
						line = line + cell.getBooleanCellValue() + "\t"; 
						break; 

					default : 

					}
					line = line + "\n";
				}
				result.add(line);
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

		return result;

	}

	//Function to read .pdf files given a path
	public static String readPdf(String path){
		String result = "";
		try {
		    PDDocument document = null;
		    document = PDDocument.load(new File(path));
		    document.getClass();
		    if (!document.isEncrypted()) {
		        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		        stripper.setSortByPosition(true);
		        PDFTextStripper Tstripper = new PDFTextStripper();
		        result = Tstripper.getText(document);
		    }
		    return result;
		} catch (Exception e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
		    return result;
		}
	}
	
	//Function to read .ppt files given a path
	public static String readPpt(String path){
		String result = "";
		try {
			FileInputStream inputStream = new FileInputStream(path);
			XMLSlideShow ppt = new XMLSlideShow(inputStream);
			
			CoreProperties props = ppt.getProperties().getCoreProperties();
			String title = props.getTitle();
			result = "Title: " + title + "\n\n";
			
			for (XSLFSlide slide: ppt.getSlides()) {
				result = result + "New slide..." + "\n\n";
				List<XSLFShape> shapes = slide.getShapes();
				for (XSLFShape shape: shapes) {
					if (shape instanceof XSLFTextShape) {
				        XSLFTextShape textShape = (XSLFTextShape)shape;
				        String text = textShape.getText();
				        result = result + text + "\n";
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		return result;
	}
	
	//Function to read image files given a path
	public static Image readImage(String path){
		Image image = null;
		try {
			File sourceimage = new File(path);
			image = ImageIO.read(sourceimage);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		return image;
	}

	//Function to read files with text that are not from the previous types
	public static String readFile(String path){
		String result = "";
		try {
			Scanner fileIn = new Scanner(new File(path));
			while(fileIn.hasNextLine()){
				result = result + fileIn.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		return result;
	}

	//Function to retrieve the content of a file given a path
	public void showFileContent(String path, Drive drive){
		String file_extension = FilenameUtils.getExtension(path);
		if(file_extension.equals("docx")){
			String result = readDocx(path);
			System.out.println(result);
			new DriveFileContents(result,null,null);
		}
		else if(file_extension.equals("doc")){
			String result = readDoc(path);
			System.out.println(result);
			new DriveFileContents(result,null,null);

		}
		else if(file_extension.equals("pdf")){
			String result = readPdf(path);
			new DriveFileContents(result,null,null);
		}
		else if(file_extension.equals("ppt") || file_extension.equals("pptx")){
			String result = readPpt(path);
			new DriveFileContents(result,null,null);
		}
		else if(file_extension.equals("xls")){
			ArrayList<String> result = readExcel(path,"xls");
			new DriveFileContents(null,result,null);
		}
		else if(file_extension.equals("xlsx")){
			ArrayList<String> result = readExcel(path,"xlsx");
			new DriveFileContents(null,result,null);
		}
		else{
			try {
				String result = readFile(path);
				if(result.equals("")){
					try {
						Image image = readImage(path);
						if(image == null){
							JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							new DriveFileContents(null,null,image);
						}
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else{
					new DriveFileContents(result,null,null);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	//Function to retrieve the content of a specific entry
	public String checkFileContent(DatabaseSnapshotEntry entry){
		String drivePath = System.getenv("HOMEPATH") + "/Google Drive";
		String path = drivePath + getPathByDocId(entry.getDocId()).substring(4);
		String file_extension = FilenameUtils.getExtension(path);
		if(file_extension.equals("docx")){
			String result = readDocx(path);
			return result;
		}
		else if(file_extension.equals("doc")){
			String result = readDoc(path);
			System.out.println(result);
			return result;

		}
		else if(file_extension.equals("pdf")){
			String result = readPdf(path);
			System.out.println(result);
			return result;
		}
		else if(file_extension.equals("ppt") || file_extension.equals("pptx")){
			String result = readPpt(path);
			System.out.println(result);
			return result;
		}
		else if(file_extension.equals("xls")){
			String result = "";
			ArrayList<String> arrayResult = readExcel(path,"xls");
			for(String s: arrayResult){
				result = result + arrayResult;
			}
			return result;
		}
		else if(file_extension.equals("xlsx")){
			String result = "";
			ArrayList<String> arrayResult = readExcel(path,"xlsx");
			for(String s: arrayResult){
				result = result + arrayResult;
			}
			return result;
		}
		else{
			try {
				String result = readFile(path);
				if(result.equals("")){
					return "";
				}
				else{
					return result;
				}
			} catch (Exception e) {
				return "";
			}
		}
	}

	//Function to get an entry/entries by a key word
	public ArrayList<DatabaseSnapshotEntry> getFilesByKwInContent(String keyWord, ArrayList<DatabaseSnapshotEntry> entrys){

		ArrayList<DatabaseSnapshotEntry> finalList = new ArrayList<DatabaseSnapshotEntry>();
		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		if(entrys == null){
			for (DatabaseSnapshotEntry entry: _database_entrys){
				if(!(entry.getResourceType().equals("folder"))){
					System.out.println("A analisar: " + entry.getFilename());
					int e = 0;
					char[] fileChar = checkFileContent(entry).toLowerCase().toCharArray();
					for(int i = 0; i < fileChar.length; i++){
						if (fileChar[i] == keyWordChar[e]){
							e++;
						}
						else{
							e = 0;
						}
						if (e == keyWordChar.length){
							finalList.add(entry);
							break;
						}
					}
				}
			}
		}
		
		else{
			for (DatabaseSnapshotEntry entry: entrys){
				if(!(entry.getResourceType().equals("folder"))){
					System.out.println("A analisar: " + entry.getFilename());
					int e = 0;
					char[] fileChar = checkFileContent(entry).toLowerCase().toCharArray();
					for(int i = 0; i < fileChar.length; i++){
						if (fileChar[i] == keyWordChar[e]){
							e++;
						}
						else{
							e = 0;
						}
						if (e == keyWordChar.length){
							finalList.add(entry);
							break;
						}
					}
				}
			}
		}
		
		return finalList;
	}

	//Function to get all files with their last modification within two timestamps
	public HashMap<Integer,DatabaseSnapshotEntry> getFilesBetweenTwoDates(long timestamp1, long timestamp2){
		HashMap<Integer,DatabaseSnapshotEntry> hash = new HashMap<Integer,DatabaseSnapshotEntry>();
		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(entry.getModified()>=timestamp1 && entry.getModified()<timestamp2){
				hash.put(entry.getModified(), entry);				
			}
		}
		return hash;
	}
	
}