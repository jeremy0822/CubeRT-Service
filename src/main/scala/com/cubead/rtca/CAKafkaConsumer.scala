package com.cubead.rtca

import kafka.consumer.Consumer
import kafka.consumer.ConsumerConfig
import kafka.message._
import kafka.serializer._
import kafka.utils._
import java.util.Properties
import kafka.utils.Logging
import scala.collection.JavaConversions._
import kafka.consumer.KafkaStream
import java.util.concurrent._
import com.cubead.rtca.common.LogParser
import kafka.api.PartitionOffsetRequestInfo
import kafka.common.TopicAndPartition
import kafka.consumer.ConsumerTimeoutException
import kafka.consumer.ConsumerTimeoutException
import java.text.SimpleDateFormat
import java.util.Date
/**
 * @author jeremy.huang
 */
class CAKafkaConsumer(val zookeeper: String,
                      val groupId: String,
                      val topic: String) extends Logging {

  val config = createConsumerConfig(zookeeper, groupId)
  val consumer = Consumer.create(config)

  var executor: ExecutorService = null

  def createConsumerConfig(zookeeper: String, groupId: String): ConsumerConfig = {
    val props = new Properties()
    props.put("zookeeper.connect", zookeeper);
    props.put("group.id", groupId);
    props.put("auto.offset.reset", "largest");
    props.put("zookeeper.session.timeout.ms", "30000");
    props.put("zookeeper.sync.time.ms", "2000");
    props.put("auto.commit.interval.ms", "1000");
    props.put("consumer.timeout.ms", "30000");
    val config = new ConsumerConfig(props)
    config
  }

  def run(numThreads: Int) = {
    executor = Executors.newFixedThreadPool(numThreads);
    val topicCountMap = Map(topic -> numThreads);
    val consumerMap = consumer.createMessageStreams(topicCountMap);
    val streams = consumerMap.get(topic).get;
    var threadNumber = 0;
    for (stream <- streams) {
      executor.submit(new ConsumerHandler(stream, threadNumber))
      threadNumber += 1
    }
  }
}

class ConsumerHandler(val stream: KafkaStream[Array[Byte], Array[Byte]], val threadNumber: Int) extends Logging with Runnable {
  val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  def run {
    var startTime: Long = SystemTime.milliseconds
    var pool = LogParser.getMapPool()
    while (true) {
      try {
        val it = stream.iterator()
        while (it.hasNext()) {
          val msg = it.next();
          val record = new String(msg.message());
          var ret = LogParser.parseLog(record, pool);
          if (msg.offset % 1000 == 0) {
        	  println(s"${format.format(new Date())}[Thread ${threadNumber}] save log:${ret}, currentOffset:${msg.offset + 1}");
          }

          if ((SystemTime.milliseconds-startTime) > 60*1000) {
            LogParser.flushAndCommit(pool)
            startTime = SystemTime.milliseconds
            println(s"${format.format(new Date())}[Thread ${threadNumber}] flush and commit data to hbase per 60s.")
          }
        }
      } catch {
        case ex: ConsumerTimeoutException => {
          LogParser.flushAndCommit(pool)
          println(s"${format.format(new Date())}[Thread ${threadNumber}] Consumer have no data for timeout 30s and will reset iterator")
        }
      }
    }
  }
}