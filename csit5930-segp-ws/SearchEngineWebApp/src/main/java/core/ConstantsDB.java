package core;

public class ConstantsDB {
    
    public final static String db_path  = "/SQLite/";
    public static final String dbFile   = db_path + "csit5930";
    
    /****************************************************************************************************
     * Insert
     ****************************************************************************************************/
    public static final String insertUrl          = "insert into url(url) values(?);";
    public static final String insertUrlTemp      = "insert into url_temp(url) values(?);";
    public static final String insertTerm         = "insert into term(term) values(?);";
    public static final String insertStem         = "insert into stem(stem) values(?);";
    public static final String insertRawToken     = "insert into raw_token(page_id, term_id, type, position) values(?, ?, ?, ?);";
    public static final String insertStemToken    = "insert into stem_token(page_id, stem_id,type, position) values(?, ?, ?, ?);";
    public static final String insertMaxTF        = "insert into max_tf(page_id, max_tf, type) values(?, ?, ?);";
    public static final String insertUrlInverted  = "insert into url_inverted(parent_page_id, child_page_id) values(?, ?);";
//    public static final String insertUrlForward   = "insert into url_forward(child_page_id, parent_page_id) values(?, ?);";
    public static final String insertStemInverted = "insert into stem_inverted(stem_id, page_id, type) values(?, ?, ?);";
    public static final String insertStemForward  = "insert into stem_forward(page_id, stem_id, type) values(?, ?, ?);";
    public static final String insertStemPosition = "insert into stem_position(stem_id, page_id, type, position) values(?, ?, ?, ?);";
    public static final String insertStemDF       = "insert into stem_df(stem_id, df) values(?, ?);";
    
    /****************************************************************************************************
     * Update
     ****************************************************************************************************/
    public static final String updateInitiallRawContent
    = "update url set raw_title=?, last_modified_date=?, raw_content=?, doc_length=? where url=?;";
    
    public static final String updateInitiallClearTitle
    = "update url set clear_title=? where page_id=?;";
    public static final String updateInitiallClearContent
    = "update url set clear_content=? where page_id=?;";
    
    public static final String updateInitiallStemTitle
    = "update url set stem_title=? where page_id=?;";
    public static final String updateInitiallStemContent
    = "update url set stem_content=? where page_id=?;";
    
    /****************************************************************************************************
     * Delete
     ****************************************************************************************************/
    public static final String deleteUrl_temp     = "delete from url_temp";
    
    /****************************************************************************************************
     * Select
     ****************************************************************************************************/
    public static final String selectAllUrl
    = "select * from url;";
    public static final String selectNewUrl
    = "select * from url_temp ut left join url u on u.url = ut.url where u.url is null;";
    public static final String selectRemovedUrl
    = "select * from url u left join url_temp ut on ut.url = u.url where ut.url is null;";
    public static final String selectDeltaUrl
    = "select * from url where doc_length is null;";
    
    
    public static final String selectAllRawTitleWithPageId
    = "select page_id || ':' || raw_title as 'data' from url;";
    public static final String selectDeltaRawTitleWithPageId
    = "select page_id || ':' || raw_title as 'data', clear_title  from url where clear_title is null;";
    public static final String selectAllRawContentWithPageId
    = "select page_id || ':' || raw_content as 'data' from url;";
    public static final String selectDeltaRawContentWithPageId
    = "select page_id || ':' || raw_content as 'data', clear_content from url where clear_content is null;";
    
    public static final String selectAllClearTitleWithPageId
    = "select page_id || ':' || clear_title as 'data' from url;";
    public static final String selectDeltaClearTitleWithPageId
    = "select page_id || ':' || clear_title as 'data', stem_title from url where stem_title  is null;";
    public static final String selectAllClearContentWithPageId
    = "select page_id || ':' || clear_content as 'data' from url;";
    public static final String selectDeltaClearContentWithPageId
    = "select page_id || ':' || clear_content as 'data', stem_content from url where stem_content is null;";
    
    public static final String selectAllStemTitleWithPageId
    = "select page_id || ':' || stem_title as 'data' from url;";
    public static final String selectAllStemContentWithPageId
    = "select page_id || ':' || stem_content as 'data' from url;";
    
    public static final String selectAllClearTitleAndContentWithPageId
    = "select data from "
            + "("
            + "select page_id || ':' || clear_title as 'data' from url "
            + "union "
            + "select page_id || ':' || clear_content as 'data' from url"
            + ")"
            + "order by 'data';";
    
    public static final String selectAllStemTitleAndContentWithPageId
    = "select data from "
            + "("
            + "select page_id || ':' || stem_title as 'data' from url "
            + "union "
            + "select page_id || ':' || stem_content as 'data' from url"
            + ")"
            + "order by 'data';";
    
    public static final String selectAllPageIdClearTitle
    = "select page_id, clear_title as data from url;";
    public static final String selectAllPageIdStemTitle
    = "select page_id, stem_title as data from url;";
    
    public static final String selectAllPageIdClearContent
    = "select page_id, clear_content as data from url;";
    public static final String selectAllPageIdStemContent
    = "select page_id, stem_content as data from url;";
    
    
    public static final String selectAllPageId
    = "select page_id from url;";
    public static final String selectUrlByPageId
    = "select * from url where page_id=?;";
    public static final String selectPageIdByUrl
    = "select * from url where url=?;";
    
    public static final String selectTermIdByTerm
    = "select term_id as id from term where term=?;";
    public static final String selectStemIdByStem
    = "select stem_id as id from stem where stem=?;";
    
    public static final String selectMaxTfByPageId
    = "select max(count) as max_tf "
            + "from ("
            + "     select "
            + "         st.page_id , st.stem_id, s.stem ,st.position ,count(s.stem) as count "
            + "     from stem_token st "
            + "     left join stem s on s.stem_id =st.stem_id where 1=1 "
            + "     and st.page_id=? "
            + "     and st.type=? "
            + "group by st.page_id, st.stem_id, s.stem"
            + ")";
    
    /****************************************************************************************************
     * Insert-Select
     ****************************************************************************************************/
    public static final String insertSelectUrlForward
    = "insert into url_forward (child_page_id, parent_page_id) "
            + "select child_page_id, parent_page_id "
            + "from url_inverted "
            + "order by child_page_id, parent_page_id;";
    
    /****************************************************************************************************
     * Misc.
     ****************************************************************************************************/
    public static final String indexTypeTerm = "term";
    public static final String indexTypeStem = "stem";
    
    public static final String scopeAllRawTitle = "scopeAllRawTitle";
    public static final String scopeAllRawContent = "scopeAllRawContent";
    public static final String scopeAllClearTitle = "scopeAllClearTitle";
    public static final String scopeAllClearContent = "scopeAllClearContent";
    public static final String scopeAllStemTitle = "scopeAllStemTitle";
    public static final String scopeAllStemContent = "scopeAllStemContent";
    public static final String scopeAllClearTitleAndContent = "scopeAllClearTitleAndContent";
    public static final String scopeAllStemTitleAndContent = "scopeAllStemTitleAndContent";
    
    public static final String tokenTypeClearTitle   = "clearTitleToken";
    public static final String tokenTypeClearContent = "clearContentToken";
    public static final String tokenTypeStemTitle   = "stemTitleToken";
    public static final String tokenTypeStemContent = "stemContentToken";
    
    public static final String dataTypeTitle    = "titleData";
    public static final String dataTypeContent  = "ContentData";
    
    public static final int dsTypeTitle = 1;
    public static final int dsTypeContent = 2;
    
    public static final String buildUpdateTypeFull = "full";
    public static final String buildUpdateTypePartial = "partial";
}
