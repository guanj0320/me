package pf.persistent.ibatis.dialect;

public class Oracle9iDialect implements Dialect {
	
	protected static final String SQL_END_DELIMITER = ";";   

	public String getLimitString(String sql, boolean hasOffset) {
//		sql = sql.trim();
//        boolean isForUpdate = false;
//        if(sql.toLowerCase().endsWith(" for update"))
//        {
//            sql = sql.substring(0, sql.length() - 11);
//            isForUpdate = true;
//        }
//        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
//        if(hasOffset)
//            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
//        else
//            pagingSelect.append("select * from ( ");
//        pagingSelect.append(sql);
//        if(hasOffset)
//            pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
//        else
//            pagingSelect.append(" ) where rownum <= ?");
//        if(isForUpdate)
//            pagingSelect.append(" for update");
//        return pagingSelect.toString();
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if(hasOffset)
			pagingSelect.append("select * from (select rownum as PageSortID , TEMP_EXPPAGEQUERY.*  from ( ");
		else 
			pagingSelect.append("select * from ( ");
		pagingSelect.append(sql);
		if(hasOffset)
			pagingSelect.append(" ) TEMP_EXPPAGEQUERY) where PageSortID > ? and PageSortID <= ?");
		else 
			pagingSelect.append(" ) where rownum <= ?");
		return pagingSelect.toString();
	}

	public String getLimitString(String sql, int offset, int limit) {
//		sql = sql.trim();
//        boolean isForUpdate = false;
//        if(sql.toLowerCase().endsWith(" for update"))
//        {
//            sql = sql.substring(0, sql.length() - 11);
//            isForUpdate = true;
//        }
//        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
//        if(offset > 0)
//            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
//        else
//            pagingSelect.append("select * from ( ");
//        pagingSelect.append(sql);
//        if(offset > 0)
//            pagingSelect.append(" ) row_ where rownum <= "+limit+") where rownum_ > "+offset);
//        else
//            pagingSelect.append(" ) where rownum <= ?");
//        if(isForUpdate)
//            pagingSelect.append(" for update");
//        return pagingSelect.toString();
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("select * from (select rownum as PageSortID , TEMP_EXPPAGEQUERY.*  from ( ");
		pagingSelect.append(sql);
		if(offset > 0)
			pagingSelect.append(" ) TEMP_EXPPAGEQUERY) where PageSortID > "+offset+" and PageSortID <= "+(offset+limit));
		else 
			pagingSelect.append(" ) TEMP_EXPPAGEQUERY) where PageSortID > 0 and PageSortID <= "+(limit));
		return pagingSelect.toString();
	}

	public boolean supportsLimit() {
		return true;
	}

	private String trim(String sql) {   
        sql = sql.trim();   
        if (sql.endsWith(SQL_END_DELIMITER)) {   
            sql = sql.substring(0, sql.length() - 1  
                    - SQL_END_DELIMITER.length());   
        }   
        return sql;   
    }  
}
