#!/bin/sh

aws configure set AWS_ACCESS_KEY_ID AKIAIWZIG6QJ6GMJTHFQ
aws configure set AWS_SECRET_ACCESS_KEY 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
aws configure set default.region us-east-1

#initialize all aws config variables
applications="Name=Hadoop Name=Hive Name=Spark Name=Hue"
emrManagedMasterSecurityGroup=sg-0e19f142a45f310b4
keyName=GRABKeyPair
subnetId=subnet-09f821b8d5e7e8d14
emrManagedSlaveSecurityGroup=sg-03cad5ebff3e119f7
instanceProfile=EMR_EC2_DefaultRole
servicerole=EMR_DefaultRole
releaselabel=emr-5.5.3
loguri=s3n://grab-extract-data/elasticmapreduce/
name=GRAB_Batch_Cluster
masterInstanceType=m3.xlarge
masterInstanceCount=1
coreInstanceType=m3.xlarge
coreInstanceCount=2
region=us-east-1

#Create EMR cluster,Add spark submit step,terminate cluster
cid=$(aws emr create-cluster --applications $applications --ec2-attributes EmrManagedMasterSecurityGroup="$emrManagedMasterSecurityGroup",KeyName="$keyName",SubnetId="$subnetId",EmrManagedSlaveSecurityGroup="$emrManagedSlaveSecurityGroup",InstanceProfile="$instanceProfile" --service-role "$servicerole" --enable-debugging --release-label "$releaselabel" --log-uri "$loguri" --steps Args=["spark-submit","--deploy-mode","cluster","--num-executors","10","--executor-cores","5","--executor-memory","4g","--driver-memory","1g","--class","grabusecase.driver.DemandSupplyRatioCalDriver","--jars","s3://grab-extract-data/Jars/GrabUseCaseUp-1.0-SNAPSHOT-jar-with-dependencies.jar","s3://grab-extract-data/Jars/GrabUseCaseUp-1.0-SNAPSHOT.jar"],Type="CUSTOM_JAR",ActionOnFailure="CONTINUE",Jar="command-runner.jar",Properties="",Name="SparkSubmit-BatchProcess" --name "$name" --instance-groups  InstanceGroupType=MASTER,InstanceType=$masterInstanceType,InstanceCount=$masterInstanceCount InstanceGroupType=CORE,InstanceType=$coreInstanceType,InstanceCount=$coreInstanceCount --auto-terminate --region $region --output text)

echo "EMR clusterId>>>>>>>>"$cid

aws emr add-steps --cluster-id $cid --steps Name="Spark application",Jar="command-runner.jar",Args=["spark-submit","--deploy-mode","cluster","--num-executors","10","--executor-cores","5","--executor-memory","4g","--driver-memory","1g","--class","grabusecase.driver.CongestionEstimatorDriver","--jars","s3://grab-extract-data/Jars/GrabUseCaseUp-1.0-SNAPSHOT-jar-with-dependencies.jar","s3://grab-extract-data/Jars/GrabUseCaseUp-1.0-SNAPSHOT.jar"]