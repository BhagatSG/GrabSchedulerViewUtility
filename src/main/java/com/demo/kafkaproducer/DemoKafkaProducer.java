package com.demo.kafkaproducer;

import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class DemoKafkaProducer {
	
	public static void main(String[] args) {
		Properties props = new Properties();
		//props.put("metadata.broker.list", "ec2-3-93-80-120.compute-1.amazonaws.com:9092");
		props.put("bootstrap.servers", "ec2-3-93-80-120.compute-1.amazonaws.com:9092");

		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		props.put("request.required.acks", "1");
		props.put("group.id","test-consumer-group");
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
        //props.put("enable.auto.commit",true);

        System.out.println("Inside Main Method & Before Kafka Producer Call");

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        System.out.println("Inside Main Method & after Kafka Producer Call");

        int sum=0;
		while(true){
			System.out.println("Starting Producing msg: "+sum);
			for(int i = 0; i < 50; i++)
			{	
				sum=sum+1;
				System.out.println(sum);
                String congEstm="{\"VendorID\":\"2\",\"lpep_pickup_datetime\":\"2015-10-01 00:38:23\",\"Lpep_dropoff_datetime\":\"2015-10-01 00:56:03\",\"Store_and_fwd_flag\":\"N\",\"RateCodeID\":\"1\",\"Pickup_longitude\":\"-73.93778992\",\"Pickup_latitude\":\"40.74872589\",\"Dropoff_longitude\":\"-73.91238403\",\"Dropoff_latitude\":\"40.77383804\",\"Passenger_count\":\"5\",\"Trip_distance\":\"2.29\",\"Fare_amount\":\"12.5\",\"Extra\":\"1\",\"MTA_tax\":\"0.5\",\"Tip_amount\":\"2.86\",\"Tolls_amount\":\"Tolls_amount\",\"Ehail_fee\":\"\",\"improvement_surcharge\":\"0.3\",\"Total_amount\":\"17.16\",\"Payment_type\":\"2\"}";
                producer.send(new ProducerRecord<String, String>("congestionEstimator", congEstm));
                System.out.println("Data Produced & Send on Topic: congestionEstimator");


                String custDetails="{\"cust_id\":\"cust1\",\"cust_latitude\":\"28.56717291\",\"cust_longitude\":\"77.14202536\",\"cust_datetime\":\"2018-01-01 04:09:00\"}";
                producer.send(new ProducerRecord<String, String>("customerData", custDetails));
                System.out.println("Data Produced & Send on Topic: customerData");

                String cabDetails="{\"cab_id\":\"cab1\",\"cab_latitude\":\"-73.93778992\",\"cab_longitude\":\"40.14202536\",\"cab_datetime\":\"2015-10-01 00:56:03\"}";
                producer.send(new ProducerRecord<String, String>("driverData", cabDetails));
                System.out.println("Data Produced & Send on Topic: driverData");
			}
			try {
				System.out.println("Produced : "+ sum+ " messages!");
				System.out.println("Sleeping for 9 minutes!!!");
				TimeUnit.MINUTES.sleep(9);
			} catch (InterruptedException e) {
				producer.close();
				e.printStackTrace();
			}
		}
		
	}
}

