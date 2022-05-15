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
	public static final String split = url.split("/")[url.split("/").length - 1];
	public static final String minecraft = user_dir + separatorChar + "minecraft";
	public static final String version = minecraft + separatorChar + "versions";
	public static final String name = minecraft + separatorChar + split;
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

	public static Runnable basic_write(Map<String, String> version_manifest, String contains) {
		return () -> {
			for (var i : version_manifest.keySet()) {
				if (i.contains(contains)) {
					System.out.println(i + " " + version_manifest.get(i));
					var versionFileName = version + separatorChar + i.replace(" ", "");
					if (!new File(versionFileName).exists()) {
						new File(versionFileName).mkdirs();
					}
					try {
						var dw = versionFileName + separatorChar + i.replace(" ", "") + ".json";
						if (new File(dw).exists()) {
							readSs(dw);
							continue;
						}
						download(version_manifest.get(i), dw);
						readSs(dw);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}


	public static void DVersion_manifest() throws IOException {
		if (!new File(minecraft).exists()) {
			new File(minecraft).mkdirs();
		}
		if (!new File(name).exists()) {
			download(url, name);
		}
		readS(name);
		Map<String, String> version_manifest = getVersion_manifest_mcVersion(name);
		//将来选择多线程取代
		Thread thread, thread1, thread2, thread3,thread4;
		thread = new Thread(basic_write(version_manifest, "1."), "release");
		thread.start();
		thread1 = new Thread(basic_write(version_manifest, "w"), "snapshot");
		thread1.start();
		thread2 = new Thread(basic_write(version_manifest, "c0"), "old");
		thread2.start();
		thread3 = new Thread(basic_write(version_manifest, "inf"),"infinite");
		thread3.start();
		thread4 = new Thread(basic_write(version_manifest, "rd"), "release_dev");
		thread4.start();
	}

	public static Map<String, String> getVersion_manifest_mcVersion(String name) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(name + ".birth"));
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
