package core;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import utils.DBUtil;
import utils.DateUtil;

public class SearchEngine extends Base {
    
    private static DBUtil db = null;
    private static Connection conn = null;
    private static ResultSet rs = null;
    
    private static ArrayList<Object> data;
    private static ArrayList<Object> criteria;
    
    public static void main(String[] args) {
        
//        printKVPair("max_tf", getMaxTfByPageId(18, 2));
        
//      getFullUrlList();
      
      /*
       * [PageId][Url]
       * [296][https://www.cse.ust.hk/~kwtleung/COMP4321/testpage.htm]
       */
//        getUrlByPageId(296);
//        getPageIdByUrl("https://www.cse.ust.hk/~kwtleung/COMP4321/testpage.htm");
        
//        ArrayUtil.printArrayList(getAllUrlClearContent());
        
//        getStemIdByStem("apps");
    }
    
    public static int getStemIdByStem(String m_stemTerm, String m_stem_term_type) {
        
        String query = null;
        if(m_stem_term_type.equals(ConstantsDB.indexTypeTerm)) {
            query = ConstantsDB.selectTermIdByTerm;
        } else if(m_stem_term_type.equals(ConstantsDB.indexTypeStem)) {
            query = ConstantsDB.selectStemIdByStem;
        }
        
        int stemId = -1;
        
        try {
            
            db = new DBUtil();
            conn = db.getConnection();
            criteria = new ArrayList<Object>();
            criteria.add(m_stemTerm);
            
            rs = db.genericSearch(conn, query, criteria);
            
            if(rs.next()) {
                stemId = rs.getInt("id");
            }
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
//        if(stemId==-1) {
//            System.out.println("[" + m_stemTerm + "][" + m_stem_term_type + "][" + stemId + "]");
//        }
        
        return stemId;
    }
    
    
    
    public static int getPageIdByUrl(String m_url) {
        int pageId = -1;
        
       try {
        
            db = new DBUtil();
            conn = db.getConnection();
            criteria = new ArrayList<Object>();
            criteria.add(m_url);
            rs = db.genericSearch(conn, ConstantsDB.selectPageIdByUrl, criteria);
            if(rs.next()) {
                pageId = rs.getInt("page_id");
            }
            
            conn.close();
       } catch(SQLException e) {
           e.printStackTrace();
       }
//       printKVPair(pageId, m_url);
       
       return pageId;
    }
    
//    public static String getUrlByPageId(int m_pageId) {
//        String url = "";
//        
//       try {
//        
//            db = new DBUtil();
//            conn = db.getConnection();
//            ArrayList<Object> criteria = new ArrayList<Object>();
//            criteria.add(m_pageId);
//            rs = db.genericSearch(conn, ConstantsDB.selectUrlByPageId, criteria);
//            if(rs.next()) {
//                url = rs.getString("Url");
//            }
//            
//            conn.close();
//       } catch(SQLException e) {
//           e.printStackTrace();
//       }
////       printKVPair(m_pageId, url);
//       
//       return url;
//        
    
    public static ArrayList<String> getFullUrlList(boolean m_with_page_id) {
        ArrayList<String> urlList = new ArrayList<String>();
        
        try {
            
            db = new DBUtil();
            conn = db.getConnection();
            rs = db.genericSearch(conn, ConstantsDB.selectAllUrl, null);
            
            while(rs.next()) {
                String pageId = rs.getString("page_id");
                String url = rs.getString("url");
                if(m_with_page_id) {
                    urlList.add(pageId+":"+url);
                } else {
                    urlList.add(url);
                }
            }
//            ArrayUtil.printArrayList(urlList);
            
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return urlList;
    }
    
    public static void fullRecraw() {
        try {
            // reset <url_temp> table
            db = new DBUtil();
            conn = db.getConnection();
            db.genericInsertUpdateDelete(conn, ConstantsDB.deleteUrl_temp, null);
            conn.commit();
            
            ArrayList<String> urls = Spider.getAllUrlList(Constants.base_url);
            
            for(String url: urls) {
                
                URL i_url = new URL(url);
                HttpURLConnection httpCon = (HttpURLConnection) i_url.openConnection();
                Date web_date = new Date(httpCon.getLastModified());
                String strLastModifiedDate =  DateUtil.getFormattedDate(web_date);
                
                data = new ArrayList<Object>();
                data.add(url);
                data.add(strLastModifiedDate);
                db.genericInsertUpdateDelete(conn, ConstantsDB.insertUrlTemp, data);
            }
            conn.commit();
            
            conn.close();
            
        } catch(IOException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<String> getNewUrl() {
        ArrayList<String> urls = new ArrayList<String>();
        
        try {
            db = new DBUtil();
            conn = db.getConnection();
            rs = db.genericSearch(conn, ConstantsDB.selectNewUrl, null);
            while(rs.next()) {
                String url = rs.getString("url");
                urls.add("add|"+url+"|-|-");
            }
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return urls;
    }
    
    public static ArrayList<String> getRemovedUrl() {
        ArrayList<String> urls = new ArrayList<String>();
        
        try {
            db = new DBUtil();
            conn = db.getConnection();
            rs = db.genericSearch(conn, ConstantsDB.selectRemovedUrl, null);
            while(rs.next()) {
                String url = rs.getString("url");
                urls.add("remove|"+url+"|-|-");
            }
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return urls;
    }
    
    public static ArrayList<String> getModifiedUrl() {
        ArrayList<String> urls = new ArrayList<String>();
        
        try {
            db = new DBUtil();
            conn = db.getConnection();
            rs = db.genericSearch(conn, ConstantsDB.selectModifiedUrl, null);
            while(rs.next()) {
                String url = rs.getString("url");
                String oldDate = rs.getString("old_date");
                String newDate = rs.getString("new_date");
                
                String data = "modify"+"|"+url+"|"+oldDate+"|"+newDate;
                urls.add(data);
            }
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return urls;
    }
    
    public static ArrayList<String> getSrcWithPageId(String m_select_score) {
//        printKVPair("m_select_score", m_select_score);
        ArrayList<String> list = new ArrayList<String>();
        
        String query = "";
        
        if(m_select_score.equals(ConstantsDB.scopeAllRawTitle)) {
            query = ConstantsDB.selectAllRawTitleWithPageId;
        } else if(m_select_score.equals(ConstantsDB.scopeAllRawContent)) {
            query = ConstantsDB.selectAllRawContentWithPageId;
        } else if(m_select_score.equals(ConstantsDB.scopeAllClearTitle)) {
            query = ConstantsDB.selectAllClearTitleWithPageId;
        } else if(m_select_score.equals(ConstantsDB.scopeAllClearContent)) {
            query = ConstantsDB.selectAllClearContentWithPageId;
        } else if(m_select_score.equals(ConstantsDB.scopeAllStemTitle)) {
            query = ConstantsDB.selectAllStemTitleWithPageId;
        } else if(m_select_score.equals(ConstantsDB.scopeAllStemContent)) {
            query = ConstantsDB.selectAllStemContentWithPageId;
        } else if(m_select_score.equals(ConstantsDB.scopeAllClearTitleAndContent)) {
            query = ConstantsDB.selectAllClearTitleAndContentWithPageId;
        } else if(m_select_score.equals(ConstantsDB.scopeAllStemTitleAndContent)) {
            query = ConstantsDB.selectAllStemTitleAndContentWithPageId;
        } else {
            printHelloWorld();
        }
        
        try {
            db = new DBUtil();
            conn = db.getConnection();
            rs = db.genericSearch(conn, query, null);
            
            while(rs.next()) {
                String item = rs.getString("data");
                list.add(item);
            }
            
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return list;
        
    }
    
    public static int getItemIdByItemName(String m_item_type, String m_item_name) {
        int itemId = -1;
        String query = null;
        
        if(m_item_type.equals(ConstantsDB.indexTypeTerm)) {
            query = ConstantsDB.selectTermIdByTerm;
        } else if(m_item_type.equals(ConstantsDB.indexTypeStem)) {
            query = ConstantsDB.selectStemIdByStem;
        }
        
        try {
            
            db = new DBUtil();
            conn = db.getConnection();
            criteria = new ArrayList<Object>();
            criteria.add(m_item_name);
            rs = db.genericSearch(conn, query, criteria);
            if(rs.next()) {
                itemId = rs.getInt("id");
            }
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
//        printKVPair(termId, m_term);
        
        return itemId;
    }
    
    public static ArrayList<Integer> getAllPageId() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        try {
            db = new DBUtil();
            conn = db.getConnection();
            rs = db.genericSearch(conn, ConstantsDB.selectAllPageId, null);
            
            while(rs.next() ) {
                list.add(rs.getInt("page_id"));
            }
            
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public static int getMaxTfByPageId(int m_pageId, int m_src_type) {
        int pageId = -1;
        
        try {
            db = new DBUtil();
            conn = db.getConnection();
            criteria = new ArrayList<Object>();
            criteria.add(m_pageId);
            criteria.add(m_src_type);
            
            rs = db.genericSearch(conn, ConstantsDB.selectMaxTfByPageId, criteria);
            
            if(rs.next()) {
                pageId = rs.getInt("max_tf");
            }
            
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return pageId;
    }
    
    public static void resetUrlTemp() {
        try {
            db = new DBUtil();
            conn = db.getConnection();
            db.genericInsertUpdateDelete(conn, ConstantsDB.deleteUrl_temp, null);
            conn.commit();
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
