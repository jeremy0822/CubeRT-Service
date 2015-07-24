package com.cubead.rtca.data

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{HConnectionManager, HConnection}

/**
 * Created by xiaoao on 5/8/15.
 */
object HBaseConnection{

  def getConnectionPool(config: Configuration): HConnection = {
    val connection = HConnectionManager.createConnection(config);
    return connection;
//    HTableInterface table = connection.getTable(TableName.valueOf("table1"));
//    try {
//      // Use the table as needed, for a single operation and a single thread
//    } finally {
//      table.close();
//      connection.close();
//    }
  }
}
