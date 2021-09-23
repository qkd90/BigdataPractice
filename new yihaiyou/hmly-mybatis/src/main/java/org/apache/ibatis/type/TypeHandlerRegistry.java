package org.apache.ibatis.type;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.io.ResolverUtil;

public final class TypeHandlerRegistry
{
  private static final Map<Class<?>, Class<?>> reversePrimitiveMap = new HashMap() { } ;

  private final Map<JdbcType, TypeHandler> JDBC_TYPE_HANDLER_MAP = new EnumMap(JdbcType.class);
  private final Map<Class<?>, Map<JdbcType, TypeHandler>> TYPE_HANDLER_MAP = new HashMap();
  private final TypeHandler UNKNOWN_TYPE_HANDLER = new UnknownTypeHandler(this);

  public TypeHandlerRegistry() {
    register(Boolean.class, new BooleanTypeHandler());
    register(Boolean.TYPE, new BooleanTypeHandler());
    register(JdbcType.BOOLEAN, new BooleanTypeHandler());
    register(JdbcType.BIT, new BooleanTypeHandler());

    register(Byte.class, new ByteTypeHandler());
    register(Byte.TYPE, new ByteTypeHandler());
    register(JdbcType.TINYINT, new ByteTypeHandler());

    register(Short.class, new ShortTypeHandler());
    register(Short.TYPE, new ShortTypeHandler());
    register(JdbcType.SMALLINT, new ShortTypeHandler());

    register(Integer.class, new IntegerTypeHandler());
    register(Integer.TYPE, new IntegerTypeHandler());
    register(JdbcType.INTEGER, new IntegerTypeHandler());

    register(Long.class, new LongTypeHandler());
    register(Long.TYPE, new LongTypeHandler());

    register(Float.class, new FloatTypeHandler());
    register(Float.TYPE, new FloatTypeHandler());
    register(JdbcType.FLOAT, new FloatTypeHandler());

    register(Double.class, new DoubleTypeHandler());
    register(Double.TYPE, new DoubleTypeHandler());
    register(JdbcType.DOUBLE, new DoubleTypeHandler());

    register(String.class, new StringTypeHandler());
    register(String.class, JdbcType.CHAR, new StringTypeHandler());
    register(String.class, JdbcType.CLOB, new ClobTypeHandler());
    register(String.class, JdbcType.VARCHAR, new StringTypeHandler());
    register(String.class, JdbcType.LONGVARCHAR, new ClobTypeHandler());
    register(String.class, JdbcType.NVARCHAR, new NStringTypeHandler());
    register(String.class, JdbcType.NCHAR, new NStringTypeHandler());
    register(String.class, JdbcType.NCLOB, new NClobTypeHandler());
    register(JdbcType.CHAR, new StringTypeHandler());
    register(JdbcType.VARCHAR, new StringTypeHandler());
    register(JdbcType.CLOB, new ClobTypeHandler());
    register(JdbcType.LONGVARCHAR, new ClobTypeHandler());
    register(JdbcType.NVARCHAR, new NStringTypeHandler());
    register(JdbcType.NCHAR, new NStringTypeHandler());
    register(JdbcType.NCLOB, new NClobTypeHandler());

    register(BigInteger.class, new BigIntegerTypeHandler());
    register(JdbcType.BIGINT, new LongTypeHandler());

    register(BigDecimal.class, new BigDecimalTypeHandler());
    register(JdbcType.REAL, new BigDecimalTypeHandler());
    register(JdbcType.DECIMAL, new BigDecimalTypeHandler());
    register(JdbcType.NUMERIC, new BigDecimalTypeHandler());

    register(Byte.class, new ByteArrayTypeHandler());
    register(Byte.class, JdbcType.BLOB, new BlobTypeHandler());
    register(Byte.class, JdbcType.LONGVARBINARY, new BlobTypeHandler());
    register(JdbcType.LONGVARBINARY, new BlobTypeHandler());
    register(JdbcType.BLOB, new BlobTypeHandler());

    register(Object.class, this.UNKNOWN_TYPE_HANDLER);
    register(Object.class, JdbcType.OTHER, this.UNKNOWN_TYPE_HANDLER);
    register(JdbcType.OTHER, this.UNKNOWN_TYPE_HANDLER);

    register(java.util.Date.class, new DateTypeHandler());
    register(java.util.Date.class, JdbcType.DATE, new DateOnlyTypeHandler());
    register(java.util.Date.class, JdbcType.TIME, new TimeOnlyTypeHandler());
    
    register(JdbcType.DATE, new DateOnlyTypeHandler());
    register(JdbcType.TIME, new TimeOnlyTypeHandler());

    register(java.sql.Date.class, new SqlDateTypeHandler());
    register(Time.class, new SqlTimeTypeHandler());
    register(Timestamp.class, new SqlTimestampTypeHandler());
    // 这里针对自定义类型做了一个定制，将时间戳输出为long
//    register(com.hmlyinfo.base.TimestampLong.class, new TimestampTypeHandler());
  }

  public boolean hasTypeHandler(Class<?> javaType) {
    return hasTypeHandler(javaType, null);
  }

  public boolean hasTypeHandler(Class<?> javaType, JdbcType jdbcType) {
    return (javaType != null) && (getTypeHandler(javaType, jdbcType) != null);
  }

  public TypeHandler getTypeHandler(Class<?> type) {
    return getTypeHandler(type, null);
  }

  public TypeHandler getTypeHandler(JdbcType jdbcType) {
    return (TypeHandler)this.JDBC_TYPE_HANDLER_MAP.get(jdbcType);
  }

  public TypeHandler getTypeHandler(Class<?> type, JdbcType jdbcType) {
    Map jdbcHandlerMap = (Map)this.TYPE_HANDLER_MAP.get(type);
    TypeHandler handler = null;
    if (jdbcHandlerMap != null) {
      handler = (TypeHandler)jdbcHandlerMap.get(jdbcType);
      if (handler == null) {
        handler = (TypeHandler)jdbcHandlerMap.get(null);
      }
    }
    if ((handler == null) && (type != null) && (Enum.class.isAssignableFrom(type))) {
      handler = new EnumTypeHandler(type);
    }
    return handler;
  }

  public TypeHandler getUnknownTypeHandler() {
    return this.UNKNOWN_TYPE_HANDLER;
  }

  public void register(JdbcType jdbcType, TypeHandler handler) {
    this.JDBC_TYPE_HANDLER_MAP.put(jdbcType, handler);
  }

  public void register(Class<?> type, TypeHandler handler) {
    MappedJdbcTypes mappedJdbcTypes = (MappedJdbcTypes)handler.getClass().getAnnotation(MappedJdbcTypes.class);
    if (mappedJdbcTypes != null) {
      for (JdbcType handledJdbcType : mappedJdbcTypes.value())
        register(type, handledJdbcType, handler);
    }
    else
      register(type, null, handler);
  }

  public void register(TypeHandler handler)
  {
    boolean mappedTypeFound = false;
    MappedTypes mappedTypes = (MappedTypes)handler.getClass().getAnnotation(MappedTypes.class);
    if (mappedTypes != null) {
      for (Class handledType : mappedTypes.value()) {
        register(handledType, handler);
        mappedTypeFound = true;
      }
    }
    if (!mappedTypeFound)
      throw new RuntimeException("Unable to get mapped types, check @MappedTypes annotation for type handler " + handler);
  }

  public void register(Class<?> type, JdbcType jdbcType, TypeHandler handler)
  {
    Map map = (Map)this.TYPE_HANDLER_MAP.get(type);
    if (map == null) {
      map = new HashMap();
      this.TYPE_HANDLER_MAP.put(type, map);
    }
    map.put(jdbcType, handler);
    if (reversePrimitiveMap.containsKey(type))
      register((Class)reversePrimitiveMap.get(type), jdbcType, handler);
  }

  public void register(String packageName)
  {
    ResolverUtil resolverUtil = new ResolverUtil();
    resolverUtil.find(new ResolverUtil.IsA(TypeHandler.class), packageName);
    Set<Class> handlerSet = resolverUtil.getClasses();
    for (Class type : handlerSet)
    {
      if ((!type.isAnonymousClass()) && (!type.isInterface()) && (!Modifier.isAbstract(type.getModifiers())))
        try {
          TypeHandler handler = (TypeHandler)type.getConstructor(new Class[0]).newInstance(new Object[0]);
          register(handler);
        } catch (Exception e) {
          throw new RuntimeException("Unable to find a usable constructor for " + type, e);
        }
    }
  }
}