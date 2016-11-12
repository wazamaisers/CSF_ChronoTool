import java.sql.*;
import java.util.*;

import Drive.*;
import Drive.Database.DatabaseSnapshotEntry;

public class ChronoTool
{

	public static void main( String args[] ) throws SQLException
	{

		// Simulação de username
		String username = "Tiago";

		// Importação da base de dados do ficheiro BD para o programa
		Drive drive = new Drive(username);

		// Detalhes do útilizador
		//new UserDetails(username);
		//System.out.println("");

		// Percentagem de ficheiros modificados pela última vez a cada altura do dia
		Timestamps tmstp = new Timestamps(username);
		ArrayList<Integer> lista = tmstp.getTimestamps();
		tmstp.getTimes(lista);
		System.out.println("");

		// Ver o nome dos ficheiros e/ou pastas numa determinada pasta

		String path = "root/CSF";
		System.out.println("Ficheiros dentro da pasta " + path); //Esta funcao tem de se definir na drive
		String doc_id = drive.getDirectoryDocIdByPath(path);     //Para usar o for debaixo la

		//for(DatabaseEntry entry: database_schema.get(doc_id)){
		//	System.out.println("Type: " + entry.getResourceType() + " -> Name: " + entry.getFilename());
		//}
		//System.out.println("");

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
		
		
		
	}
}