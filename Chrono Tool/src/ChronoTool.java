import java.sql.*;
import java.util.*;

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
		
		String keyWord = "ML0";
		ArrayList<String> database_entrys = new ArrayList<String>();
		database_entrys.add("PkGo");
		database_entrys.add("ML3");
		database_entrys.add("ML000000002");
		database_entrys.add("MLas");
		database_entrys.add("nova imagem.bmp");
		database_entrys.add("ML00000002");
		database_entrys.add("Uis7ML0da");
		
		ArrayList<String> finalList = new ArrayList<String>();
		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		for (String entry: database_entrys){
			int e = 0;
			char[] fileChar = entry.toLowerCase().toCharArray();
			for(int i = 0; i < fileChar.length; i++){
				System.out.println("Ele: " + fileChar[i]);
				System.out.println("Eu: " + keyWordChar[e] + "\n");
				if (fileChar[i] == keyWordChar[e]){
					e++;
				}
				else{
					e = 0;
				}
				System.out.println("Valor do E: " + e);
				if (e == keyWordChar.length){
					finalList.add(entry);
					break;
				}
			}
		}
		System.out.println(finalList);
		
		
		
		
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