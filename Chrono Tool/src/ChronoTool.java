import java.sql.*;
import java.util.*;

import Drive.*;

public class ChronoTool
{

	public static void main( String args[] ) throws SQLException
	{
		
		// Simulação de username
		String username = "Tiago";
		
		// Importação da base de dados do ficheiro BD para o programa
		Database database = new Database(username);
		HashMap <String, ArrayList<DatabaseEntry>> database_schema = database.getDatabaseSchema();
		ArrayList<DatabaseEntry> database_entrys = database.getDatabaseEntrys();
		DatabaseAnalisis database_analisis = new DatabaseAnalisis();
		
		// Detalhes do útilizador
		new UserDetails(username);
		System.out.println("");
		
		// Percentagem de ficheiros modificados pela última vez a cada altura do dia
		Timestamps tmstp = new Timestamps(username);
		ArrayList<Integer> lista = tmstp.getTimestamps();
		tmstp.getTimes(lista);
		System.out.println("");
		
		// Ver o nome dos ficheiros e/ou pastas numa determinada pasta
		
		System.out.println("Ficheiros dentro da pasta AR");
		String path = "root/AR";
		String doc_id = database_analisis.getDirectoryDocIdByPath(path, database_entrys);
		
		for(DatabaseEntry entry: database_schema.get(doc_id)){
			System.out.println(entry.getFilename());
		}
		System.out.println("");
		
		// Ver as extensões de todos os ficheiros que se encontram numa dada pasta e suas filhas
		
		System.out.println("Extensão de todos os ficheiros dentro da pasta Tiago, incluindo em pastas filhas");
		path = "root/Tiago";
		doc_id = database_analisis.getDirectoryDocIdByPath(path, database_entrys);
		ArrayList<DatabaseEntry> children = database_analisis.getArrayOfChildren(doc_id, database_schema);
		HashMap <String, Integer> hm = database_analisis.getExtensions(children);
		
		for(HashMap.Entry<String, Integer> entry: hm.entrySet()){
			System.out.println("Extensão : " + entry.getKey() + " Quantidade: " + entry.getValue());
		}
	}
}