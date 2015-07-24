import _root_.sbt.Keys._

name := "rtca"

version := "1.0.0.11"

scalaVersion := "2.10.4"

val sparkVersion = "1.3.1"

val hbaseVersion = "0.98.8-hadoop2"

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "0.8.2.1",
  "org.apache.spark" %% "spark-yarn" % sparkVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion
    exclude("org.apache.zookeeper", "zookeeper")
    exclude("org.slf4j", "slf4j-api")
    exclude("org.slf4j", "slf4j-log4j12")
    exclude("org.slf4j", "jul-to-slf4j")
    exclude("org.slf4j", "jcl-over-slf4j"),
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka" % sparkVersion
    exclude("org.apache.zookeeper", "zookeeper"),
  "com.101tec" % "zkclient" % "0.4"
    exclude("org.apache.zookeeper", "zookeeper"),
  "org.apache.curator" % "curator-test" % "2.4.0"
    exclude("org.jboss.netty", "netty")
    exclude("org.slf4j", "slf4j-log4j12"),
  "commons-io" % "commons-io" % "2.4",
  "com.novus" %% "salat" % "1.9.9",
  "org.apache.commons" % "commons-pool2" % "2.2",
  "org.apache.hbase" % "hbase-client" % hbaseVersion,
  "org.apache.hbase" % "hbase-common" % hbaseVersion,
  "org.apache.hbase" % "hbase-hadoop-compat" % hbaseVersion,
  "org.apache.hbase" % "hbase-it" % hbaseVersion,
  "org.apache.hbase" % "hbase-hadoop2-compat" % hbaseVersion,
  "org.apache.hbase" % "hbase-prefix-tree" % hbaseVersion,
  "org.apache.hbase" % "hbase-protocol" % hbaseVersion,
  "org.apache.hbase" % "hbase-server" % hbaseVersion excludeAll ExclusionRule(organization = "org.mortbay.jetty"),
  "org.apache.hbase" % "hbase-shell" % hbaseVersion,
  "org.apache.hbase" % "hbase-testing-util" % hbaseVersion,
  "org.apache.hbase" % "hbase-thrift" % hbaseVersion,
  "org.apache.hadoop" % "hadoop-client" % "2.2.0",
  "org.apache.hadoop" % "hadoop-common" % "2.2.0"  excludeAll ExclusionRule(organization = "javax.servlet"),
  "eu.unicredit" %% "hbase-rdd" % "0.4.2",
  "it.nerdammer.bigdata" % "spark-hbase-connector_2.10" % "0.9.4",
  "com.github.nscala-time" %% "nscala-time" % "1.8.0",
  // Logback with slf4j facade
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  // Test dependencies
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.mockito" % "mockito-all" % "1.9.5" % "test"
)

javacOptions ++= Seq("-encoding", "utf-8")

resolvers += "sbt-pack repo" at " http://repo1.maven.org/maven2/org/xerial/sbt/"

packSettings

packMain := Map(
  "start" -> "com.cubead.rtca.RTAnalyst"
)
