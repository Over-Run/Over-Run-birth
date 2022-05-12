package org.overrun.utls;

import java.io.*;
import java.net.URL;

public class Url {
	public static final String url = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
	public static final String user_dir = System.getProperty("user.dir");
	public static final char separatorChar = File.separatorChar;
	public static void download(String url,String name) throws IOException {
		var connection = new URL(url).openConnection();
		BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new FileWriter(name));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			bw.write(line + "\n");
			sb.append(line);
		}
		reader.close();
	}


	public static void DVersion_manifest() throws IOException {
		String split = url.split("/")[url.split("/").length - 1];
		String fileName = user_dir + separatorChar + "minecraft";
		String name = fileName + separatorChar + split;
		if (!new File(fileName).exists()) {
			new File(fileName).mkdirs();
		}
		download(url, name);
	}
}
