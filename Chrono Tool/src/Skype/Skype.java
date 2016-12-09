package Skype;

import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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

import Drive.Drive;
import Drive.DriveFileContents;
import Skype.Database.DatabaseCallsEntry;
import Skype.Database.DatabaseContactsEntry;
import Skype.Database.DatabaseMessagesEntry;
import Skype.Database.DatabaseSharedFilesStoredEntry;
import Skype.Database.DatabaseSharedLinksEntry;
import Skype.Database.PopulateDatabase;

public class Skype {

	boolean _databaseLoaded = false;
	private HashMap<String,DatabaseSharedLinksEntry> _shared_links = new HashMap<String,DatabaseSharedLinksEntry>();
	private HashMap<String,DatabaseSharedFilesStoredEntry> _shared_files = new HashMap<String,DatabaseSharedFilesStoredEntry>();
	private HashMap<String,DatabaseContactsEntry> _contacts = new HashMap<String,DatabaseContactsEntry>();
	private HashMap<String,ArrayList<DatabaseMessagesEntry>> _messages = new HashMap<String,ArrayList<DatabaseMessagesEntry>>();
	private HashMap<String,ArrayList<DatabaseCallsEntry>> _calls = new HashMap<String,ArrayList<DatabaseCallsEntry>>();
	private HashMap<String,DatabaseContactsEntry> _user_profile = new HashMap<String,DatabaseContactsEntry>();

	public Skype(String path){
		PopulateDatabase db = new PopulateDatabase(path);
		_databaseLoaded = db.getDbCreated();
		_shared_links = db.getSharedLinks();
		_shared_files = db.getSharedFiles();
		_contacts = db.getContacts();
		_messages = db.getMessages();
		_calls = db.getCalls();
		_user_profile = db.getUserProfile();
	}

	public boolean getDatabaseLoaded(){
		return _databaseLoaded;
	}

	public void testSkype(){
		System.out.println("\n - - - Testes de links partilhados - - -\n");

		for(Map.Entry<String,DatabaseSharedLinksEntry> entry: _shared_links.entrySet()){
			System.out.println("Link: " + entry.getValue().getLink());
			System.out.println("Time: " + entry.getValue().getTimestamp() + "\n");
		}

		System.out.println(" - - - Testes de ficheiros partilhados - - -\n");

		for(Map.Entry<String,DatabaseSharedFilesStoredEntry> entry: _shared_files.entrySet()){
			System.out.println("File Name: " + entry.getValue().getFileName());
			System.out.println("Local Path: " + entry.getValue().getLocalPath());
			System.out.println("User shared with: " + entry.getValue().getUserName());
		}

		System.out.println(" - - - Testes de contactos - - - \n");

		for(Map.Entry<String,DatabaseContactsEntry> entry: _contacts.entrySet()){
			System.out.println("Name: " + entry.getValue().getFullName());
			System.out.println("Skypename: " + entry.getValue().getSkypeName());
			System.out.println("Gender: " + entry.getValue().getGender());
			System.out.println("City: " + entry.getValue().getCity());
			System.out.println("Birthday: " + entry.getValue().getBirthday() + "\n");
		}

		System.out.println(" - - - Testes de chamadas - - - \n");

		for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> list: _calls.entrySet()){
			for(DatabaseCallsEntry entry: list.getValue()){
				System.out.println("Call with: " + entry.getDispname());
				System.out.println("Skypename: " + entry.getIdentity());
				System.out.println("Time: " + entry.getStartTimestamp() + "\n");
			}
		}
		System.out.println(" - - - Testes de mensagens - - - \n");

		for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> list: _messages.entrySet()){
			System.out.println("Messages with: " + list.getKey());
			for(DatabaseMessagesEntry entry: list.getValue()){
				System.out.println("From: " + entry.getAuthor());
				System.out.println("Time: " + entry.getTimestamp());
				System.out.println("Message: " + entry.getMessage() + "\n");
			}
		}
	}

	public String getUserAvatarPath(String skypeusername){
		String path = _user_profile.get(skypeusername).getAvatarImage();
		return path;
	}
	
	public String getAvatarPath(String skypeusername){
		String path = _contacts.get(skypeusername).getAvatarImage();
		return path;
	}
	
	public String[][] getBasicContactInfo(){
		String[][] arrays = new String[_contacts.entrySet().size()][4];
		int i = 0;
		for(Map.Entry<String,DatabaseContactsEntry> entry: _contacts.entrySet()){
			try{
				arrays[i][0] = entry.getValue().getFullName().toString();
			}
			catch(Exception e){
				arrays[i][0] = "";
			}

			try{
				arrays[i][1] = entry.getValue().getSkypeName().toString();
			}
			catch(Exception e){
				arrays[i][1] = "";
			}

			try{
				arrays[i][2] = entry.getValue().getGender().toString();
			}
			catch(Exception e){
				arrays[i][2] = "";
			}

			try{
				if(!(entry.getValue().getLastOnlineTimestamp()==0)){
					java.util.Date time=new java.util.Date((long)entry.getValue().getLastOnlineTimestamp()*1000);
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					arrays[i][3] = dateFormat.format(time);
				}
				else{
					arrays[i][3] = "";
				}
				
			}
			catch(Exception e){
				arrays[i][3] = "";
			}
			i++;	
		}
		return arrays;
	}

	public String getSkypeName(){
		String skypeName = "";
		for(Map.Entry<String,DatabaseContactsEntry> entry: _user_profile.entrySet()){
			skypeName = entry.getValue().getFullName();
		}
		return skypeName;
	}

	public String getSkypeUserName(){
		String skypeUserName = "";
		for(Map.Entry<String,DatabaseContactsEntry> entry: _user_profile.entrySet()){
			skypeUserName = entry.getValue().getSkypeName();
		}
		return skypeUserName;
	}

	public HashMap<String,DatabaseContactsEntry> getUserProfile(){
		return _user_profile;
	}
	
	public HashMap<String,DatabaseContactsEntry> getContacts(){
		return _contacts;
	}

	public String[][] getAllCalls(){
		int size = 0;
		for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> entry: _calls.entrySet()){
			for(DatabaseCallsEntry entry1: entry.getValue()){
				if(entry1.getCreationTimestamp()==0){
					continue;
				}
				size++;
			}
		}
		String[][] arrays = new String[size][6];
		int i = 0;
		for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> entry: _calls.entrySet()){
			for(DatabaseCallsEntry entry1: entry.getValue()){
				if(entry1.getCreationTimestamp()==0){
					continue;
				}
				try{
					arrays[i][0] = entry1.getDispname();
				}
				catch(Exception e){
					arrays[i][0] = "";
				}

				try{
					arrays[i][1] = entry1.getIdentity();
				}
				catch(Exception e){
					arrays[i][1] = "";
				}

				try{
					if(entry1.getWhoCalls().equals("1")){
						arrays[i][2] = entry1.getDispname();
					}
					else if(entry1.getWhoCalls().equals("2")){
						arrays[i][2] = getSkypeName();			
					}

				}
				catch(Exception e){
					arrays[i][2] = "";
				}

				try{
					if(!(entry1.getStartTimestamp() == 0)){
						arrays[i][3] = "Yes";
					}
					else{
						arrays[i][3] = "No";
					}
				}
				catch(Exception e){
					arrays[i][3] = "No";
				}

				try{
					java.util.Date time=new java.util.Date((long)entry1.getCreationTimestamp()*1000);
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					arrays[i][4] = dateFormat.format(time);
				}
				catch(Exception e){
					arrays[i][4] = "";
				}

				try{
					arrays[i][5] = entry1.getCreationTimestamp().toString();
				}
				catch(Exception e){
					arrays[i][5] = "";
				}
				i++;
			}
		}
		return arrays;
	}

	public String[][] getAllMessages(){
		int size = 0;
		for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> entry: _messages.entrySet()){
			for(DatabaseMessagesEntry entry1: entry.getValue()){
				try{
					if(entry1.getMessage().length()>=20){
						if(entry1.getMessage().substring(0,15).equals("<partlist type=")){
							continue;
						}
					}
				}
				catch(Exception e){
					continue;
				}
				size++;
			}
		}
		String[][] arrays = new String[size][6];
		int i = 0;
		for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> entry: _messages.entrySet()){
			for(DatabaseMessagesEntry entry1: entry.getValue()){
				try{
					if(entry1.getMessage().length()>=20){
						if(entry1.getMessage().substring(0,15).equals("<partlist type=")){
							continue;
						}
					}
				}
				catch(Exception e){
					continue;
				}

				try{
					arrays[i][0] = entry1.getChatname();
				}
				catch(Exception e){
					arrays[i][0] = "";
				}

				try{
					arrays[i][1] = entry1.getAuthor();
				}
				catch(Exception e){
					arrays[i][1] = "";
				}

				try{
					arrays[i][2] = entry1.getFromDispname();
				}
				catch(Exception e){
					arrays[i][2] = "";
				}

				try{
					arrays[i][3] = entry1.getMessage();
				}
				catch(Exception e){
					arrays[i][3] = "";
				}

				try{
					java.util.Date time=new java.util.Date((long)entry1.getTimestamp()*1000);
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					arrays[i][4] = dateFormat.format(time);
				}
				catch(Exception e){
					arrays[i][4] = "";
				}

				try{
					arrays[i][5] = entry1.getTimestamp().toString();
				}
				catch(Exception e){
					arrays[i][5] = "";
				}
				i++;
			}
		}
		return arrays;
	}

	public String[][] getAllLinks(){
		int size = 0;
		for(Map.Entry<String,DatabaseSharedLinksEntry> entry: _shared_links.entrySet()){
			if(!(entry.getValue().getLink().charAt(0) == 'i')){
				size++;
			}
		}
		String[][] arrays = new String[size][4];
		int i = 0;
		for(Map.Entry<String,DatabaseSharedLinksEntry> entry: _shared_links.entrySet()){
			if(!(entry.getValue().getLink().charAt(0) == 'i')){
				try{
					arrays[i][0] = entry.getValue().getLink().substring(1);
				}
				catch(Exception e){
					arrays[i][0] = "";
				}

				try{
					arrays[i][1] = entry.getValue().getType();
				}
				catch(Exception e){
					arrays[i][1] = "";
				}
				try{
					java.util.Date time=new java.util.Date((long)entry.getValue().getTimestamp()*1000);
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					arrays[i][2] = dateFormat.format(time);
				}
				catch(Exception e){
					arrays[i][2] = "";
				}
				try{
					arrays[i][3] = entry.getValue().getTimestamp().toString();
				}
				catch(Exception e){
					arrays[i][3] = "";
				}
				i++;
			}
		}
		return arrays;
	}

	public String[][] getAllFiles(){
		String[][] arrays = new String[_shared_files.entrySet().size()][4];
		int i = 0;
		for(Map.Entry<String,DatabaseSharedFilesStoredEntry> entry: _shared_files.entrySet()){
			try{
				arrays[i][0] = entry.getValue().getFileName();
			}
			catch(Exception e){
				arrays[i][0] = "";
			}

			try{
				arrays[i][1] = entry.getValue().getLocalPath();
			}
			catch(Exception e){
				arrays[i][1] = "";
			}

			try{
				arrays[i][2] = entry.getValue().getUserName();
			}
			catch(Exception e){
				arrays[i][2] = "";
			}

			try{
				arrays[i][3] = getSizeCorrect(entry.getValue().getSize());
			}
			catch(Exception e){
				arrays[i][3] = "";
			}
			i++;
		}
		return arrays;
	}

	public String getSizeCorrect(long bytes){
		long kilo = 1024L;
		long mega = 1048576L;
		long giga = 10737418424L;
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
			size = "" + (bytes/(1024*1024*1024)) + " Gbytes";
		}
		return size;
	}

	public int getTotalContacts(){
		return _contacts.size();
	}

	public int getTotalCalls(){
		int size = 0;
		for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> entry: _calls.entrySet()){
			for(DatabaseCallsEntry entry1: entry.getValue()){
				if(!(entry1.getCreationTimestamp()==0)){
					size++;
				}
			}
		}
		return size;
	}

	public int getTotalMessages(){
		int size = 0;
		for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> entry: _messages.entrySet()){
			for(DatabaseMessagesEntry entry1: entry.getValue()){
				try{
					if(entry1.getMessage().length()>=20){
						if(entry1.getMessage().substring(0,15).equals("<partlist type=")){
							continue;
						}
					}
				}
				catch(Exception e){
					continue;
				}
				size++;
			}
		}
		return size;
	}

	public int getTotalFiles(){
		return _shared_files.size();
	}

	public int getTotalLinks(){
		return _shared_links.size();
	}

	public ArrayList<String> getSkypeNamesList(){
		ArrayList<String> list = new ArrayList<String>();
		for(Map.Entry<String,DatabaseContactsEntry> entry: _contacts.entrySet()){
			list.add(entry.getValue().getSkypeName());
		}
		return list;
	}

	public int getLastCallsByPeriod(String period){
		java.util.Date now = new java.util.Date();
		LocalDate now1 = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate timeAgo = null;
		if(period.equals("weeks")){
			timeAgo = now1.minus(1, ChronoUnit.WEEKS);
		}
		else if(period.equals("months")){
			timeAgo = now1.minus(1, ChronoUnit.MONTHS);
		}

		long timestamp = Timestamp.valueOf(timeAgo.atStartOfDay()).getTime()/1000;
		int calls = 0;
		for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> list: _calls.entrySet()){
			for(DatabaseCallsEntry entry: list.getValue()){
				if(entry.getCreationTimestamp() >= timestamp){
					calls++;
				}
			}
		}
		return calls;
	}

	public int getLastMessagesByPeriod(String period){
		java.util.Date now = new java.util.Date();
		LocalDate now1 = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate timeAgo = null;
		if(period.equals("weeks")){
			timeAgo = now1.minus(1, ChronoUnit.WEEKS);
		}
		else if(period.equals("months")){
			timeAgo = now1.minus(1, ChronoUnit.MONTHS);
		}

		long timestamp = Timestamp.valueOf(timeAgo.atStartOfDay()).getTime()/1000;
		int calls = 0;
		for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> list: _messages.entrySet()){
			for(DatabaseMessagesEntry entry: list.getValue()){
				if(entry.getTimestamp() >= timestamp){
					calls++;
				}
			}
		}
		return calls;
	}

	public HashMap <String,HashMap<String,Integer>> getTop(String type){
		HashMap <String,Integer> top1 = new HashMap <String,Integer>();
		HashMap <String,Integer> top2 = new HashMap <String,Integer>();
		HashMap <String,Integer> top3 = new HashMap <String,Integer>();
		HashMap <String,HashMap<String,Integer>> ret = 
				new HashMap <String,HashMap<String,Integer>>();

		String first = "";
		String second = "";
		String third = "";
		Integer fst = 0;
		Integer scd = 0;
		Integer trd = 0;
		String person ="";
		Integer total = 0;
		if(type.equals("messages")){
			for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> list: _messages.entrySet()){
				for(DatabaseMessagesEntry entry1: list.getValue()){
					try{
						if(entry1.getMessage().length()>=20){
							if(entry1.getMessage().substring(0,15).equals("<partlist type=")){
								continue;
							}
						}
					}
					catch(Exception e){
						continue;
					}
					total++;
				}

				person = list.getKey();
				if (total>fst){
					third = second;
					second = first;
					trd = scd;
					scd = fst;
					first = person;
					fst = total;
				}
				else if (total<fst && total>scd){
					third = second;
					trd = scd;
					second = person;
					scd = total;
				}
				else if (total<scd && total>trd){
					third = person;
					trd = total;
				}
			}
		}

		else if(type.equals("calls")){
			for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> list: _calls.entrySet()){
				person = list.getKey();
				total = list.getValue().size();
				if (total>fst){
					third = second;
					second = first;
					trd = scd;
					scd = fst;
					first = person;
					fst = total;
				}
				else if (total<=fst && total>scd){
					third = second;
					trd = scd;
					second = person;
					scd = total;
				}
				else if (total<=scd && total>trd){
					third = person;
					trd = total;
				}
			}
		}


		top1.put(first, fst);
		top2.put(second, scd);
		top3.put(third, trd);
		ret.put("First", top1);
		ret.put("Second", top2);
		ret.put("Third", top3);
		return ret;
	}

	public String[][] getAllCallsByName(String skypeName){
		int size = 0;
		ArrayList<DatabaseCallsEntry> list = _calls.get(skypeName);
		for(DatabaseCallsEntry entry1: list){
			if(entry1.getCreationTimestamp()==0){
				continue;
			}
			size++;
		}
		String[][] arrays = new String[size][6];
		int i = 0;
		for(DatabaseCallsEntry entry1: list){
			if(entry1.getCreationTimestamp()==0){
				continue;
			}
			try{
				arrays[i][0] = entry1.getDispname();
			}
			catch(Exception e){
				arrays[i][0] = "";
			}

			try{
				arrays[i][1] = entry1.getIdentity();
			}
			catch(Exception e){
				arrays[i][1] = "";
			}

			try{
				if(entry1.getWhoCalls().equals("1")){
					arrays[i][2] = entry1.getDispname();
				}
				else if(entry1.getWhoCalls().equals("2")){
					arrays[i][2] = getSkypeName();			
				}

			}
			catch(Exception e){
				arrays[i][2] = "";
			}

			try{
				if(!(entry1.getStartTimestamp() == 0)){
					arrays[i][3] = "Yes";
				}
				else{
					arrays[i][3] = "No";
				}
			}
			catch(Exception e){
				arrays[i][3] = "No";
			}

			try{
				java.util.Date time=new java.util.Date((long)entry1.getCreationTimestamp()*1000);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				arrays[i][4] = dateFormat.format(time);
			}
			catch(Exception e){
				arrays[i][4] = "";
			}

			try{
				arrays[i][5] = entry1.getCreationTimestamp().toString();
			}
			catch(Exception e){
				arrays[i][5] = "";
			}
			i++;
		}
		return arrays;
	}

	public String[][] getAllMessagesByName(String skypeName){
		int size = 0;
		ArrayList<DatabaseMessagesEntry> list = _messages.get(skypeName);
		for(DatabaseMessagesEntry entry1: list){
			try{
				if(entry1.getMessage().length()>=20){
					if(entry1.getMessage().substring(0,15).equals("<partlist type=")){
						continue;
					}
				}
			}
			catch(Exception e){
				continue;
			}
			size++;
		}
		String[][] arrays = new String[size][6];
		int i = 0;
		for(DatabaseMessagesEntry entry1: list){
			try{
				if(entry1.getMessage().length()>=20){
					if(entry1.getMessage().substring(0,15).equals("<partlist type=")){
						continue;
					}
				}
			}
			catch(Exception e){
				continue;
			}

			try{
				arrays[i][0] = entry1.getChatname();
			}
			catch(Exception e){
				arrays[i][0] = "";
			}

			try{
				arrays[i][1] = entry1.getAuthor();
			}
			catch(Exception e){
				arrays[i][1] = "";
			}

			try{
				arrays[i][2] = entry1.getFromDispname();
			}
			catch(Exception e){
				arrays[i][2] = "";
			}

			try{
				arrays[i][3] = entry1.getMessage();
			}
			catch(Exception e){
				arrays[i][3] = "";
			}

			try{
				java.util.Date time=new java.util.Date((long)entry1.getTimestamp()*1000);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				arrays[i][4] = dateFormat.format(time);
			}
			catch(Exception e){
				arrays[i][4] = "";
			}

			try{
				arrays[i][5] = entry1.getTimestamp().toString();
			}
			catch(Exception e){
				arrays[i][5] = "";
			}
			i++;
		}

		return arrays;
	}

	public String[][] getAllFilesByName(String skypeName){
		int size = 0;
		for(Map.Entry<String,DatabaseSharedFilesStoredEntry> entry: _shared_files.entrySet()){
			if(entry.getValue().getUserName().equals(skypeName)){
				size++;
			}
		}

		String[][] arrays = new String[size][4];
		int i = 0;
		for(Map.Entry<String,DatabaseSharedFilesStoredEntry> entry: _shared_files.entrySet()){
			if(entry.getValue().getUserName().equals(skypeName)){
				try{
					arrays[i][0] = entry.getValue().getFileName();
				}
				catch(Exception e){
					arrays[i][0] = "";
				}

				try{
					arrays[i][1] = entry.getValue().getLocalPath();
				}
				catch(Exception e){
					arrays[i][1] = "";
				}

				try{
					arrays[i][2] = entry.getValue().getUserName();
				}
				catch(Exception e){
					arrays[i][2] = "";
				}

				try{
					arrays[i][3] = getSizeCorrect(entry.getValue().getSize());
				}
				catch(Exception e){
					arrays[i][3] = "";
				}
				i++;
			}
		}
		return arrays;
	}

	public void showFileContent(String path){

		String file_extension = FilenameUtils.getExtension(path);
		if(file_extension.equals("docx")){
			String result = Drive.readDocx(path);
			System.out.println(result);
			new DriveFileContents(result,null,null);
		}
		else if(file_extension.equals("doc")){
			String result = Drive.readDoc(path);
			System.out.println(result);
			new DriveFileContents(result,null,null);

		}
		else if(file_extension.equals("pdf")){
			String result = Drive.readPdf(path);
			System.out.println(result);
			new DriveFileContents(result,null,null);
		}
		else if(file_extension.equals("ppt") || file_extension.equals("pptx")){
			String result = Drive.readPpt(path);
			System.out.println(result);
			new DriveFileContents(result,null,null);
		}
		else if(file_extension.equals("xls")){
			ArrayList<String> result = Drive.readExcel(path,"xls");
			for(String s: result){
				System.out.println(s);
			}
			new DriveFileContents(null,result,null);
		}
		else if(file_extension.equals("xlsx")){
			ArrayList<String> result = Drive.readExcel(path,"xlsx");
			for(String s: result){
				System.out.println(s);
			}
			new DriveFileContents(null,result,null);
		}
		else{
			try {
				String result = Drive.readFile(path);
				if(result.equals("")){
					try {
						Image image = Drive.readImage(path);
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

	public void links(String link){
		try {
			Desktop.getDesktop().browse(new URI(link));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}