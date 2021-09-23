package com.hmlyinfo.mybatisplugin;

public class SqlServerDialect
  implements Dialect
{
  public String getLimitString(String sql, int offset, int limit)
  {
    return null;
  }

  public boolean supportsLimit()
  {
    return false;
  }

  public boolean supportsOrder()
  {
    return false;
  }
}