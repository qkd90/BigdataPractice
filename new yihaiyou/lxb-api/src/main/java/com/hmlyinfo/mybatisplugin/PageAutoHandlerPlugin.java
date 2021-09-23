package com.hmlyinfo.mybatisplugin;

import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.log4j.Logger;

import com.hmlyinfo.base.persistent.PageDto;

@Intercepts({@org.apache.ibatis.plugin.Signature(type=StatementHandler.class, method="prepare", args={java.sql.Connection.class})})
public class PageAutoHandlerPlugin
  implements Interceptor
{
  private final Logger log = Logger.getLogger(PageAutoHandlerPlugin.class);
  private static final String DELEGATE_PARAMETER_HANDLER = "delegate.parameterHandler";
  private static final String DELEGATE_BOUNDSQL_SQL = "delegate.boundSql.sql";
  private static final String DELEGATE_ROW_BOUNDS_OFFSET = "delegate.rowBounds.offset";
  private static final String DELEGATE_ROW_BOUNDS_LIMIT = "delegate.rowBounds.limit";
  private static final String DIALECT_PROPERTIES_KEY = "dialect";
  private static String dataBaseDialectType = "";

  public Object intercept(Invocation inv)
    throws Throwable
  {
    StatementHandler statementHandler = (StatementHandler)inv.getTarget();
    MetaObject metaObject = MetaObject.forObject(statementHandler);
    
    // count语句不拦截
    MappedStatement stmt = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
    if (stmt.getId().endsWith("count"))
    {
    	return inv.proceed();
    }

    DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler)metaObject.getValue("delegate.parameterHandler");
    Object paraObj = defaultParameterHandler.getParameterObject();
    PageDto pageDto = null;
    if ((paraObj instanceof PageDto))
      pageDto = (PageDto)paraObj;
    else if ((paraObj instanceof Map)) {
      Map paraMap = ((Map)paraObj);
      for (Object o : paraMap.entrySet()) {
    	  Map.Entry e = (Map.Entry) o;
        if ((e.getValue() instanceof PageDto)) {
          pageDto = (PageDto)e.getValue();
          break;
        }
      }
    }

    String srcSql = (String)metaObject.getValue("delegate.boundSql.sql");
    if (this.log.isDebugEnabled()) {
      this.log.debug("/********************************************/");
      this.log.debug("/******the src sql=[" + srcSql + "]*******/");
      this.log.debug("/********************************************/");
    }
    if ((srcSql == null) || (srcSql.toLowerCase().indexOf("select") < 0) || (pageDto == null)) {
      return inv.proceed();
    }
    Dialect.dbType dataBaseType = null;
    try {
      dataBaseType = Dialect.dbType.valueOf(dataBaseDialectType.toUpperCase());
    } catch (Exception e) {
      if (this.log.isDebugEnabled()) {
        this.log.debug("/**********==get key from config xml for dialect Exception:[" + e.getMessage() + "]===**********/");
      }
    }
    if (this.log.isDebugEnabled()) {
      this.log.debug("/*****the xml defined for dialect=[" + dataBaseType + "]*************/");
    }
    if (dataBaseType == null) {
      this.log.error("/************===the dialect is not defined in the configure XML and is null===*****************/");
      throw new RuntimeException("the dialect in the configure property is not defined:" + dataBaseType);
    }
    Dialect dialect = null;
    switch (dataBaseType) {
    case MYSQL:
      dialect = new MySqlDialect();
      break;
    case ORACLE:
      dialect = new OracleDialect();
      break;
    case SQLSERVER:
      dialect = new SqlServerDialect();
      break;
    case SQLSERVER2005:
      dialect = new SqlServer2005Dialect();
      break;
    default:
      dialect = new MySqlDialect();
    }
    metaObject.setValue("delegate.boundSql.sql", dialect.getLimitString(srcSql, pageDto.getRowOffset(), pageDto.getRowLimit()));
    metaObject.setValue("delegate.rowBounds.offset", Integer.valueOf(0));
    metaObject.setValue("delegate.rowBounds.limit", Integer.valueOf(2147483647));

    return inv.proceed();
  }

  public Object plugin(Object arg0)
  {
    return Plugin.wrap(arg0, this);
  }

  public void setProperties(Properties arg0)
  {
    dataBaseDialectType = arg0.getProperty("dialect");
  }
}