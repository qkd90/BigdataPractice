package com.hmlyinfo.mybatisplugin;

public abstract interface Dialect
{
  public abstract boolean supportsLimit();

  public abstract String getLimitString(String paramString, int paramInt1, int paramInt2);

  public abstract boolean supportsOrder();

  public static enum dbType
  {
    MYSQL, 
    ORACLE, 
    SQLSERVER, 
    SQLSERVER2005;
  }
}