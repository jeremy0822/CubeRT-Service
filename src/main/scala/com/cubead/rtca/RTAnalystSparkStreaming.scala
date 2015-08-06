package com.cubead.rtca

import com.cubead.rtca.codec.{ JsonDecoder, ImpressionLogDecoder }
import com.cubead.rtca.common.{ HbaseTool, LogParser }
import com.cubead.rtca.data.HBaseConnection
import com.cubead.rtca.model.RequestData
import com.cubead.rtca.models.{ Constants }
import org.apache.hadoop.hbase.{ TableName, KeyValue, HTableDescriptor, HBaseConfiguration }
import org.apache.hadoop.hbase.mapreduce.{ HFileOutputFormat, TableOutputFormat, TableInputFormat }
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.client.{ HTable, HBaseAdmin, Put }
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.io.Writable
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.rdd.{ PairRDDFunctions, RDD }
import org.apache.spark.storage.StorageLevel

import scala.Array
import scala.xml.{ Node, XML }
import org.apache.spark.streaming.{ Seconds, StreamingContext }
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.{ Logging, SparkConf, SparkContext }
import org.apache.spark.streaming.kafka.{ OffsetRange, KafkaUtils }

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import org.apache.hadoop.fs.{ Path, FileSystem }
import org.apache.hadoop.conf.Configuration
import java.io.{ InputStreamReader, BufferedReader }

import kafka.serializer.StringDecoder

/**
 * Created by xiaoao on 30/3/15.
 */
import unicredit.spark.hbase._
object RTAnalystSparkStreaming extends Logging {

  def main(args: Array[String]): Unit = {
    if (args.length < 3) {
      System.err.println("Usage: ./bin/start zookeeper.connect hbase.zookeeper.quorum timeDuration")
      System.err.println("  zookeeper.connect: the zookeeper host and port,example: 192.168.3.226:2181")
      System.err.println("  hbase.zookeeper.quorum: hbase host ,example: hbase001")
      System.err.println("  timeDuration: the time duration for get the ca log. unit(ms)")
      System.err.println("Example: ./bin/start 192.168.3.226:2181 hbase001 10")
      System.exit(1)
    }
    val tableName = "rtca"

    val Array(zookeeper, hbasehost, timeWindow) = args
    println("zookeeper.connect: ", zookeeper)
    println("hbase.zookeeper.quorum: ", hbasehost)
    println("timeDuration: ", timeWindow)

    val appName = "rtca"
    lazy val sparkContext = new SparkConf().setAppName(appName).setMaster("local[4]")
    //    sparkContext.setExecutorEnv("spark.streaming.receiver.writeAheadLog.enable", "true")
    val windowDuration = Seconds(timeWindow.toInt)
    lazy val streamingContext = new StreamingContext(sparkContext, windowDuration)
    streamingContext.checkpoint("checkpoints")

    val kafkaParams = scala.collection.immutable.Map(
      "zookeeper.connect" -> zookeeper,
      "zookeeper.connection.timeout.ms" -> "30000",
      "group.id" -> Constants.kafkaGroup)

    val topics = scala.collection.immutable.Map(Constants.KafkaTopic -> 2)
    val messages = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](streamingContext, kafkaParams, topics, StorageLevel.MEMORY_AND_DISK_SER)
    //    val info = messages.window(BatchDuration)
    val info = messages.window(windowDuration)

    val conf = HBaseConfiguration.create();
    conf.set("hbase.zookeeper.quorum", hbasehost);

    HbaseTool.initHConnection(conf)
    info.foreachRDD { rdd =>
      {
        rdd.foreachPartition { partition =>
          {
            val pool = LogParser.getMapPool()
            partition.foreach(record => LogParser.parseLog(record._2, pool))
            LogParser.flushAndCommit(pool)
          }
        }
      }
    }
    streamingContext.start()
  }
}
