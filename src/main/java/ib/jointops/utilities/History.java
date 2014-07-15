package ib.jointops.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ibm.icu.util.StringTokenizer;

public class History {
	List<String> historyOfHosts = new ArrayList<String>();
	List<String> historyOfDBs = new ArrayList<String>();
	List<String> historyOfUsers = new ArrayList<String>();
	List<String> historyFull = new ArrayList<String>();
	
	public History() {
		try {
			readFile();
		} catch (IOException e) {
			historyOfHosts = null;
			historyOfDBs = null;
			historyOfUsers = null;
		}
	}
	
	private void readFile() throws IOException {
		File file = new File("history");
		if (checkFile())
		{
			BufferedReader bR = new BufferedReader(new FileReader(file));
			String line;

			while((line = bR.readLine()) != null)
			{
				historyFull.add(line);
				StringTokenizer token = new StringTokenizer(line, "\t");
				historyOfHosts.add(0, token.nextToken());
				historyOfDBs.add(0, token.nextToken());
				historyOfUsers.add(0, token.nextToken());
			}
			bR.close();
			
		}	
	}
	public void addToFile(String host, String db, String user) throws IOException {
		File file = new File("history");
		String separator = System.getProperties().getProperty("line.separator");
		
		String line1 = host + "\t" + db + "\t" + user + separator;
		historyOfDBs.add(db);
		historyOfHosts.add(host);
		historyOfUsers.add(user);
		
		List<String> historyOfHosts2 = new ArrayList<>(new LinkedHashSet<>(historyOfHosts));
		List<String> historyOfDBs2 = new ArrayList<>(new LinkedHashSet<>(historyOfDBs));
		List<String> historyOfUsers2 = new ArrayList<>(new LinkedHashSet<>(historyOfUsers));
		
		BufferedWriter bW = new BufferedWriter(new FileWriter(file, false));
		String[] dbsAr = (String[]) historyOfDBs2.toArray(new String[0]);
		String[] usrAr = (String[]) historyOfUsers2.toArray(new String[0]);
		String[] hstAr = (String[]) historyOfHosts2.toArray(new String[0]);
		
		int to = Math.min(Math.min(dbsAr.length, usrAr.length), hstAr.length);
		
		//int to = (hstAr.length >= 4) ? 4 : hstAr.length; 
		for (int i = 0; i < to; i++) {
			String line = hstAr[i] + "\t" + dbsAr[i] + "\t" + usrAr[i] + separator;
			bW.write(line);
		}
		bW.close();
	}
	public List<String> getHistoryOfHosts() {
		return this.historyOfHosts;
	}
	public List<String> getHistoryOfUsers() {
		return this.historyOfUsers;
	}
	public List<String> getHistoryOfDBs() {
		return this.historyOfDBs;
	}
	
	private boolean checkFile() {
		File file = new File("history");
		return file.exists() && file.canRead();
	}
}
