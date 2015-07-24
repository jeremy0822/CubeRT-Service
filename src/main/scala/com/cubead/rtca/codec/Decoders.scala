package com.cubead.rtca.codec

import com.cubead.rtca.model.RequestData
import com.cubead.rtca.models.ImpressionLog
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.novus.salat
import com.novus.salat.global._
import kafka.serializer.{Decoder, Encoder}
import kafka.utils.VerifiableProperties
import org.apache.commons.io.Charsets

/**
 * Created by xiaoao on 30/3/15.
 */

// encode and decode logs in JSON (in this tuto for readability purpose) but it would be better to consider something like AVRO or protobuf)
class ImpressionLogDecoder(props: VerifiableProperties) extends Decoder[ImpressionLog] {
  def fromBytes(bytes: Array[Byte]): ImpressionLog = {
    salat.grater[ImpressionLog].fromJSON(new String(bytes, Charsets.UTF_8))
  }
}

class ImpressionLogEncoder(props: VerifiableProperties) extends Encoder[ImpressionLog] {
  def toBytes(impressionLog: ImpressionLog): Array[Byte] = {
    salat.grater[ImpressionLog].toCompactJSON(impressionLog).getBytes(Charsets.UTF_8)
  }
}

class JsonEncoder(verifiableProperties: VerifiableProperties) extends Encoder[RequestData] {

  def toBytes(requestData: RequestData): Array[Byte] = {
    val objectMapper = new ObjectMapper()  with ScalaObjectMapper
    objectMapper.registerModule(DefaultScalaModule)
    val coder = objectMapper.writeValueAsString(requestData)
    println(s"coder: ${coder.toString()}")
    coder.getBytes()
  }
}

class JsonDecoder(verifiableProperties: VerifiableProperties)  extends Decoder[RequestData] {
  def fromBytes(bytes: Array[Byte]): RequestData = {
    val objectMapper = new ObjectMapper() with ScalaObjectMapper
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.readValue(bytes, classOf[RequestData])
  }
}