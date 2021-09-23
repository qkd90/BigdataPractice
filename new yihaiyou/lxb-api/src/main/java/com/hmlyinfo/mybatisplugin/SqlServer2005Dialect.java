package com.hmlyinfo.mybatisplugin;

public class SqlServer2005Dialect
  implements Dialect
{
  protected static final String SQL_END_DELIMITER = ";";
  private ThreadLocal<Boolean> supportsVariableLimit = new ThreadLocal();

  public SqlServer2005Dialect()
  {
    this.supportsVariableLimit.set(Boolean.valueOf(false));
  }

  private void setSupportsVariableLimit(boolean first) {
    this.supportsVariableLimit.set(Boolean.valueOf(first));
  }

  protected static int getSqlAfterSelectInsertPoint(String sql) {
    int selectIndex = sql.toLowerCase().indexOf("select");
    int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
    return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
  }

  public String getLimitString(String sql, boolean hasOffset, int offset, int limit) {
    int orderByIndex = sql.toLowerCase().lastIndexOf("order by");
    if (orderByIndex <= 0) {
      sql = sql + "ORDER BY CURRENT_TIMESTAMP";
      orderByIndex = sql.toLowerCase().lastIndexOf("order by");
    }
    int begin = offset;
    int end = offset + limit;
    String sqlOrderBy = sql.substring(orderByIndex + 8);
    String sqlRemoveOrderBy = sql.substring(0, orderByIndex);
    int insertPoint = getSqlAfterSelectInsertPoint(sql);
    return 
      new StringBuilder(") select * from tempPagination where RowNumber between ").append(begin).append(" and ").append(end).toString();
  }

  public String getLimitString(String sql, int offset, int limit)
  {
    setSupportsVariableLimit(offset > 0);
    if (offset == 0) {
      return new StringBuffer(sql.length() + 8).append(sql).insert(getSqlAfterSelectInsertPoint(sql), " top " + limit).toString();
    }
    return getLimitString(sql, offset > 0, offset, limit);
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