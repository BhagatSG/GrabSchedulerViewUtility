package com.batch.grab.cronapp.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SparkSubmitBatchApp {

	public void invokeSparkSubmitBatchCmd()
			throws IOException {

		try {
			String[] command = { "sh", "/home/ec2-user/SparkSubmit-BatchCron.sh"};
					
			ProcessBuilder pb = new ProcessBuilder(command);

			System.out.println("===================Run spark submit Batch command====================");

			Process p = pb.start();
			String output = loadStream(p.getInputStream());
			String error = loadStream(p.getErrorStream());
			int rc = p.waitFor();
			System.out.println("Process ended with rc=" + rc);
			System.out.println("\nStandard Output:\n");
			System.out.println(output);
			System.out.println("\nStandard Error:\n");
			System.out.println(error);

		} catch (InterruptedException ex1) {
			System.out.println("Spark Submit process InterruptedException");
			ex1.printStackTrace();
		} catch (IOException ex2) {
			System.out.println("Spark Submit process IOException");
			ex2.printStackTrace();
		}

		System.out.println("Spark submit execution Done====>");
	}

	private static String loadStream(InputStream inputStream)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + System.getProperty("line.separator"));
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}
}
