package org.overrun.utls;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.overrun.utls.ReadJson.readS;
import static org.overrun.utls.ReadJson.readSs;

public class Url {
	public static final String url = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
	public static final String user_dir = System.getProperty("user.dir");
	public static final char separatorChar = File.separatorChar;
	public static void download(String url,String name) throws IOException {
		var connection = new URL(url).openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new FileWriter(name));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			bw.write(line + "\n");
			sb.append(line);
		}
		reader.close();
		bw.close();

	}


	public static void DVersion_manifest() throws IOException {
		String split = url.split("/")[url.split("/").length - 1];
		String fileName = user_dir + separatorChar + "minecraft";
		String name = fileName + separatorChar + split;
		if (!new File(fileName).exists()) {
			new File(fileName).mkdirs();
		}
		if (!new File(name).exists()) {
			download(url, name);
		}
		readS(name);
		Map<String, String> version_manifest = getVersion_manifest_mcVersion(name);
		Thread thread, thread1;
		Runnable runnable, runnable1;
		runnable = () -> {
			for (var i : version_manifest.keySet()) {
				if (i.contains("1.")) {
					System.out.println(i + " " + version_manifest.get(i));
					var versionFileName = user_dir + separatorChar + "minecraft"+ separatorChar + "versions" + separatorChar + i.replace(" ", "");
					if (!new File(versionFileName).exists()) {
						new File(versionFileName).mkdirs();
					}
					try {
						var dw = versionFileName + separatorChar + i.replace(" ", "_") + ".json";
						if (new File(dw).exists()) {
							continue;
						}
						download(version_manifest.get(i), dw);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			for (var i : version_manifest.keySet()) {
				var versionFileName = user_dir + separatorChar + "minecraft"+ separatorChar + "versions" + separatorChar + i.replace(" ", "");
				if (!new File(versionFileName).exists()) {
					new File(versionFileName).mkdirs();
				}
				if (i.contains("1.")) {
					var dw = versionFileName + separatorChar + i.replace(" ", "_") + ".json";
					try {
						readSs(dw);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread = new Thread(runnable, "1.x.x");
		thread.start();
		runnable1 = () -> {
			for (var i : version_manifest.keySet()) {
				if (i.contains("w")) {
					System.out.println(i + " " + version_manifest.get(i));
					var versionFileName = user_dir + separatorChar + "minecraft"+ separatorChar + "versions" + separatorChar + i.replace(" ", "");
					if (!new File(versionFileName).exists()) {
						new File(versionFileName).mkdirs();
					}
					try {
						var dw = versionFileName + separatorChar + i.replace(" ", "_") + ".json";
						if (new File(dw).exists()) {
							continue;
						}
						download(version_manifest.get(i), dw);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			for (var i : version_manifest.keySet()) {
				var versionFileName = user_dir + separatorChar + "minecraft"+ separatorChar + "versions" + separatorChar + i.replace(" ", "");
				if (!new File(versionFileName).exists()) {
					new File(versionFileName).mkdirs();
				}
				if (i.contains("w")) {
					var dw = versionFileName + separatorChar + i.replace(" ", "_") + ".json";
					try {
						readSs(dw);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread1 = new Thread(runnable1, "snapshot");
		thread1.start();

		/*ReadJson.formatter(name);*/
	}

	public static Map<String, String> getVersion_manifest_mcVersion(String name) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(name + ".birth"));
		BufferedWriter bw = new BufferedWriter(new FileWriter(name + ".copy.birth"));
		String line;
		Map<String, String> version_manifest = new HashMap<>();
		String version = null, url = null;
		while ((line = reader.readLine()) != null) {
			if (line.contains("id")) {
				version = line.split(":")[1].replace("\"", "").replace(",", "");
			}
			if (line.contains("url")) {
				url = line.split(":")[1].replace("\"", "").replace(",", "") + ":" + line.split(":")[2].replace("\"", "").replace(",", "");
			}
			if (version != null && url != null) {
				version_manifest.put(version, url);
				version = null;
				url = null;
			}
		}
		reader.close();
		return version_manifest;
	}
}
