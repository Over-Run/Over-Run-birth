package org.overrun.utls;

import java.io.*;

public class ReadJson {
	public static void readJsons(String name) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(name));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(name).getPath() + ".birth"));
		var line = br.lines();
		StringBuilder sb = new StringBuilder();
		line.forEach(sb::append);
		bw.write(sb.toString());
		bw.close();
		br.close();
		/*while ((line = br.readLine()) != null) {
			System.out.println("\n" +line);
		}*/
	}

	public static void readS(String name) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(name));
		BufferedWriter bw = new BufferedWriter(new FileWriter(name + ".birth"));
		var line = br.lines();
		StringBuilder sb = new StringBuilder();
		line.forEach(sb::append);
		bw.write(sb.toString().replace("{", "").replace("}", "\n").replace("[", "\n").replace("\"id\":", "\"id\":").replace("\"type\":", "\n\"type\":").replace("\"url\":", "\n\"url\":").replace("\"time\":", "\n\"time\":"));
		bw.close();
		br.close();

	}
	public static void readSs(String name) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(name));
		BufferedWriter bw = new BufferedWriter(new FileWriter(name + ".birth"));
		var line = br.lines();
		StringBuilder sb = new StringBuilder();
		line.forEach(sb::append);
		bw.write(sb.toString().replace("{", "").replace("}", "\n").replace("[", "\n").replace("]", "").replace(",", "\n").replace("\"", ""));
		bw.close();
		br.close();

	}
}
