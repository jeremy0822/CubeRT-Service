package com.cubead.rtca.common;

/**
 * Created by xiaoao on 5/7/15.
 */

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

public class HTableTemplate{
    private String tableName;

    public void setHTable(String tableName){
        this.tableName=tableName;
    }

    public void delete(List<Delete> dels){
        HTableInterface table=null;
        try {
            table=HbaseTool.getTable(tableName);

            table.delete(dels);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        finally{
            if(table!=null)
                try {
                    table.close();
                } catch (IOException e) {
                    //ignore
                }
        }
    }



    public void delete(Delete del){
        HTableInterface table=null;
        try {
            table=HbaseTool.getTable(tableName);

            table.delete(del);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        finally{
            if(table!=null)
                try {
                    table.close();
                } catch (IOException e) {
                    //ignore
                }
        }
    }

    public void batch(List<Row> actions){
        HTableInterface table=null;
        try {
            table=HbaseTool.getTable(tableName);

            table.batch(actions);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        finally{
            if(table!=null)
                try {
                    table.close();
                } catch (IOException e) {
                    //ignore
                }
        }
    }

    public void put(Put put){
        HTableInterface table=null;
        try {
            table=HbaseTool.getTable(tableName);

            table.put(put);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        finally{
            if(table!=null)
                try {
                    table.close();
                } catch (IOException e) {
                    //ignore
                }
        }
    }

    public void put(List<Put> puts){
        HTableInterface table=null;
        try {
            table=HbaseTool.getTable(tableName);

            table.put(puts);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        finally{
            if(table!=null)
                try {
                    table.close();
                } catch (IOException e) {
                    //ignore
                }
        }
    }

    public Result get(Get get){
        HTableInterface table=null;
        try {
            table=HbaseTool.getTable(tableName);
            return table.get(get);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        finally{
            if(table!=null)
                try {
                    table.close();
                } catch (IOException e) {
                    //ignore
                }
        }
    }



}

