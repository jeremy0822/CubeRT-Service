package com.cubead.rtca.processer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.cubead.rtca.common.CommonFunc;
import com.cubead.rtca.common.DateUtil;
import com.cubead.rtca.common.IPSeeker;
import com.cubead.rtca.model.Constant;
import com.cubead.rtca.model.MergedDTO;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;
/**
 * Created by xiaoao on 5/7/15.
 */
public class CaLogImporter {
    private static final Logger log = Logger.getLogger(CaLogImporter.class);

    private static final String SPIDER_BAIDU1 = "Baiduspider";
    private static final String SPIDER_BAIDU2 = "spider-ads";

    public static class MapHadoop extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
        private ImmutableBytesWritable caPageview = new ImmutableBytesWritable(Bytes.toBytes("ca_pageview"));
        private ImmutableBytesWritable caConvertclk = new ImmutableBytesWritable(Bytes.toBytes("ca_convertclk"));
        private ImmutableBytesWritable caConversion = new ImmutableBytesWritable(Bytes.toBytes("ca_convert"));//JOIN_KEY
        private ImmutableBytesWritable caPageviewOver = new ImmutableBytesWritable(Bytes.toBytes("ca_pageviewover"));//
        private HashMap balckList=null;



        private byte[] f=Bytes.toBytes("f");//column family
        private byte[] ActionTime=Bytes.toBytes("ActionTime");         //访问时间
        private byte[] TimeRange=Bytes.toBytes("TimeRange");
        private byte[] UID=Bytes.toBytes("UID");                       //访客ID,第三方cookie
        private byte[] OwnUID=Bytes.toBytes("OwnUID");                       //访客ID,客户网站cookie
        private byte[] TenantID=Bytes.toBytes("TenantID");
        private byte[] terminal=Bytes.toBytes("terminal");
        private byte[] IP=Bytes.toBytes("IP");
        private byte[] Area=Bytes.toBytes("Area");
        private byte[] Source=Bytes.toBytes("Source");                 //来源
        private byte[] CaSource=Bytes.toBytes("CaSource");                 //来源
        private byte[] UtmSource=Bytes.toBytes("UtmSource");           //UTM来源
        private byte[] PreviousPage=Bytes.toBytes("PreviousPage");     //上一页
        private byte[] CurrentPage=Bytes.toBytes("CurrentPage");       //当前页
        private byte[] PageTitle=Bytes.toBytes("PageTitle");
        private byte[] Medium=Bytes.toBytes("Medium");
        private byte[] Creative=Bytes.toBytes("Creative");
        private byte[] Campaign=Bytes.toBytes("Campaign");
        private byte[] CampaignId=Bytes.toBytes("CampaignId");
        private byte[] GroupSet=Bytes.toBytes("GroupSet");
        private byte[] Keyword=Bytes.toBytes("Keyword");               //关键字
        private byte[] SearchKW=Bytes.toBytes("SearchKW");             //搜索字
        private byte[] SearchPKW=Bytes.toBytes("SearchPKW");
        private byte[] PageType=Bytes.toBytes("PageType");
        private byte[] Position=Bytes.toBytes("Position");
        private byte[] Action=Bytes.toBytes("Action");
        private byte[] IsConvert=Bytes.toBytes("IsConvert");
        private byte[] FTime=Bytes.toBytes("FTime");
        private byte[] LTime=Bytes.toBytes("LTime");
        private byte[] VTime=Bytes.toBytes("VTime");
        private byte[] STime=Bytes.toBytes("STime");
        private byte[] NewSession=Bytes.toBytes("NewSession");
        private byte[] NewVisitor=Bytes.toBytes("NewVisitor");
        private byte[] TransData=Bytes.toBytes("TransData");
        private byte[] LogTime=Bytes.toBytes("LogTime");               //记录日志的时间
        private byte[] Url=Bytes.toBytes("Url");                       //用户访问的URL
        private byte[] CaKid=Bytes.toBytes("CaKid");                   //keywordID
        private byte[] CaCv=Bytes.toBytes("CaCv");                     //创意ID
        private byte[] CaMdt=Bytes.toBytes("CaMdt");                   //点击来源的标识符，来源为凤巢标识为1，来源为网盟标识为2
        private byte[] CaPl=Bytes.toBytes("CaPl");                     //来源的具体网站domain
        private byte[] CaMt=Bytes.toBytes("CaMt");                     //该创意的关键词匹配方式，精确匹配标识为1，高级短语匹配标识为2，广泛匹配标识为3。
        private byte[] CaPn=Bytes.toBytes("CaPn");                     //搜索结果中的整体排名情况。例如：{adposition}=cr1且{pagenum}=2
        private byte[] CaDt=Bytes.toBytes("CaDt");                     //创意是否为“动态创意”，展现为动态创意的标识为1，未展现动态创意的标识为0。
        private byte[] CaAdp=Bytes.toBytes("CaAdp");                   //识为cl：pc左侧无底色，clg：pc 左侧有底色，cr：pc右侧，mt：无线上方，mb：无线下方，排名用具体数字标识。例如：cl3代表该点击来自pc左侧无底色广告位第3名。
        private byte[] CaAbt=Bytes.toBytes("CaAbt");                   //所点击物料是否参与“方案实验”以及在“方案实验”中的状态，标识为空代表未参与“方案实验”，标识为0代表已参与“方案实验”且为对照组物料，标识为1代表已参与“方案实验”且为实验组物料
        private byte[] CaMedium = Bytes.toBytes("CaMedium");           //百度竞价为1、品专2、网盟3
        private byte[] UtmCreative = Bytes.toBytes("UtmCreative");
        private byte[] UtmGroupset = Bytes.toBytes("UtmGroupset");
        private byte[] UtmKeyword = Bytes.toBytes("UtmKeyword");
        private byte[] SessionID=Bytes.toBytes("SessionID");           //SessionID，基于第三方cookie的sessionid
        private byte[] OwnSessionID=Bytes.toBytes("OwnSessionID");     //OwnSessionID基于客户网站的sessonid
        private byte[] JoinKey=Bytes.toBytes("JoinKey");                //joinKey基于客户网站的joinKey
        private byte[] CaSbtType=Bytes.toBytes("CaSbtType");            //caSbtType  1  表示点击转化  2 有成功页面的转化
        private byte[] KeywordID=Bytes.toBytes("KeywordID");
        private byte[] SearchCL = Bytes.toBytes("SearchCL");
        private byte[] SearchPN = Bytes.toBytes("SearchPN");
        private byte[] BaiduTN = Bytes.toBytes("BaiduTN");
        private byte[] TerminaType = Bytes.toBytes("TerminaType");


        private IPSeeker ipSeeker;
        Map<String,String> umap;
        final String speList[]={"内蒙古","广西","新疆","宁夏","西藏"};

        @Override
        protected void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException {
            MergedDTO dto = new MergedDTO();
            String line = value.toString();
            String[] data = line.split("\\]\\[");
            String blacklistKey1 ="";
            String blacklistKey2 ="";
            if (data[0].contains("CARAWDATA")) {
                GenerateDTO(data,dto);
            }
            if(null != dto.getTenantID() && !"".equals(dto.getTenantID())) {
                StringBuilder keyBuilder=new StringBuilder();
                keyBuilder.append(dto.getTenantID()).append("-").append(dto.getTimestamp());

                byte[] row=Bytes.toBytes(keyBuilder.toString());
                blacklistKey1 = dto.getTenantID()+"-"+dto.getClientIP();
                blacklistKey2 = dto.getTenantID()+"-"+CommonFunc.getIpOfStr(dto.getUrl());
                if(dto.getIsSpider().equals("1") || balckList.containsKey(blacklistKey1) || balckList.containsKey(blacklistKey2) ) {
                    Put outPut=new Put(row);
                    write(context, caPageviewOver, outPut, dto);
                } else {
                    if(dto.getAction().contains("PAGE:EVENT")) {
                        Put clkput=new Put(row);
                        write(context, caConvertclk, clkput, dto);
                    } else if  (dto.getAction().contains("PAGE:CONVERT")){
                        Put convertput = new Put(row);
                        write(context, caConversion,convertput, dto);
                    }else {
                        Put put=new Put(row);
                        write(context, caPageview, put, dto);
                    }
                }
            }
        }


        private void write(Context context, ImmutableBytesWritable table, Put put,MergedDTO dto) throws IOException, InterruptedException {
            String reg="(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
            String area = "";
            put.add(f, ActionTime, Bytes.toBytes(dto.getTimestamp()));
            put.add(f,TimeRange,Bytes.toBytes(dto.getTimestamp()));
            put.add(f,UID,Bytes.toBytes(dto.getUID()));
            put.add(f,OwnUID,Bytes.toBytes(dto.getOwnUID()));
            put.add(f,TenantID,Bytes.toBytes(dto.getTenantID()));
            put.add(f,terminal,Bytes.toBytes(dto.getTerminal()));
            put.add(f,IP,Bytes.toBytes(dto.getClientIP()));
            if (dto.getClientIP().matches(reg)){
                area = ipSeeker.getCountry(dto.getClientIP());
                log.info(dto.getClientIP());
                log.info(area);
            }
            put.add(f,Area,Bytes.toBytes(area));
            put.add(f,Source,Bytes.toBytes(dto.getSource()));
            put.add(f,PreviousPage,Bytes.toBytes(dto.getFromPage()));
            put.add(f,CurrentPage,Bytes.toBytes(dto.getToPage()));
            put.add(f,PageTitle,Bytes.toBytes(""));//未分析
            put.add(f,Medium,Bytes.toBytes(dto.getMedium()));//cpc or 空
            put.add(f,Creative,Bytes.toBytes(dto.getCreativeId()));//暂时没用
            put.add(f,Campaign,Bytes.toBytes(dto.getCampaign()));
            put.add(f,CampaignId,Bytes.toBytes(dto.getCampaignId()));
            put.add(f,GroupSet,Bytes.toBytes(dto.getGroupSet()));
            put.add(f,Keyword,Bytes.toBytes(dto.getKeyword()));
            put.add(f,SearchKW,Bytes.toBytes(dto.getSearchKW()));
            put.add(f,SearchPKW,Bytes.toBytes(dto.getSearchPKW()));
            put.add(f,PageType,Bytes.toBytes(dto.getPageType()));
            put.add(f,CaSbtType,Bytes.toBytes(dto.getCaSbtType()));
            put.add(f,Position,Bytes.toBytes(dto.getPosition()));//
            put.add(f,Action,Bytes.toBytes(dto.getAction()));
            if(dto.getAction().contains("PAGE:EVENT") && dto.getAction().contains("_crt_") ) {//点击转化
                dto.setIsCovert("1");
                put.add(f,IsConvert,Bytes.toBytes(dto.getIsCovert()));
            }
            put.add(f,FTime,Bytes.toBytes(dto.getfTime()));
            put.add(f,LTime,Bytes.toBytes(dto.getlTime()));
            put.add(f,VTime,Bytes.toBytes(dto.getvTime()));
            put.add(f,STime,Bytes.toBytes(dto.getsTime()));
            put.add(f,NewSession,Bytes.toBytes(dto.getNewSession()));//
            put.add(f,NewVisitor,Bytes.toBytes(dto.getNewVisitor()));//
            put.add(f,TransData,Bytes.toBytes(dto.getTransData()));
            put.add(f,LogTime,Bytes.toBytes(dto.getLogTime()));
            put.add(f,Url,Bytes.toBytes(dto.getUrl()));//url
            put.add(f,UtmSource,Bytes.toBytes(dto.getUtmSource()));
            put.add(f,CaSource,Bytes.toBytes(dto.getCaSource()));
            put.add(f,SearchCL,Bytes.toBytes(dto.getSearchCl()));
            put.add(f,SearchPN,Bytes.toBytes(dto.getSearchPn()));
            put.add(f,BaiduTN,Bytes.toBytes(dto.getBaiduTn()));
            put.add(f,TerminaType,Bytes.toBytes(dto.getTerminaType()));

            put.add(f,CaSource,Bytes.toBytes(dto.getCaSource()));
            if (dto.getUtmSource().equalsIgnoreCase("baidu")){
                log.info("currentURL:"+dto.getToPage());
                log.info("tenantid:"+dto.getTenantID());
                log.info("timestamp:"+dto.getTimestamp());
                log.info("ip:"+dto.getClientIP());
            }

            put.add(f,CaKid,Bytes.toBytes(dto.getCaKid()));
            put.add(f,KeywordID,Bytes.toBytes(dto.getKeywordID()));
            put.add(f,CaCv,Bytes.toBytes(dto.getCaCv()));
            put.add(f,CaMdt,Bytes.toBytes(dto.getCaMdt()));
            put.add(f,CaPl,Bytes.toBytes(dto.getCaPl()));
            put.add(f,CaMt,Bytes.toBytes(dto.getCaMt()));
            put.add(f,CaPn,Bytes.toBytes(dto.getCaPn()));
            put.add(f,CaDt,Bytes.toBytes(dto.getCaDt()));
            put.add(f,CaAdp,Bytes.toBytes(dto.getCaAdp()));
            put.add(f,CaAbt,Bytes.toBytes(dto.getCaAbt()));
            put.add(f,CaMedium,Bytes.toBytes(dto.getCaMedium()));
            put.add(f,UtmCreative,Bytes.toBytes(dto.getUtmCreative()));
            put.add(f,UtmGroupset,Bytes.toBytes(dto.getGroupSet()));
            put.add(f,UtmKeyword,Bytes.toBytes(dto.getKeyword()));
            put.add(f,SessionID,Bytes.toBytes(dto.getSessionID()));
            put.add(f,OwnSessionID,Bytes.toBytes(dto.getOwnSessionID()));
            put.add(f,JoinKey,Bytes.toBytes(dto.getJoinKey()));
            context.write(table, put);
        }
        @Override
        protected void setup(Context context) throws IOException,InterruptedException {
            ipSeeker = new IPSeeker("src/pack/qqwry.dat");
            balckList = CommonFunc.getBlackList();
            log.info(ipSeeker);

            super.setup(context);
        }

        @Override
        protected void cleanup(Context context) throws IOException,
                InterruptedException {
            super.cleanup(context);
        }
    }
    public static void GenerateDTO(String[] data, MergedDTO dto) {
        data[data.length - 1] = data[data.length - 1].replaceAll("[\\]]","");
        for (String item : data) {
            int pos = item.indexOf(":");
            if (pos < 0)
                continue;

            String cakey = item.substring(0, pos);
            String cavalue = item.substring(pos + 1).trim();

            if (cakey.toUpperCase().indexOf(Constant.PARAM_CA_TIMESTAMP) >= 0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String logTime = DateUtil.convertDate(Long.parseLong(cavalue), sdf);
                dto.setTimestamp(cavalue);
                dto.setLogTime(logTime);
            }
            else if (cakey.toUpperCase().indexOf(Constant.PARAM_CA_BROWSER) >= 0)
                parseBrowser(cavalue, dto);
            else if (cakey.toUpperCase().indexOf(Constant.PARAM_CA_REFERER) >= 0)
                parseReferer(cavalue, dto);
            else if (cakey.toUpperCase().indexOf(Constant.PARAM_CA_REQUEST) >= 0)
                parseRequest(cavalue, dto);
            else if (cakey.toUpperCase().indexOf(Constant.PARAM_CA_COOKIE) >= 0)
                parseCACookie(cavalue, dto);
            else if (cakey.toUpperCase().indexOf(Constant.PARAM_CA_BODY) >= 0)
                parseBody(cavalue, dto);
        }
    }

    protected static void parseBrowser(String data, MergedDTO dto) {

        if(data.contains(SPIDER_BAIDU1)|| data.contains(SPIDER_BAIDU2)) {
            dto.setIsSpider("1");
        }

        int pos = data.indexOf(" ");
        if (pos <= 0)
            dto.setClientIP(data);
        else
            dto.setClientIP(data.substring(0, pos));
        try{
            String browser = data.substring(data.indexOf("-")+1);
            dto.setTerminal(browser);
            String terminal = browser.toLowerCase();
            if(terminal.indexOf("windows") != -1 || (terminal.indexOf("mac") != -1 && terminal.indexOf("ipad") == -1 && terminal.indexOf("iphone") == -1) || (terminal.indexOf("linux") != -1 && terminal.indexOf("android") == -1)){
                dto.setTerminaType("1");
            }else{
                dto.setTerminaType("2");
            }
        }catch(Exception E){
            log.error("Browser:"+data);
        }

    }

    /**
     * the current page URL in client browser that send trace request to CA
     *
     * @param data
     * @param dto
     */
    protected static void parseReferer(String data, MergedDTO dto) {
        dto.setUrl(data);
        int pos = data.indexOf("?");
        if (pos < 0)
            dto.setToPage(data);
        else {
            dto.setToPage(data.substring(0, pos));
            parseURI(data.substring(pos),dto);
        }


    }

    /**
     * parse trace parameter, generated by CA JS
     *
     * @param data
     * @param dto
     */
    protected static void parseRequest(String data, MergedDTO dto) {
        int pos = data.indexOf("?");
        if (pos < 0)
            dto.setFromPage("");
        else {
            String vtime = "";
            String[] params = data.substring(pos + 1).split("&");
            for (String param : params) {
                String value = param.substring(param.indexOf("=") + 1);
                if (param.toUpperCase().indexOf("CA_PTYPE=") >= 0)
                    dto.setPageType(value);
                else if (param.toUpperCase().indexOf("CA_TENANT=") >= 0)
                    dto.setTenantID(value);
                else if (param.toUpperCase().indexOf("CA_SBTTYPE=") >= 0)
                    dto.setCaSbtType(value);
                else if (param.toUpperCase().indexOf("CA_VTIME=") >= 0) {
                    vtime = param;
                    String[] session = value.split("-");
                    if(null !=session){
                        if(session.length ==1 ){
                            dto.setvTime(session[0]);
                        }
                        else if(session.length ==2 ){
                            dto.setvTime(session[0]);
                            dto.setNewVisitor(session[1]);
                        }
                        else{
                            dto.setvTime(session[0]);
                            dto.setNewVisitor(session[1]);
                            dto.setNewSession(session[2]);
                        }
                    }
                }
                else if (param.toUpperCase().indexOf("CA_PREREF=") >= 0){
                    value = CommonFunc.unescape(value);
                    dto.setFromPage(value);
                    if (value.indexOf("?")>=0){
                        parseURI(value.substring(value.indexOf("?")),dto);
                    }
                } else if (param.toUpperCase().indexOf("CA_ACTION=") >= 0){
                    String actions[] = value.split("-");
                    if (actions.length>1){
                        String position = actions[0] + "-" + actions[1];
                        dto.setPosition(position);
                    }
                    dto.setAction(CommonFunc.unescape(value));
                } else if (param.toUpperCase().indexOf("CA_COOKIE=") >= 0){
                    parseSiteCookie(CommonFunc.unescape(value), dto);
                }

            }
            dto.setTraceCode(dto.getTraceCode() + "&" + vtime);
        }
    }

    protected static void parseTraceURI(String data, MergedDTO dto) {
        String[] params = data.split("&");
        String vtime = "";
        for (String param : params) {
            String value = param.substring(param.indexOf("=") + 1);
            if (param.toUpperCase().indexOf("UTM_CAMPAIGNID=") >= 0){
                dto.setCampaignId(CommonFunc.unescape(value));
            }else if (param.toUpperCase().indexOf("UTM_CAMPAIGN=") >= 0){
                dto.setCampaign(CommonFunc.tryDecode(value, new StringBuilder(
                        CommonFunc.CS_UTF8)));
            }else if(param.toUpperCase().indexOf("UTM_CREATIVE=") >= 0){
                dto.setUtmCreative(CommonFunc.tryDecode(value, new StringBuilder(
                        CommonFunc.CS_UTF8)));
            }else if(param.toUpperCase().indexOf("UTM_GROUPSET=") >= 0){
                dto.setUtmGroupset(CommonFunc.tryDecode(value, new StringBuilder(
                        CommonFunc.CS_UTF8)));
            }else if(param.toUpperCase().indexOf("UTM_KEYWORD=") >= 0){
                dto.setUtmKeyword(CommonFunc.tryDecode(value, new StringBuilder(
                        CommonFunc.CS_UTF8)));
            }else if (param.toUpperCase().indexOf("UTM_TERM=") >= 0){
                dto.setKeyword(CommonFunc.tryDecode(value, new StringBuilder(
                        CommonFunc.CS_UTF8)));
            }else if (param.toUpperCase().indexOf("UTM_CONTENT=") >= 0){
                dto.setGroupSet(CommonFunc.tryDecode(value, new StringBuilder(
                        CommonFunc.CS_UTF8)));
            }else if (param.toUpperCase().indexOf("UTM_MEDIUM=") >= 0){
                if (value.indexOf("%")>=0){
                    try {
                        value = URLDecoder.decode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                dto.setMedium(value);
            } else if (param.toUpperCase().indexOf("CA_CREATIVEID=") >= 0){
                dto.setCreativeId(value);
            } else if (param.toUpperCase().indexOf("UTM_SOURCE=") >= 0) {
                dto.setUtmSource(value);
            } else if (param.toUpperCase().indexOf("CA_SOURCE=") >= 0) {
                dto.setCaSource(value);
            } else if (param.toUpperCase().indexOf("CA_KID=") >= 0) {
                dto.setCaKid(value);
            } else if (param.toUpperCase().indexOf("CA_KEYWORDID") >= 0){
                dto.setKeywordID(value);
            } else if (param.toUpperCase().indexOf("CA_CV=") >= 0) {
                dto.setCaCv(value);
            } else if (param.toUpperCase().indexOf("CA_MDT=") >= 0) {
                dto.setCaMdt(value);
            } else if (param.toUpperCase().indexOf("CA_PL=") >= 0) {
                dto.setCaPl(value);
            } else if (param.toUpperCase().indexOf("CA_MT=") >= 0) {
                dto.setCaMt(value);
            } else if (param.toUpperCase().indexOf("CA_PN=") >= 0) {
                dto.setCaPn(value);
            } else if (param.toUpperCase().indexOf("CA_DT=") >= 0) {
                dto.setCaDt(value);
            } else if (param.toUpperCase().indexOf("CA_ADP=") >= 0) {
                dto.setCaAdp(value);
            } else if (param.toUpperCase().indexOf("CA_ABT=") >= 0) {
                dto.setCaAbt(value);
            } else if(param.toUpperCase().indexOf("CA_MEDIUM") >= 0){
                dto.setCaMedium(value);
            }
        }
        dto.setTraceCode(dto.getTraceCode() + "&" + vtime);
    }
    /**
     * parse trace parameter, generated by CA JS
     *
     * @param data
     * @param dto
     */
    protected static void parseURI(String data, MergedDTO dto) {
        try{
            int pos = data.indexOf("?");
            if (pos >= 0){
                String vtime = "";
                String[] params = data.substring(pos + 1).split("&");
                for (String param : params) {
                    String value = param.substring(param.indexOf("=") + 1);
                    if (param.toUpperCase().indexOf("UTM_CAMPAIGNID=")>=0){
                        dto.setCampaignId(CommonFunc.unescape(value));
                    } else if (param.toUpperCase().indexOf("UTM_CAMPAIGN=")>=0){
                        dto.setCampaign(CommonFunc.tryDecode(value, new StringBuilder(CommonFunc.CS_UTF8)));
                    } else if (param.toUpperCase().indexOf("UTM_TERM=")>=0){
                        dto.setKeyword(CommonFunc.tryDecode(value, new StringBuilder(CommonFunc.CS_UTF8)));
                    } else if (param.toUpperCase().indexOf("UTM_CONTENT=")>=0){
                        dto.setGroupSet(CommonFunc.tryDecode(value, new StringBuilder(CommonFunc.CS_UTF8)));
                    } else if (param.toUpperCase().indexOf("UTM_MEDIUM=")>=0){
                        if (value.indexOf("%")>=0){
                            value = URLDecoder.decode(value, "UTF-8");
                        }
                        dto.setMedium(value);
                    } else if(param.toUpperCase().indexOf("UTM_CREATIVE=") >= 0){
                        dto.setUtmCreative(CommonFunc.tryDecode(value, new StringBuilder(
                                CommonFunc.CS_UTF8)));
                    } else if(param.toUpperCase().indexOf("UTM_GROUPSET=") >= 0){
                        dto.setUtmGroupset(CommonFunc.tryDecode(value, new StringBuilder(
                                CommonFunc.CS_UTF8)));
                    } else if(param.toUpperCase().indexOf("UTM_KEYWORD=") >= 0){
                        dto.setUtmKeyword(CommonFunc.tryDecode(value, new StringBuilder(
                                CommonFunc.CS_UTF8)));
                    } else if (param.toUpperCase().indexOf("UTM_SOURCE=") >= 0) {
                        dto.setUtmSource(value);
                    } else if (param.toUpperCase().indexOf("CA_SOURCE=") >= 0) {
                        dto.setCaSource(value);
                    } else if (param.toUpperCase().indexOf("CA_CREATIVEID=")>=0){
                        dto.setCreativeId(value);
                    } else if (param.toUpperCase().indexOf("CA_KEYWORDID=") >= 0){
                        dto.setKeywordID(value);
                    } else if (param.toUpperCase().indexOf("CA_KID=") >= 0) {
                        dto.setCaKid(value);
                    } else if(param.toUpperCase().indexOf("CA_CV=") >= 0) {
                        dto.setCaCv(value);
                    } else if(param.toUpperCase().indexOf("CA_MDT=") >= 0) {
                        dto.setCaMdt(value);
                    } else if(param.toUpperCase().indexOf("CA_PL=") >= 0) {
                        dto.setCaPl(value);
                    } else if(param.toUpperCase().indexOf("CA_MT=") >= 0) {
                        dto.setCaMt(value);
                    } else if(param.toUpperCase().indexOf("CA_PN=") >= 0) {
                        dto.setCaPn(value);
                    } else if(param.toUpperCase().indexOf("CA_DT=") >= 0) {
                        dto.setCaDt(value);
                    } else if(param.toUpperCase().indexOf("CA_ADP=") >= 0) {
                        dto.setCaAdp(value);
                    } else if(param.toUpperCase().indexOf("CA_ABT=") >= 0) {
                        dto.setCaAbt(value);
                    } else if(param.toUpperCase().indexOf("CA_MEDIUM") >= 0){
                        dto.setCaMedium(value);
                    }
                    setSearch( dto, param, value);
                }
                dto.setTraceCode(dto.getTraceCode() + "&" + vtime);
            }
        }catch(Exception e){
            log.error("error URLDecoder decode data :"+data, e);
        }
    }
    private static void setSearch(MergedDTO dto,String param,String value){
        if (param.toUpperCase().startsWith("WD=") || param.toUpperCase().startsWith("WORD=")|| param.toUpperCase().startsWith("Q=")|| param.toUpperCase().startsWith("QUERY=")){
            dto.setSearchKW(CommonFunc.tryDecode(value, new StringBuilder(CommonFunc.CS_UTF8)));
        }else if (param.toUpperCase().startsWith("BS=") || param.toUpperCase().startsWith("PQ=") || param.toUpperCase().startsWith("OQ=")|| param.toUpperCase().startsWith("SUG=")){
            dto.setSearchPKW(CommonFunc.tryDecode(value, new StringBuilder(CommonFunc.CS_UTF8)));
        }else if(param.toUpperCase().startsWith("CL=")){
            dto.setSearchCl(CommonFunc.tryDecode(value, new StringBuilder(CommonFunc.CS_UTF8)));
        }else if(param.toUpperCase().startsWith("TN=")){
            dto.setBaiduTn(CommonFunc.tryDecode(value, new StringBuilder(CommonFunc.CS_UTF8)));
        }else if(param.toUpperCase().startsWith("PN=")){
            dto.setSearchPn(CommonFunc.tryDecode(value, new StringBuilder(CommonFunc.CS_UTF8)));
        }
    }
    protected static void parseSiteCookie(String data, MergedDTO dto) {
        String[] cookies = data.split(";");

        String field = null;
        StringBuilder buff = new StringBuilder();
        StringBuilder others = new StringBuilder();
        Map<String, String> cubes = new HashMap<String, String>();

        for (String cookie : cookies) {
            buff.setLength(0);
            String[] item = cookie.trim().split("=");
            if (item.length != 2){
                continue;
            } else if (item[0].toUpperCase().equals("CA_TRACE")) {
                String trace = "";
                try {
                    //trace = URLDecoder.decode(URLDecoder.decode(URLDecoder.decode(item[1], "utf-8"), "utf-8"), "utf-8");
                    trace = CommonFunc.unescape(item[1]);
                } catch (Exception e) {
                    trace = CommonFunc.unescape(item[1]);
                    log.error("CA_TRACE:"+item[1],e);
                }
                String[] codes = trace.split("[\\&]");
                for (String code : codes) {
                    String[] kv = code.trim().split("=");
                    if (kv.length != 2)
                        buff.append(code).append("&");
                    else {
                        field = Constant.traceCodeMap.get(kv[0]
                                .toLowerCase());
                        if (field == null)
                            buff.append(code).append("&");
                        else
                            buff.append(field).append("=").append(kv[1])
                                    .append("&");
                    }
                }

                dto.setTraceCode(buff.toString());
                parseTraceURI(trace,dto);
            } else if (item[0].toUpperCase().equals("CA_SE")){
                String searchUrl="";
                try {
                    searchUrl = CommonFunc.unescape(item[1]);
                } catch (Exception eee) {
                    searchUrl = CommonFunc.unescape(item[1]);
                    log.error(item[1], eee);
                }
                dto.setSearchURL(searchUrl);
                parseURI(searchUrl,dto);
            } else if (item[0].toUpperCase().startsWith("CA_")) {
                if(item[0].toUpperCase().startsWith("CA_FTIME")){
                    dto.setfTime(item[1]);
                } else if (item[0].toUpperCase().startsWith("CA_LTIME")){
                    dto.setlTime(item[1]);
                } else if (item[0].toUpperCase().startsWith("CA_STIME")){
                    dto.setsTime(item[1]);
                }
                field = Constant.traceCodeMap.get(item[0].toLowerCase());
                if (field == null)
                    cubes.put(item[0], item[1]);
                else
                    cubes.put(field, item[1]);
            } else {
                others.append(cookie).append(";");
            }
        }

        if (dto.getTraceCode() == null || dto.getTraceCode().isEmpty()) {
            buff.setLength(0);
            for (Map.Entry<String, String> entry : cubes.entrySet()) {
                buff.append(entry.getKey()).append("=")
                        .append(entry.getValue()).append("&");
            }
            dto.setTraceCode(buff.toString());
        } else {
            String traceCode = dto.getTraceCode().toUpperCase();
            buff.setLength(0);
            for (Map.Entry<String, String> entry : cubes.entrySet()) {
                if (traceCode.indexOf(entry.getValue().toUpperCase()) < 0)
                    buff.append(entry.getKey()).append("=")
                            .append(entry.getValue()).append("&");
            }
            dto.setTraceCode(buff.toString() + dto.getTraceCode());
        }

        dto.setCookie(others.toString());
    }

    protected static void parseCACookie(String data, MergedDTO dto) {
        String[] cookies = data.split(";");

        for (String cookie : cookies) {
            String[] item = cookie.split("=");
            if (item.length != 2)
                continue;

            if (item[0].toUpperCase().equals(Constant.CAUID))
                dto.setUID(item[1]);
            if (item[0].toUpperCase().equals(Constant.OWNCAUID))
                dto.setOwnUID(item[1]);
            if(item[0].toUpperCase().equals(Constant.SESSIONID)) {
                dto.setSessionID(item[1]);
            }
            if(item[0].toUpperCase().equals(Constant.OWNSESSIONID)) {
                dto.setOwnSessionID(item[1]);
            }
            if(item[0].toUpperCase().equals(Constant.JOINKEY)) {
                dto.setJoinKey(item[1]);
            }
        }
    }

    protected static void parseBody(String data, MergedDTO dto) {
        String tmp = data.trim();
        if (tmp.length() > 0 && tmp.indexOf("ca_transcontent=") >= 0) {
            tmp = tmp.substring(tmp.indexOf("=") + 1);
            dto.setTransInfo(CommonFunc.unescape(CommonFunc.unescape(tmp)));
        }
    }

//    public static void main(String[] args) throws IOException,
//            InterruptedException, ClassNotFoundException {
//        Configuration conf = HBaseConfiguration.create();
//        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
//        for (String arg : otherArgs){
//            log.info(arg);
//        }
//
//        if (otherArgs.length < 1) {
//            System.err.println("Only " + otherArgs.length
//                    + " arguments supplied, required: 1");
//            System.err.println("Usage: CaImporter <INPUT_FILE>");
//            System.exit(-1);
//        }
//
//        //conf.set(TableOutputFormat.OUTPUT_TABLE, "ca_pageview");
//
//        Job job = new Job(conf, "CaLogImporter");
//        job.setJarByClass(CaLogImporter.class);
//        job.setMapperClass(MapHadoop.class);
//        job.setNumReduceTasks(0);
//        job.setInputFormatClass(TextInputFormat.class);
//        job.setOutputFormatClass(MultiTableOutputFormat.class);
//
//        FileInputFormat.setInputPaths(job, new Path(otherArgs[0]));
//
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
//    }
}


