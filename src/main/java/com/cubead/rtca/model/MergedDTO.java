package com.cubead.rtca.model;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoao on 27/4/15.
 */
public class MergedDTO {
    private String timestamp = "";//日志记录时间戳
    private String pageType = "N";
    private String recvAddr = "";
    private String uID = "";
    private String ownUID = "";
    private String tenantID = "";
    private String terminal = "";
    private String clientIP = "";
    private String fromPage = "";
    private String toPage = "";
    private String action = "";
    private String searchURL = "";
    private String traceCode = "";
    private String transInfo = "";
    private String cookie = "";
    private String source = "";
    private String utmSource = "";
    private String campaign = "";
    private String campaignId = "";
    private String medium = "";
    private String groupSet = "";
    private String keyword = "";
    private String creativeId = "";
    private String searchKW = "";
    private String SearchPKW = "";
    private String position = "";
    private String fTime = "";
    private String lTime = "";
    private String vTime = "";
    private String sTime = "";
    private String NewSession = "0";
    private String NewVisitor = "0";
    private String transData = "";
    private String logTime = "";//日志记录时间
    private String url = "";
    private String area ="";//地域
    private String caKid = "";//keywordID
    private String keywordID = "";
    private String caCv = "";//创意ID
    private String caMdt = "";//点击来源的标识符，来源为凤巢标识为1，来源为网盟标识为2
    private String caPl = "";//来源的具体网站domain
    private String caMt = "";//该创意的关键词匹配方式，精确匹配标识为1，高级短语匹配标识为2，广泛匹配标识为3。
    private String caPn = "";//搜索结果中的整体排名情况。例如：{adposition}=cr1且{pagenum}=2
    private String caDt = "";//创意是否为“动态创意”，展现为动态创意的标识为1，未展现动态创意的标识为0。
    private String caAdp = "";//识为cl：pc左侧无底色，clg：pc 左侧有底色，cr：pc右侧，mt：无线上方，mb：无线下方，排名用具体数字标识。例如：cl3代表该点击来自pc左侧无底色广告位第3名。
    private String caAbt = "";//所点击物料是否参与“方案实验”以及在“方案实验”中的状态，标识为空代表未参与“方案实验”，标识为0代表已参与“方案实验”且为对照组物料，标识为1代表已参与“方案实验”且为实验组物料
    private String caMedium = "";
    private String sessionID = "";
    private String ownSessionID = "";
    private String joinKey = ""; //joinKey
    private String caSbtType="";
    /**
     * @return the caSbtType
     */
    public String getCaSbtType() {
        return caSbtType;
    }

    /**
     * @param caSbtType the caSbtType to set
     */
    public void setCaSbtType(String caSbtType) {
        this.caSbtType = caSbtType;
    }
    private String caSource = "";//来源
    private String isSpider = "0";//是否来自百度爬虫
    private String searchPn = "";//搜索页码
    private String searchCl = "";//搜索类型
    private String baiduTn = "";//百度联盟合作地址
    private String terminaType = "";

    private String utmCreative = "";
    private String utmGroupset= "";
    private String utmKeyword = "";
    private String isCovert = "0";//是否是转化点击





    /**
     * @return the joinKey
     */
    public String getJoinKey() {
        return joinKey.toUpperCase();
    }

    /**
     * @param joinKey the joinKey to set
     */
    public void setJoinKey(String joinKey) {
        this.joinKey = joinKey.toUpperCase();
    }



    public String getIsCovert() {
        return isCovert;
    }

    public void setIsCovert(String isCovert) {
        this.isCovert = isCovert;
    }
    public static final Map<String, String> FIELD_NAME = new HashMap<String, String>();
    static {
        FIELD_NAME.put("C01", Constant.LOG_FIELD_TIMESTAMP);
        FIELD_NAME.put("C02", Constant.LOG_FIELD_UID);
        FIELD_NAME.put("C03", Constant.LOG_FIELD_TERMINAL);
        FIELD_NAME.put("C04", Constant.LOG_FIELD_CLIENTIP);
        FIELD_NAME.put("C05", Constant.LOG_FIELD_FROMPAGE);
        FIELD_NAME.put("C06", Constant.LOG_FIELD_TOPAGE);
        FIELD_NAME.put("C07", Constant.LOG_FIELD_COOKIE);
        FIELD_NAME.put("C08", Constant.LOG_FIELD_ACTION);
        FIELD_NAME.put("C09", Constant.LOG_FIELD_SEARCHURL);
        FIELD_NAME.put("C10", Constant.LOG_FIELD_TRACECODE);
        FIELD_NAME.put("C11", Constant.LOG_FIELD_TRANSACTION);
        FIELD_NAME.put("C12", Constant.LOG_FIELD_TENANT);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRecvAddr() {
        return recvAddr;
    }

    public void setRecvAddr(String recvAddr) {
        if (recvAddr == null)
            this.recvAddr = "";
        else this.recvAddr = recvAddr;
    }

    public String getUID() {
        return uID;
    }

    public void setUID(String uID) {
        if (uID == null)
            this.uID = "";
        else this.uID = uID;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        if (terminal == null)
            this.terminal = "";
        else this.terminal = terminal;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        if (clientIP == null)
            this.clientIP = "";
        else this.clientIP = clientIP;
    }

    public String getFromPage() {
        return fromPage;
    }

    public void setFromPage(String from) {
        if (from == null)
            this.fromPage = "";
        else {
            this.fromPage = from;
            if (from.length()>0){
                int fromIndex = from.indexOf(":");
                int toIndex = from.indexOf("/", fromIndex+3);
                if((fromIndex+3) < from.length() && toIndex < from.length() && toIndex > fromIndex+3){
                    this.source = from.substring(fromIndex+3, toIndex);
                }else{
                    this.source = from;
                }
            }
        }
    }

    public String getToPage() {
        return toPage;
    }

    public void setToPage(String toPage) {
        if (toPage == null)
            this.toPage = "";
        else this.toPage = toPage;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(url != null){
            this.url = url;
        }else{
            this.url = "";
        }

    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        if (pageType == null)
            this.pageType = "N";
        else this.pageType = pageType;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        if (cookie == null)
            this.cookie = "";
        else this.cookie = cookie;
    }

    public String getSearchURL() {
        return searchURL;
    }

    public void setSearchURL(String searchURL) {
        if (searchURL == null)
            this.searchURL = "";
        else this.searchURL = searchURL;
    }

    public String getTraceCode() {
        return traceCode;
    }

    public void setTraceCode(String traceCode) {
        if (traceCode == null)
            this.traceCode = "";
        else this.traceCode = traceCode;
    }

    public String getTransInfo() {
        return transInfo;
    }

    public void setTransInfo(String transInfo) {
        this.transInfo = transInfo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        if (action == null)
            this.action = "";
        else this.action = action;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(String groupSet) {
        this.groupSet = groupSet;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId;
    }

    public String getSearchKW() {
        return searchKW;
    }

    public void setSearchKW(String searchKW) {
        this.searchKW = searchKW;
    }

    public String getSearchPKW() {
        return SearchPKW;
    }

    public void setSearchPKW(String searchPKW) {
        SearchPKW = searchPKW;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getfTime() {
        return fTime;
    }

    public void setfTime(String fTime) {
        this.fTime = fTime;
    }

    public String getlTime() {
        return lTime;
    }

    public void setlTime(String lTime) {
        this.lTime = lTime;
    }

    public String getvTime() {
        return vTime;
    }

    public void setvTime(String vTime) {
        this.vTime = vTime;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getNewSession() {
        return NewSession;
    }

    public void setNewSession(String newSession) {
        NewSession = newSession;
    }

    public String getNewVisitor() {
        return NewVisitor;
    }

    public void setNewVisitor(String newVisitor) {
        NewVisitor = newVisitor;
    }

    public String getTransData() {
        return transData;
    }

    public void setTransData(String transData) {
        this.transData = transData;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getuID() {
        return uID;
    }


    public String getCaKid() {
        return caKid;
    }

    public void setCaKid(String caKid) {
        this.caKid = caKid;
    }

    public String getCaCv() {
        return caCv;
    }

    public void setCaCv(String caCv) {
        this.caCv = caCv;
    }

    public String getCaMdt() {
        return caMdt;
    }

    public void setCaMdt(String caMdt) {
        this.caMdt = caMdt;
    }

    public String getCaPl() {
        return caPl;
    }

    public void setCaPl(String caPl) {
        this.caPl = caPl;
    }

    public String getCaMt() {
        return caMt;
    }

    public void setCaMt(String caMt) {
        this.caMt = caMt;
    }

    public String getCaPn() {
        return caPn;
    }

    public void setCaPn(String caPn) {
        this.caPn = caPn;
    }

    public String getCaDt() {
        return caDt;
    }

    public void setCaDt(String caDt) {
        this.caDt = caDt;
    }

    public String getCaAdp() {
        return caAdp;
    }

    public void setCaAdp(String caAdp) {
        this.caAdp = caAdp;
    }

    public String getCaAbt() {
        return caAbt;
    }

    public void setCaAbt(String caAbt) {
        this.caAbt = caAbt;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getIsSpider() {
        return isSpider;
    }

    public void setIsSpider(String isSpider) {
        this.isSpider = isSpider;
    }

    public String getCaSource() {
        return caSource;
    }

    public void setCaSource(String caSource) {
        this.caSource = caSource;
    }
    public String getOwnUID() {
        return ownUID;
    }

    public void setOwnUID(String ownUID) {
        this.ownUID = ownUID;
    }

    public String getOwnSessionID() {
        return ownSessionID;
    }

    public void setOwnSessionID(String ownSessionID) {
        this.ownSessionID = ownSessionID;
    }



    public String getSearchPn() {
        return searchPn;
    }

    public void setSearchPn(String searchPn) {
        this.searchPn = searchPn;
    }

    public String getSearchCl() {
        return searchCl;
    }

    public void setSearchCl(String searchCl) {
        this.searchCl = searchCl;
    }

    public String getBaiduTn() {
        return baiduTn;
    }

    public void setBaiduTn(String baiduTn) {
        this.baiduTn = baiduTn;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKeywordID() {
        return keywordID;
    }

    public void setKeywordID(String keywordID) {
        this.keywordID = keywordID;
    }

    public String getTerminaType() {
        return terminaType;
    }

    public void setTerminaType(String terminaType) {
        this.terminaType = terminaType;
    }

    public String getCaMedium() {
        return caMedium;
    }

    public void setCaMedium(String caMedium) {
        this.caMedium = caMedium;
    }

    public String getUtmCreative() {
        return utmCreative;
    }

    public void setUtmCreative(String utmCreative) {
        this.utmCreative = utmCreative;
    }

    public String getUtmGroupset() {
        return utmGroupset;
    }

    public void setUtmGroupset(String utmGroupset) {
        this.utmGroupset = utmGroupset;
    }

    public String getUtmKeyword() {
        return utmKeyword;
    }

    public void setUtmKeyword(String utmKeyword) {
        this.utmKeyword = utmKeyword;
    }
    @Override
    public String toString() {
        return "MergedDTO [timestamp=" + timestamp + ", pageType=" + pageType
                + ", recvAddr=" + recvAddr + ", uID=" + uID +", OWNID=" + ownUID + ", tenantID="
                + tenantID + ", terminal=" + terminal + ", clientIP="
                + clientIP + ", fromPage=" + fromPage + ", toPage=" + toPage
                + ", action=" + action + ", searchURL=" + searchURL
                + ", traceCode=" + traceCode + ", transInfo=" + transInfo
                + ", cookie=" + cookie + ", source=" + source + ", utmSource="
                + utmSource + ", campaign=" + campaign + ", campaignId="
                + campaignId + ", medium=" + medium + ", groupSet=" + groupSet
                + ", keyword=" + keyword + ", creativeId=" + creativeId
                + ", searchKW=" + searchKW + ", SearchPKW=" + SearchPKW
                + ", position=" + position + ", fTime=" + fTime + ", lTime="
                + lTime + ", vTime=" + vTime + ", sTime=" + sTime + ", keywordid=" + keywordID
                + ", NewSession=" + NewSession + ", NewVisitor=" + NewVisitor
                + ", transData=" + transData + ", logTime=" + logTime
                + ", url=" + url + ", area=" + area + ", caKid=" + caKid
                + ", caCv=" + caCv + ", caMdt=" + caMdt + ", caPl=" + caPl
                + ", caMt=" + caMt + ", caPn=" + caPn + ", caDt=" + caDt
                + ", caAdp=" + caAdp + ", caAbt=" + caAbt + ", sessionID="
                + sessionID+", ownSessionID="+ownSessionID
                + ",joinKey="+joinKey
                + ",caSbtType="+caSbtType
                +",caSource=" +caSource+ ",cl="+searchCl+",pn="+searchPn+",tn="+baiduTn
                + ", isSpider=" + isSpider + "]";
    }


    public static String  getIpOfStr(String str){
        Pattern pattern = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
        Matcher m = pattern.matcher(str);
        String result = "";
        while (m.find()) {
            result = m.group();
            if (!"".equals(result)) {
                break;
            }
        }

        return result;
    }
//    public static void main(String[] args) throws ParseException {
//        MergedDTO dto = new MergedDTO();
//        CaLogImporter caLogImporter = new CaLogImporter();
//
////  	    String line ="[CARAWDATA  - 2014-09-10T00:00:04.726 ][Timestamp: 1410278404726 ][Browser: 115.239.212.194 - Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:17.0; Baiduspider-ads) Gecko/17.0 Firefox/17.0 ][Referre: http://shaoxing.xueda.com/?k=ppc&f=baiduppc&ca_abt=%7Babtest%7D&ca_adp=%7Badposition%7D&ca_cv=%7Bcreative%7D&ca_dt=%7Bdongtai%7D&ca_kid=%7Bkeywordid%7D&ca_mdt=%7Bmediatype%7D&ca_mt=%7Bmatchtype%7D&ca_pl=%7Bplacement%7D&ca_pn=%7Bpagenum%7D&chl=baidu&utm_campaign=a@%e9%87%8d%e5%ba%86&utm_content=%e9%87%8d%e5%ba%86%e5%ae%b6%e6%95%99-%e5%b9%b4%e7%ba%a7-%e5%b0%8f%e5%ad%a6&utm_medium=cpc&utm_source=baidu&ca_source=baidu&utm_term=%e5%9b%9b%e5%b9%b4%e7%ba%a7%e8%a1%a5%e8%af%be ][Request: http://www.cubead.cn:7070/recv/trace.do?ca_dm=www.xueda.com&ca_tenant=107427&ca_ptype=L&ca_vtime=34-1-1&ca_preref=&ca_action=0-0-PAGE%3ANORMAL%28PAGE%3ALANDING%29&ca_cookie=xueda_City%3Dxueda_city_id_key%3Dhttp%3A//shaoxing.xueda.com%3B%20CloudGuest%3DeuKnBgvLk9NHXtyuLb8OBbcLK+r/qnLyKkdUHolq+eg267t91FKvJvqJEocVI6278o1PLk++EOEU5vPoHFRnKikkQG8an4wR77DiwhgAzxE5j3IxgJZPUs5C1NtdP4RiP+7iF7G9074noBm2eQNT8YbmGfsi5q0tRgiV+FkDkBm8FBDF3nssdhqpMF05li81%3B%20__XueDaUID%3D100%3B%20__XueDaPID%3D100%3B%20xuedareferrer%3Dhttp%253A//shaoxing.xueda.com/%253Fk%253Dppc%2526f%253Dbaiduppc%2526ca_abt%253D%25257Babtest%25257D%2526ca_adp%253D%25257Badposition%25257D%2526ca_cv%253D%25257Bcreative%25257D%2526ca_dt%253D%25257Bdongtai%25257D%2526ca_kid%253D%25257Bkeywordid%25257D%2526ca_mdt%253D%25257Bmediatype%25257D%2526ca_mt%253D%25257Bmatchtype%25257D%2526ca_pl%253D%25257Bplacement%25257D%2526ca_pn%253D%25257Bpagenum%25257D%2526chl%253Dbaidu%2526utm_campaign%253Da@%2525e9%252587%25258d%2525e5%2525ba%252586%2526utm_content%253D%2525e9%252587%25258d%2525e5%2525ba%252586%2525e5%2525ae%2525b6%2525e6%252595%252599-%2525e5%2525b9%2525b4%2525e7%2525ba%2525a7-%2525e5%2525b0%25258f%2525e5%2525ad%2525a6%2526utm_medium%253Dcpc%2526utm_source%253Dbaidu%2526utm_term%253D%2525e5%25259b%25259b%2525e5%2525b9%2525b4%2525e7%2525ba%2525a7%2525e8%2525a1%2525a5%2525e8%2525af%2525be%3B%20looyu_id%3D5e15b6f83c4c0f8f7b13a9f0c922d2d7e9_41098%253A1%3B%20looyu_41098%3Dv%253A5e15b6f83c4c0f8f7b13a9f0c922d2d7e9%252Cref%253A%252Cr%253A%252Cmon%253Ahttp%253A//m154.looyu.com/monitor%3B%20CNZZDATA1441140%3Dcnzz_eid%253D1255354061-1410273762-%2526ntime%253D1410273762%3B%20ca_ftime%3D1410307199863%3B%20ca_ltime%3D1410307199863%3B%20ca_stime%3D1410307199863%3B%20ca_se%3DDIRECT%3B%20ca_trace%3Dca_abt%253D%25257Babtest%25257D%2526ca_adp%253D%25257Badposition%25257D%2526ca_cv%253D%25257Bcreative%25257D%2526ca_dt%253D%25257Bdongtai%25257D%2526ca_kid%253D%25257Bkeywordid%25257D%2526ca_mdt%253D%25257Bmediatype%25257D%2526ca_mt%253D%25257Bmatchtype%25257D%2526ca_pl%253D%25257Bplacement%25257D%2526ca_pn%253D%25257Bpagenum%25257D%2526utm_campaign%253Da@%2525e9%252587%25258d%2525e5%2525ba%252586%2526utm_content%253D%2525e9%252587%25258d%2525e5%2525ba%252586%2525e5%2525ae%2525b6%2525e6%252595%252599-%2525e5%2525b9%2525b4%2525e7%2525ba%2525a7-%2525e5%2525b0%25258f%2525e5%2525ad%2525a6%2526utm_medium%253Dcpc%2526utm_source%253Dbaidu%2526utm_term%253D%2525e5%25259b%25259b%2525e5%2525b9%2525b4%2525e7%2525ba%2525a7%2525e8%2525a1%2525a5%2525e8%2525af%2525be ][Cookie: CAUID=71e7013b404726_73EFD4C2_50932;SESSION_ID=3158EA77B9DE347EAD0094C8437A8A04 ][Body:  ]";
//        //String line ="[CARAWDATA  - 2015-03-24T19:46:14.188 ][Timestamp: 1427197574188 ][Browser: 211.157.179.12 - Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0 ][Referre: http://www.dafengso.cn/ ][Request: http://www.cubead.cn:7070/recv/trace.do?ca_dm=www.dafengso.cn&ca_tenant=6913&ca_ptype=C&ca_vtime=23679-0-0&ca_preref=&ca_cookie=ca_ftime%3D1426841192741%3B%20ca_ltime%3D1426841192741%3B%20ca_se%3DDIRECT%3B%20Hm_lvt_f94326d309761a42e1d8e0ec1b1eefbc%3D1426842539%2C1426844402%2C1427188238%2C1427193105%3B%20BRIDGE_R1842154%3D%3B%20ca_uid%3DCC3D21AA5F1C4AB4896C50372B82DAE0%3B%20ca_se%3DDIRECT%3B%20ca_ftime%3D1427189563948%3B%20tq_current_visit_time%3D1427193104591%3B%20ca_sessionid%3D941261EB8E2447918B8B66A6492BC76C%3B%20ca_stime%3D1427197554251%3B%20Hm_lpvt_f94326d309761a42e1d8e0ec1b1eefbc%3D1427197554%3B%20VERSION%3D2%2C0%2C0%2C0%3B%20BRIDGE_INVITE_0%3D0%3B%20BRIDGE_REFRESH%3D5000%3B%20BRIDGE_CLOCK%3D1427197573169%3B%20BRIDGE_NEED%3D1%3B%20baidu_qiao_v3_count_1842154%3D1&ca_action=0-0-PAGE%3ACONVERT%28_conversation_%29&ca_sessionid=941261EB8E2447918B8B66A6492BC76C&ca_uid=CC3D21AA5F1C4AB4896C50372B82DAE0 ][Cookie: CAUID=3d67d3d1980739_D39DB30C_496;CAUIDX=3d67d3d1980739_D39DB30C_496;JSESSIONID=87D67D6A9C37BD6FF56B4EBF85EF675D;SESSION_ID=87D67D6A9C37BD6FF56B4EBF85EF675D;OWN_UID=CC3D21AA5F1C4AB4896C50372B82DAE0;OWN_SESSION_ID=941261EB8E2447918B8B66A6492BC76C ][Body:  ]";
//        // String line="[CARAWDATA  - 2015-04-21T18:06:28.288 ][Timestamp: 1429610788288 ][Browser: 127.0.0.1 - Mozilla/5.0 (Windows NT 6.1; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0 ][Referre: http://www.dafengso.cn/ ][Request: http://carev.cubead.cn/recv/trace.do?ca_dm=www.dafengso.cn&ca_tenant=119378&ca_ptype=C&ca_vtime=43959-0-0&ca_preref=&ca_action=296-1378-PAGE%3AEVENT%28_crt_track%29&ca_cookie=ca_ftime%3D1426841192741%3B%20Hm_lvt_f94326d309761a42e1d8e0ec1b1eefbc%3D1428645516%2C1429524005%2C1429583892%2C1429589016%3B%20BRIDGE_R1842154%3D%3B%20ca_uid%3DCC3D21AA5F1C4AB4896C50372B82DAE0%3B%20ca_se%3DDIRECT%3B%20ca_ftime%3D1427189563948%3B%20ca_trace%3Dutm_source%253Dbaidu%3B%20join_key%3DCC3D21AA5F1C4AB4896C50372B82DAE0_BDF72859A46340CD8AA24654E95708BC_1429610744151%3B%20ca_ltime%3D1429583891806%3B%20ca_sessionid%3DBDF72859A46340CD8AA24654E95708BC%3B%20ca_stime%3D1429610744107%3B%20tq_current_visit_time%3D1429589015757%3B%20Hm_lpvt_f94326d309761a42e1d8e0ec1b1eefbc%3D1429610744%3B%20VERSION%3D2%2C0%2C0%2C0%3B%20BRIDGE_INVITE_0%3D0%3B%20BRIDGE_REFRESH%3D5000%3B%20BRIDGE_CLOCK%3D1429610784029%3B%20BRIDGE_NEED%3D1%3B%20baidu_qiao_v3_count_1842154%3D1%3B%20_DOYOO_RESEVE_KEY%3D%2523params%253AuserId%252CCC3D21AA5F1C4AB4896C50372B82DAE0_BDF72859A46340CD8AA24654E95708BC_1429610758492&ca_sessionid=BDF72859A46340CD8AA24654E95708BC&ca_uid=CC3D21AA5F1C4AB4896C50372B82DAE0&ca_join=CC3D21AA5F1C4AB4896C50372B82DAE0_BDF72859A46340CD8AA24654E95708BC_1429610744151 ][Cookie: CAUID=bb807262885250_D39DB30C_649413;CAUIDX=bb807262885250_D39DB30C_649413;JSESSIONID=995CB928FCF626C2F1C1A0E4A1DB5658;SESSION_ID=995CB928FCF626C2F1C1A0E4A1DB5658;OWN_UID=CC3D21AA5F1C4AB4896C50372B82DAE0;OWN_SESSION_ID=BDF72859A46340CD8AA24654E95708BC;JOIN_KEY=CC3D21AA5F1C4AB4896C50372B82DAE0_BDF72859A46340CD8AA24654E95708BC_1429610744151 ][Body:  ]";
//        String line="[CARAWDATA  - 2015-04-21T18:06:29.232 ][Timestamp: 1429610789232 ][Browser: 127.0.0.1 - Mozilla/5.0 (Windows NT 6.1; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0 ][Referre: http://www.dafengso.cn/ ][Request: http://carev.cubead.cn/recv/trace.do?ca_dm=www.dafengso.cn&ca_tenant=119378&ca_ptype=C&ca_vtime=44074-0-0&ca_preref=&ca_sbttype=1&ca_cookie=ca_ftime%3D1426841192741%3B%20Hm_lvt_f94326d309761a42e1d8e0ec1b1eefbc%3D1428645516%2C1429524005%2C1429583892%2C1429589016%3B%20BRIDGE_R1842154%3D%3B%20ca_uid%3DCC3D21AA5F1C4AB4896C50372B82DAE0%3B%20ca_se%3DDIRECT%3B%20ca_ftime%3D1427189563948%3B%20ca_trace%3Dutm_source%253Dbaidu%3B%20join_key%3DCC3D21AA5F1C4AB4896C50372B82DAE0_BDF72859A46340CD8AA24654E95708BC_1429610744151%3B%20ca_ltime%3D1429583891806%3B%20ca_sessionid%3DBDF72859A46340CD8AA24654E95708BC%3B%20ca_stime%3D1429610744107%3B%20tq_current_visit_time%3D1429589015757%3B%20Hm_lpvt_f94326d309761a42e1d8e0ec1b1eefbc%3D1429610744%3B%20VERSION%3D2%2C0%2C0%2C0%3B%20BRIDGE_INVITE_0%3D0%3B%20BRIDGE_REFRESH%3D5000%3B%20BRIDGE_CLOCK%3D1429610784029%3B%20BRIDGE_NEED%3D1%3B%20baidu_qiao_v3_count_1842154%3D1%3B%20_DOYOO_RESEVE_KEY%3D%2523params%253AuserId%252CCC3D21AA5F1C4AB4896C50372B82DAE0_BDF72859A46340CD8AA24654E95708BC_1429610788067&ca_action=0-0-PAGE%3ACONVERT%28_crt_click%29&ca_sessionid=BDF72859A46340CD8AA24654E95708BC&ca_uid=CC3D21AA5F1C4AB4896C50372B82DAE0&ca_join=CC3D21AA5F1C4AB4896C50372B82DAE0_BDF72859A46340CD8AA24654E95708BC_1429610744151 ][Cookie: CAUID=bb807262885250_D39DB30C_649413;CAUIDX=bb807262885250_D39DB30C_649413;JSESSIONID=995CB928FCF626C2F1C1A0E4A1DB5658;SESSION_ID=995CB928FCF626C2F1C1A0E4A1DB5658;OWN_UID=CC3D21AA5F1C4AB4896C50372B82DAE0;OWN_SESSION_ID=BDF72859A46340CD8AA24654E95708BC;JOIN_KEY=CC3D21AA5F1C4AB4896C50372B82DAE0_BDF72859A46340CD8AA24654E95708BC_1429610744151 ][Body:  ]";
//        String[] data = line.split("\\]\\[");
//        caLogImporter.GenerateDTO(data, dto);
//        System.out.println(dto);
//        //System.out.println(getIpOfStr("http://180.169.24.249/cpsg/gcbj/ksbjNO5/index.shtml"));
//    }

}
