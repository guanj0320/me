package pf.persistent.dao;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pf.persistent.ibatis.LimitSqlExecutor;
import pf.tools.reflect.ReflectUtil;


import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;

public abstract class BaseDAO extends SqlMapClientDaoSupport {
	private SqlExecutor sqlExecutor;   
	  
    public SqlExecutor getSqlExecutor() {   
        return sqlExecutor;   
    }   
  
    public void setSqlExecutor(SqlExecutor sqlExecutor) {   
        this.sqlExecutor = sqlExecutor;   
    }   
  
    public void setEnableLimit(boolean enableLimit) {   
        if (sqlExecutor instanceof LimitSqlExecutor) {   
            ((LimitSqlExecutor) sqlExecutor).setEnableLimit(enableLimit);   
        }   
    }   
  
    public void initialize() throws Exception {   
        if (sqlExecutor != null) {   
            SqlMapClient sqlMapClient = getSqlMapClientTemplate()
                    .getSqlMapClient();   
            if (sqlMapClient instanceof ExtendedSqlMapClient) {   
                ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient)   
                        .getDelegate(), "sqlExecutor", SqlExecutor.class,   
                        sqlExecutor);   
            }   
        }   
    }   

}
