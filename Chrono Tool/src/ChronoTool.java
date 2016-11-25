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