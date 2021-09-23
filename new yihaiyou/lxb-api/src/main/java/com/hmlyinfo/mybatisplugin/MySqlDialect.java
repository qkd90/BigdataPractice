package com.hmlyinfo.mybatisplugin;

public class MySqlDialect
  implements Dialect
{
  protected static final String SQL_END_DELIMITER = ";";

  protected String trim(String sql)
  {
    String trimedSql = sql.trim();
    if (trimedSql.endsWith(";")) {
      trimedSql = trimedSql.substring(0, trimedSql.length() - ";".length());
    }
    return trimedSql;
  }

  public String getLimitString(String sql, int offset, int limit)
  {
    String trimedSql = trim(sql);
    if ((offset < 0) || (limit <= 0)) {
      return trimedSql;
    }
    StringBuffer sb = new StringBuffer(trimedSql.length() + 20);
    sb.append(trimedSql).append(" limit " + offset + "," + limit).append(";");
    return sb.toString();
  }

  public boolean supportsLimit()
  {
    return true;
  }

  public boolean supportsOrder()
  {
    return false;
  }
}