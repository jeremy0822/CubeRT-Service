package com.cubead.rtca.models

/**
 * Created by xiaoao on 31/3/15.
 */
object Constants {
  val NumPublishers = 5
  val NumAdvertisers = 3

  val Publishers = (0 to NumPublishers).map("publisher_" +)
  val Advertisers = (0 to NumAdvertisers).map("advertiser_" +)
  val UnknownGeo = "unknown"
  val Geos = Seq("NY", "CA", "FL", "MI", "HI", UnknownGeo)
  val NumWebsites = 10000
  val NumCookies = 10000
  
  val HBASE_FAMILY = "f"
  val hBASE_FLUSH_SIZE = 1000
  val hBASE_Buffer_SIZE = 1000 + 200

  val KafkaTopic = "topic-rtca"
  val kafkaGroup = "grtca"

  val SPIDER_BAIDU1 = "Baiduspider"
  val sPIDER_BAIDU2 = "spider-ads"

  val TB_ca_pageview = "rtca_pageview"
  val TB_ca_convertclk = "rtca_convertclk"
  val TB_ca_convert = "rtca_convert"
  val TB_ca_pageviewover = "rtca_pageviewover"
}
