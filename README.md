# rtca
handling web activity data

一。所需环境，jvm1.7+，kafka 0.8.2.1。
在启动程序前先启动zookeeper和kafka。
其中kafka的启动和创建topic如下：
cd 到kafka 目录下
1.启动kafka
./bin/kafka-server-start.sh -daemon config/server.properties
2.创建topic
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic topic-rtca
3.验证是否创建成功或是否存有数据(可选)
./bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic topic-rtca --from-beginning

二。程序分两部分：
1。ca部分，web工程，可以放在tomcat等容器下运行；
主要配置项说明：
kafka.zookeepers=192.168.3.102:2181 //zookeeper地址，根据实际安装进行配置
kafka.brokers=192.168.3.102:9092    //kafka地址，根据实际安装进行配置
kafka.groupId=grtca                 //kafka group名称，不用动
kafka.topic=topic-rtca              //kafka topic名称，不用动
zookeeper.connection.timeout.ms=10000 // zookeeper超时时间，可以不用动
打包方式maven，命令 mvn clean install package -DskipTests

2.rtca，命令行项目，启动方式是./bin/start 192.168.3.102:2181 hbase001 10
参数说明：
192.168.3.102:2181 为zookeeper地址，和ca中的配置保持一致；
hbase001 hbase地址，具体需要配置hbase的那些个文件；
10 为多久进行一次数据收集(多久生成一个RDD)
其中第二项配置，根据实际的hbase安装，以及配置的host文件进行设定
打包方式sbt,命令 sbt clean compile pack,为target下的pack文件夹

三。测试方式，建议对线上进行数据引流来进行测试；
四。测试重点：
程序功能：
1.程序功能正常，无明显bug；
2.数据完整性，和原系统中的数据一致；
3.系统异常时，能从异常点开始进行数据接收，不会丢失数据；

程序性能：
并发情况：经测，每秒支持1100多条数据入hbase
提供以下场景的压力曲线图
1.和线上同数据来源时 
  1.程序的性能变化曲线，吞吐量，响应时间，网络流量，CPU，内存，磁盘等基本数据曲线图；
  2.jvm内存变化曲线；
2.2X,3X,5X,10X线上压力时的上述指标；


