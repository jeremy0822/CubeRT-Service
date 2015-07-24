package com.cubead.rtca.common;

/**
 * Created by xiaoao on 5/7/15.
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseTool {

    private static Log logger = LogFactory.getLog(HbaseTool.class);

    private static HConnection pool = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public synchronized static HConnection getHConnection() throws IOException{
        if(pool==null){
            Configuration config = HBaseConfiguration.create();
            //config.setInt("hbase.client.retries.number", 3);
            pool=HConnectionManager.createConnection(config);
        }
        return pool;
    }

    /**
     * @param config
     * @throws IOException
     */
    public static void initHConnection(Configuration config){
        try {
            pool = HConnectionManager.createConnection(config);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    /**
     * @param tableName
     */
    public static void createTable(String tableName) {
        HBaseAdmin hBaseAdmin=null;
        try {
            Configuration configuration=HBaseConfiguration.create();
            hBaseAdmin = new HBaseAdmin(configuration);
            if (hBaseAdmin.tableExists(tableName)) {
                hBaseAdmin.disableTable(tableName);
                hBaseAdmin.deleteTable(tableName);
                logger.info(tableName + " is exist,detele....");
            }
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            HColumnDescriptor family = new HColumnDescriptor(Bytes.toBytes("f"));
            family.setCompressionType(Algorithm.SNAPPY);
            tableDescriptor.addFamily(family);
            hBaseAdmin.createTable(tableDescriptor);

            logger.info("create "+tableName+" success!!");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally{
            if(hBaseAdmin!=null)
                try {
                    hBaseAdmin.close();
                } catch (IOException e) {
                    //do nothing
                }
        }
    }
    public static List<String> listTableNames(){
        HBaseAdmin hBaseAdmin=null;
        try {
            Configuration configuration=HBaseConfiguration.create();
            hBaseAdmin = new HBaseAdmin(configuration);
            TableName[] tableNames=hBaseAdmin.listTableNames();

            List<String> names=new ArrayList<String>();

            for (TableName tableName : tableNames) {
                names.add(tableName.getNameAsString());
            }

            return names;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally{
            if(hBaseAdmin!=null)
                try {
                    hBaseAdmin.close();
                } catch (IOException e) {
                    //do nothing
                }
        }
    }
    public static HTableInterface getTable(String tableName){
        try {
            return getHConnection().getTable(tableName);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public static HTableInterface getTable(byte[] tableName){
        try {
            return getHConnection().getTable(tableName);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
    public static HTableTemplate getHTableTemplate(String tableName){
        HTableTemplate template=new HTableTemplate();
        template.setHTable(tableName);

        return template;
    }

//    public static void main(String[] args) {
//        HTableTemplate template=getHTableTemplate("");
//        Result res=template.get(new Get(Bytes.toBytes("1003_creativematch_2013-04-15_264836455_1122")));
//        System.out.println(res.isEmpty());
//    }

}
