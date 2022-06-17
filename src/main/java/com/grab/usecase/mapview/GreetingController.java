package com.grab.usecase.mapview;

/**
 * Created by bhagat on 3/23/19.
 */

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GreetingController {

    @CrossOrigin()
    @RequestMapping("/trafficCongestion")
    @ResponseBody
    public  List<TrafficCongestionDto> getTrafficCongestion() throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials("AKIAIWZIG6QJ6GMJTHFQ","232OnBeoFWMTWsYRRuZ4mLQRDB+gq78Gj2S3/NSc");

        AmazonS3 s3client = new AmazonS3Client(credentials);
        s3client.setRegion(Region.getRegion(Regions.US_WEST_2));

        String bucketName = "grab-extract-data";

        String dataKey = "TrafficCongestionEstimatorMapview/part-00000-bb16cc18-7a03-4f95-b53a-29a5f8500b88.csv";

        GetObjectRequest request = new GetObjectRequest(bucketName, dataKey);

        S3Object s3Object = s3client.getObject(request);

        BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
        List<TrafficCongestionDto> output = new ArrayList<>();
        String line = null;
        int count =0;
        while((line = reader.readLine()) != null){
            if(count == 0){
                count++;
                continue;
            }
            String[] values = line.split(",");

            output.add(new TrafficCongestionDto(values[0], values[1], values[2], values[3], values[6]));
        }
        return output;

    }

    @CrossOrigin()
    @RequestMapping("/demandSupply")
    @ResponseBody
    public  List<DemandSupplyDto> getDemandSupplyRatio() throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials("AKIAIWZIG6QJ6GMJTHFQ","232OnBeoFWMTWsYRRuZ4mLQRDB+gq78Gj2S3/NSc");

        AmazonS3 s3client = new AmazonS3Client(credentials);
        s3client.setRegion(Region.getRegion(Regions.US_WEST_2));

        String bucketName = "grab-extract-data";

        String dataKey = "DemandSupplyCalMapview/part-00000-0a143f45-2779-4957-9cff-84d3f1f84c28.csv";

        GetObjectRequest request = new GetObjectRequest(bucketName, dataKey);

        S3Object s3Object = s3client.getObject(request);

        BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
        List<DemandSupplyDto> output = new ArrayList<>();
        String line = null;
        int count =0;
        while((line = reader.readLine()) != null){
            if(count == 0){
                count++;
                continue;
            }
            String[] values = line.split(",");

            output.add(new DemandSupplyDto(values[0], values[1], values[2], values[3], values[4]));
        }
        return output;

    }
}
