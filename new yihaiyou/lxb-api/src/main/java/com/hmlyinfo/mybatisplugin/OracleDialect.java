package com.hmlyinfo.mybatisplugin;

public class OracleDialect
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

  public boolean supportsLimit() {
    return true;
  }

  public String getLimitString(String sql, int offset, int limit)
  {
    String trimedSql = trim(sql);
    if ((offset < 0) || (limit <= 0)) {
      return trimedSql;
    }
    StringBuffer sb = new StringBuffer(trimedSql.length() + 20);
    sb.append("select * from ( select row_limit.*, rownum rownum_ from (");
    sb.append(trimedSql);
    sb.append(" ) row_limit where rownum <= ");
    sb.append(limit + offset);
    sb.append(" ) where rownum_ >");
    sb.append(offset);
    return sb.toString();
  }

  public boolean supportsOrder()
  {
    return false;
  }
}