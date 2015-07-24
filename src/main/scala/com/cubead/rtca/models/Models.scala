package com.cubead.rtca.models

import org.joda.time.DateTime

/**
 * Created by xiaoao on 30/3/15.
 */
case class ImpressionLog(timestamp: String, url: String)

//timestamp: Long,hour: String,time: String,method: String,resource: String,query: String,domain: String,browser: String,referre: String,clientIP: String,cookie: String,contentType: String,ownSessionid: String,ownUid: String
//
//// result to be stored in HBase
//case class AggregationResult(date: DateTime, publisher: String, geo: String, imps: Int, uniques: Int, avgBids: Double)
//
//case class PublisherGeoKey(publisher: String, geo: String)
