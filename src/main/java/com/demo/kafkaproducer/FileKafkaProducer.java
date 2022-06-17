package com.demo.kafkaproducer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class FileKafkaProducer {
	public static void main(String[] args) {
		String directory=args[0];
		String topicName=args[1];
		Properties props = new Properties();
		//props.put("metadata.broker.list", "ec2-3-93-80-120.compute-1.amazonaws.com:9092");
		props.put("bootstrap.servers", "ec2-3-93-80-120.compute-1.amazonaws.com:9092");

		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("serializer.class", "kafka.serializer.StringEncoder");

		props.put("request.required.acks", "1");
		props.put("group.id","test-consumer-group");
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		//props.put("enable.auto.commit",true);

        System.out.println("Inside Main Method & Before Kafka Producer Call");


		ArrayList<String> list = new ArrayList<>();

		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				list.add(listOfFiles[i].getAbsolutePath());
			} 
		}
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);		
		try {
			for (String fileName : list){
			    System.out.println("fileName "+fileName);
				String msg=readFile(fileName, StandardCharsets.UTF_8);
				producer.send(new ProducerRecord<String, String>(topicName, msg));
			}
			producer.close();
			System.out.println("Produced Messages!!!!!!!!");

		} catch (Exception e) {
			producer.close();
			e.printStackTrace();
			System.out.println("Error Producing Messages!!!!!!!!");
		}

	}



	static String readFile(String path, Charset encoding) 
			throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}




