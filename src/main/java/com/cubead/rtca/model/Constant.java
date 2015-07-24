package com.cubead.rtca.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoao on 27/4/15.
 */
public class Constant {

    //外部调用controller后，返回的信息
    public static String STATUS = "status";//状态
    public static String STATUS_SUCCESS = "success";//成功
    public static String STATUS_FAILED = "failed";//失败
    public static String ERROR_MSG = "errorMsg";//失败原因
    public static String RESPONSE_DATA = "data";//失败原因

    public static final String CA_COMP_RECV = "receiver";
    public static final String CA_COMP_CTRL = "controller";
    public static final String CA_COMP_CLCT = "collector";
    public static final String CA_COMP_ANLT = "analyst";

    public static enum ComponentEnum {
        RECV(CA_COMP_RECV), CLCT(CA_COMP_CLCT), ANLT(CA_COMP_ANLT), CTRL(CA_COMP_CTRL);

        private String title;

        ComponentEnum(String name) {
            this.title = name;
        }

        public String getTitle() {
            return title;
        }

        public static ComponentEnum match(String title) {
            for (ComponentEnum item : values()) {
                if (item.getTitle().equals(title))
                    return item;
            }
            return null;
        }
    }

    public static enum CompStatusEnum {
        UNKNOWN, ONLINE, OFFLINE, FAILED
    }

    public static String PHOENIXDRIVER="org.apache.phoenix.jdbc.PhoenixDriver";
    public static String PHOENIXCONNECT="jdbc:phoenix:yarn001";
//    public static String PHOENIXCONNECT="jdbc:phoenix:NN1";


    public static final String CAUID = "CAUID";
    public static final String OWNCAUID = "OWN_UID";
    public static final String SESSIONID = "SESSION_ID";
    public static final String OWNSESSIONID = "OWN_SESSION_ID";
    public static final String JOINKEY = "JOIN_KEY";
    //  public static final String CASBTTYPE="CA_SBTTYPE";

    public static final String CA_CHL_MED="1";
    public static final String CA_CHL_MED_CAMP="2";
    public static final String CA_CHL_MED_CAMP_UNIT_KEY="3";
    public static final String CA_CHL_MED_AREA="4";
    public static final String CA_EMPTY_STR="-";


    public static final String LOG_ONLINE = "online";
    public static final String LOG_SWAP = "swap";
    public static final String LOG_OFFLINE = "offline";
    public static final String LOG_DOWNLOAD = "download";
    public static final String LOG_MERGE = "merge";

    public static final String PARAM_CA_REQUEST = "REQUEST";
    public static final String PARAM_CA_COOKIE = "COOKIE";
    public static final String PARAM_CA_REFERER = "REFERRE";
    public static final String PARAM_CA_TIMESTAMP = "TIMESTAMP";
    public static final String PARAM_CA_BROWSER = "BROWSER";
    public static final String PARAM_CA_BODY = "BODY";
    public static final String PARAM_CA_CREATIVE = "CA_CREATIVE";

    public static final String CA_FIELD_FTIME = "CA_FTIME";
    public static final String CA_FIELD_LTIME = "CA_LTIME";
    public static final String CA_FIELD_VTIME = "CA_VTIME";
    public static final String CA_FIELD_VISITOR = "CA_VISITOR";
    public static final String CA_FIELD_SESSION = "CA_SESSION";
    public static final String CA_FIELD_TENANTID = "CA_TENANTID";
    public static final String CA_FIELD_CASOURCE = "CA_SOURCE";
    public static final String CA_FIELD_CREATIVE = "CA_CREATIVE";
    public static final String CA_FIELD_MEDIUM = "CA_MEDIUM";
    public static final String CA_FIELD_CAMPAIGN = "CA_CAMPAIGN";
    public static final String CA_FIELD_GROUP = "CA_GROUP";
    public static final String CA_FIELD_KEYWORD = "CA_KEYWORD";
    public static final String CA_FIELD_SITE = "CA_SITE";
    public static final String CA_FIELD_AREA = "CA_AREA";
    public static final String CA_FIELD_POSITION = "CA_POSITION";


    public static final String LOG_FIELD_UID = "UID";
    public static final String LOG_FIELD_PTYPE = "PTYPE";
    public static final String LOG_FIELD_TENANT = "TENANT";
    public static final String LOG_FIELD_VIEWSITE = "VIEWSITE";
    public static final String LOG_FIELD_TIMERANGE = "TIMERANGE";
    public static final String LOG_FIELD_TIMESTAMP = "TIMESTAMP";
    public static final String LOG_FIELD_CLIENTIP = "CLIENTIP";
    public static final String LOG_FIELD_TERMINAL = "TERMINAL";
    public static final String LOG_FIELD_FROMPAGE = "FROMPAGE";
    public static final String LOG_FIELD_TOPAGE = "TOPAGE";
    public static final String LOG_FIELD_ACTION = "ACTION";
    public static final String LOG_FIELD_TRANSACTION = "TRANS";
    public static final String LOG_FIELD_TRACECODE = "TRACECODE";
    public static final String LOG_FIELD_COOKIE = "COOKIE";
    public static final String LOG_FIELD_SEARCHURL = "SEARCHURL";
    public static final String LOG_FIELD_SEARCHKW = "SEARCHKW";
    public static final String LOG_FIELD_SEARCHPKW = "SEARCHPKW";
    public static final String LOG_FIELD_BATCHNUM = "BATCHNUM";
    public static final String LOG_FIELD_ACTIONTIME = "ACTIONTIME";


    public static final String  SOLR_OPT_ADD="add";
    public static final String  SOLR_OPT_QRY="query";
    public static final String  SOLR_CA_SPLIT_STR="-";
    public static final String  SOLR_CA_TASKTABLE="ca_solrtask";
    public static final String  CA_BLACKLIST="ca_blacklist";
    public static final Map<String, String> traceCodeMap = new HashMap<String, String>();
    static {
        traceCodeMap.put("utm_medium", Constant.CA_FIELD_MEDIUM);
        traceCodeMap.put("utm_source", Constant.CA_FIELD_CASOURCE);
        traceCodeMap.put("utm_campaign", Constant.CA_FIELD_CAMPAIGN);
        traceCodeMap.put("utm_content", Constant.CA_FIELD_GROUP);
        traceCodeMap.put("utm_term", Constant.CA_FIELD_KEYWORD);
        traceCodeMap.put("ca_medium", Constant.CA_FIELD_MEDIUM);
        traceCodeMap.put("ca_source", Constant.CA_FIELD_CASOURCE);
        traceCodeMap.put("ca_campaign", Constant.CA_FIELD_CAMPAIGN);
        traceCodeMap.put("ca_group", Constant.CA_FIELD_GROUP);
        traceCodeMap.put("ca_keyword", Constant.CA_FIELD_KEYWORD);
        traceCodeMap.put("ca_creative", Constant.CA_FIELD_CREATIVE);
        traceCodeMap.put("ca_creativeid", Constant.CA_FIELD_CREATIVE);
        traceCodeMap.put("ca_kwid", Constant.CA_FIELD_KEYWORD);
        //traceCodeMap.put("CA_MEDIUMID", Constant.CA_FIELD_MEDIUM);
        //traceCodeMap.put("CA_PID", Constant.CA_FIELD_CAMPAIGN);
        //traceCodeMap.put("CA_KID", Constant.CA_FIELD_KEYWORD);
        //traceCodeMap.put("CA_UID", Constant.CA_FIELD_GROUP);
        //traceCodeMap.put("CA_CID", Constant.CA_FIELD_CREATIVE);
    }




}

