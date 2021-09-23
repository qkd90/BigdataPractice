package org.apache.ibatis.executor.resultset;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.loader.ResultLoader;
import org.apache.ibatis.executor.loader.ResultLoaderMap;
import org.apache.ibatis.executor.loader.ResultObjectProxy;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.result.DefaultResultContext;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Discriminator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

public class FastResultSetHandler
  implements ResultSetHandler
{
  protected final Executor executor;
  protected final Configuration configuration;
  protected final MappedStatement mappedStatement;
  protected final RowBounds rowBounds;
  protected final ParameterHandler parameterHandler;
  protected final ResultHandler resultHandler;
  protected final BoundSql boundSql;
  protected final TypeHandlerRegistry typeHandlerRegistry;
  protected final ObjectFactory objectFactory;

  public FastResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql, RowBounds rowBounds)
  {
    this.executor = executor;
    this.configuration = mappedStatement.getConfiguration();
    this.mappedStatement = mappedStatement;
    this.rowBounds = rowBounds;
    this.parameterHandler = parameterHandler;
    this.boundSql = boundSql;
    this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    this.objectFactory = this.configuration.getObjectFactory();
    this.resultHandler = resultHandler;
  }

  public void handleOutputParameters(CallableStatement cs)
    throws SQLException
  {
    Object parameterObject = this.parameterHandler.getParameterObject();
    MetaObject metaParam = this.configuration.newMetaObject(parameterObject);
    List parameterMappings = this.boundSql.getParameterMappings();
    for (int i = 0; i < parameterMappings.size(); i++) {
      ParameterMapping parameterMapping = (ParameterMapping)parameterMappings.get(i);
      if ((parameterMapping.getMode() == ParameterMode.OUT) || (parameterMapping.getMode() == ParameterMode.INOUT))
        if ("java.sql.ResultSet".equalsIgnoreCase(parameterMapping.getJavaType().getName())) {
          handleRefCursorOutputParameter(cs, parameterMapping, i, metaParam);
        } else {
          TypeHandler typeHandler = parameterMapping.getTypeHandler();
          if (typeHandler == null) {
            throw new ExecutorException("Type handler was null on parameter mapping for property " + parameterMapping.getProperty() + ".  " + "It was either not specified and/or could not be found for the javaType / jdbcType combination specified.");
          }

          metaParam.setValue(parameterMapping.getProperty(), typeHandler.getResult(cs, i + 1));
        }
    }
  }

  protected void handleRefCursorOutputParameter(CallableStatement cs, ParameterMapping parameterMapping, int parameterMappingIndex, MetaObject metaParam) throws SQLException
  {
    ResultSet rs = (ResultSet)cs.getObject(parameterMappingIndex + 1);
    String resultMapId = parameterMapping.getResultMapId();
    if (resultMapId != null) {
      ResultMap resultMap = this.configuration.getResultMap(resultMapId);
      DefaultResultHandler resultHandler = new DefaultResultHandler();
      handleRowValues(rs, resultMap, resultHandler, new RowBounds());
      metaParam.setValue(parameterMapping.getProperty(), resultHandler.getResultList());
    } else {
      throw new ExecutorException("Parameter requires ResultMap for output types of java.sql.ResultSet");
    }
    rs.close();
  }

  public List handleResultSets(Statement stmt)
    throws SQLException
  {
    List multipleResults = new ArrayList();
    List resultMaps = this.mappedStatement.getResultMaps();
    int resultMapCount = resultMaps.size();
    int resultSetCount = 0;
    ResultSet rs = stmt.getResultSet();

    while (rs == null)
    {
      if (stmt.getMoreResults()) {
        rs = stmt.getResultSet(); continue;
      }
      if (stmt.getUpdateCount() != -1)
      {
        continue;
      }

    }

    validateResultMapsCount(rs, resultMapCount);
    while ((rs != null) && (resultMapCount > resultSetCount)) {
      ResultMap resultMap = (ResultMap)resultMaps.get(resultSetCount);
      handleResultSet(rs, resultMap, multipleResults);
      rs = getNextResultSet(stmt);
      cleanUpAfterHandlingResultSet();
      resultSetCount++;
    }
    return collapseSingleResultList(multipleResults);
  }

  protected void closeResultSet(ResultSet rs) {
    try {
      if (rs != null)
        rs.close();
    }
    catch (SQLException e)
    {
    }
  }

  protected void cleanUpAfterHandlingResultSet() {
  }

  protected void validateResultMapsCount(ResultSet rs, int resultMapCount) {
    if ((rs != null) && (resultMapCount < 1))
      throw new ExecutorException("A query was run and no Result Maps were found for the Mapped Statement '" + this.mappedStatement.getId() + "'.  It's likely that neither a Result Type nor a Result Map was specified.");
  }

  protected void handleResultSet(ResultSet rs, ResultMap resultMap, List multipleResults)
    throws SQLException
  {
    try
    {
      if (this.resultHandler == null) {
        DefaultResultHandler defaultResultHandler = new DefaultResultHandler();
        handleRowValues(rs, resultMap, defaultResultHandler, this.rowBounds);
        multipleResults.add(defaultResultHandler.getResultList());
      } else {
        handleRowValues(rs, resultMap, this.resultHandler, this.rowBounds);
      }
    } finally {
      closeResultSet(rs);
    }
  }

  protected List collapseSingleResultList(List multipleResults) {
    if (multipleResults.size() == 1) {
      return (List)multipleResults.get(0);
    }
    return multipleResults;
  }

  protected void handleRowValues(ResultSet rs, ResultMap resultMap, ResultHandler resultHandler, RowBounds rowBounds)
    throws SQLException
  {
    DefaultResultContext resultContext = new DefaultResultContext();
    skipRows(rs, rowBounds);
    while (shouldProcessMoreRows(rs, resultContext, rowBounds)) {
      ResultMap discriminatedResultMap = resolveDiscriminatedResultMap(rs, resultMap);
      Object rowValue = getRowValue(rs, discriminatedResultMap, null);
      resultContext.nextResultObject(rowValue);
      resultHandler.handleResult(resultContext);
    }
  }

  protected boolean shouldProcessMoreRows(ResultSet rs, ResultContext context, RowBounds rowBounds) throws SQLException {
    return (rs.next()) && (context.getResultCount() < rowBounds.getLimit()) && (!context.isStopped());
  }

  protected void skipRows(ResultSet rs, RowBounds rowBounds) throws SQLException {
    if (rs.getType() != 1003) {
      if (rowBounds.getOffset() != 0)
        rs.absolute(rowBounds.getOffset());
    }
    else
      for (int i = 0; i < rowBounds.getOffset(); i++) rs.next(); 
  }

  protected ResultSet getNextResultSet(Statement stmt)
    throws SQLException
  {
    try
    {
      if (stmt.getConnection().getMetaData().supportsMultipleResultSets())
      {
        if ((stmt.getMoreResults()) || (stmt.getUpdateCount() != -1))
          return stmt.getResultSet();
      }
    }
    catch (Exception e)
    {
    }
    return null;
  }

  protected Object getRowValue(ResultSet rs, ResultMap resultMap, CacheKey rowKey)
    throws SQLException
  {
    List mappedColumnNames = new ArrayList();
    List unmappedColumnNames = new ArrayList();
    ResultLoaderMap lazyLoader = instantiateResultLoaderMap();
    Object resultObject = createResultObject(rs, resultMap, lazyLoader);
    if ((resultObject != null) && (!this.typeHandlerRegistry.hasTypeHandler(resultMap.getType()))) 
    {
      MetaObject metaObject = this.configuration.newMetaObject(resultObject);
      loadMappedAndUnmappedColumnNames(rs, resultMap, mappedColumnNames, unmappedColumnNames);
      boolean foundValues = resultMap.getConstructorResultMappings().size() > 0;
      if (!AutoMappingBehavior.NONE.equals(this.configuration.getAutoMappingBehavior())) {
        foundValues = (applyAutomaticMappings(rs, unmappedColumnNames, metaObject)) || (foundValues);
      }
      foundValues = (applyPropertyMappings(rs, resultMap, mappedColumnNames, metaObject, lazyLoader)) || (foundValues);
      foundValues = ((lazyLoader != null) && (lazyLoader.size() > 0)) || (foundValues);
      resultObject = foundValues ? resultObject : null;
      return resultObject;
    }
    return resultObject;
  }

  protected ResultLoaderMap instantiateResultLoaderMap() {
    if (this.configuration.isLazyLoadingEnabled()) {
      return new ResultLoaderMap();
    }
    return null;
  }

  protected boolean applyPropertyMappings(ResultSet rs, ResultMap resultMap, List<String> mappedColumnNames, MetaObject metaObject, ResultLoaderMap lazyLoader)
    throws SQLException
  {
    boolean foundValues = false;
    List<ResultMapping> propertyMappings = resultMap.getPropertyResultMappings();
    for (ResultMapping propertyMapping : propertyMappings) {
      String column = propertyMapping.getColumn();
      if ((propertyMapping.isCompositeResult()) || ((column != null) && (mappedColumnNames.contains(column.toUpperCase(Locale.ENGLISH))))) {
        Object value = getPropertyMappingValue(rs, metaObject, propertyMapping, lazyLoader);
        if (value != null) {
          String property = propertyMapping.getProperty();
          metaObject.setValue(property, value);
          foundValues = true;
        }
      }
    }
    return foundValues;
  }

  protected Object getPropertyMappingValue(ResultSet rs, MetaObject metaResultObject, ResultMapping propertyMapping, ResultLoaderMap lazyLoader) throws SQLException {
    TypeHandler typeHandler = propertyMapping.getTypeHandler();
    if (propertyMapping.getNestedQueryId() != null)
      return getNestedQueryMappingValue(rs, metaResultObject, propertyMapping, lazyLoader);
    if (typeHandler != null) {
      String column = propertyMapping.getColumn();
      return typeHandler.getResult(rs, column);
    }
    return null;
  }

  protected boolean applyAutomaticMappings(ResultSet rs, List<String> unmappedColumnNames, MetaObject metaObject) throws SQLException {
    boolean foundValues = false;
    for (String columnName : unmappedColumnNames) {
      String property = metaObject.findProperty(columnName);
      if (property != null) {
        Class propertyType = metaObject.getSetterType(property);
        if (this.typeHandlerRegistry.hasTypeHandler(propertyType)) {
          TypeHandler typeHandler = this.typeHandlerRegistry.getTypeHandler(propertyType);
          Object value = typeHandler.getResult(rs, columnName);
          if (value != null) {
            metaObject.setValue(property, value);
            foundValues = true;
          }
        }
      }
    }
    return foundValues;
  }

  protected void loadMappedAndUnmappedColumnNames(ResultSet rs, ResultMap resultMap, List<String> mappedColumnNames, List<String> unmappedColumnNames) throws SQLException {
    mappedColumnNames.clear();
    unmappedColumnNames.clear();
    ResultSetMetaData rsmd = rs.getMetaData();
    int columnCount = rsmd.getColumnCount();
    Set mappedColumns = resultMap.getMappedColumns();
    for (int i = 1; i <= columnCount; i++) {
      String columnName = this.configuration.isUseColumnLabel() ? rsmd.getColumnLabel(i) : rsmd.getColumnName(i);
      String upperColumnName = columnName.toUpperCase(Locale.ENGLISH);
      if (mappedColumns.contains(upperColumnName)) {
        mappedColumnNames.add(upperColumnName);
        mappedColumnNames.add(columnName);
      } else {
        unmappedColumnNames.add(upperColumnName);
        unmappedColumnNames.add(columnName);
      }
    }
  }

  protected Object createResultObject(ResultSet rs, ResultMap resultMap, ResultLoaderMap lazyLoader)
    throws SQLException
  {
    List constructorArgTypes = new ArrayList();
    List constructorArgs = new ArrayList();
    Object resultObject = createResultObject(rs, resultMap, constructorArgTypes, constructorArgs);
    if ((resultObject != null) && (this.configuration.isLazyLoadingEnabled())) {
      return ResultObjectProxy.createProxy(resultObject, lazyLoader, this.configuration.isAggressiveLazyLoading(), this.objectFactory, constructorArgTypes, constructorArgs);
    }
    return resultObject;
  }

  protected Object createResultObject(ResultSet rs, ResultMap resultMap, List<Class> constructorArgTypes, List<Object> constructorArgs) throws SQLException
  {
    Class resultType = resultMap.getType();
    List constructorMappings = resultMap.getConstructorResultMappings();
    if (this.typeHandlerRegistry.hasTypeHandler(resultType))
      return createPrimitiveResultObject(rs, resultMap);
    if (constructorMappings.size() > 0) {
      return createParameterizedResultObject(rs, resultType, constructorMappings, constructorArgTypes, constructorArgs);
    }
    return this.objectFactory.create(resultType);
  }

  protected Object createParameterizedResultObject(ResultSet rs, Class resultType, List<ResultMapping> constructorMappings, List<Class> constructorArgTypes, List<Object> constructorArgs)
    throws SQLException
  {
    boolean foundValues = false;
    for (ResultMapping constructorMapping : constructorMappings) {
      Class parameterType = constructorMapping.getJavaType();
      TypeHandler typeHandler = constructorMapping.getTypeHandler();
      String column = constructorMapping.getColumn();
      Object value = typeHandler.getResult(rs, column);
      constructorArgTypes.add(parameterType);
      constructorArgs.add(value);
      foundValues = (value != null) || (foundValues);
    }
    return foundValues ? this.objectFactory.create(resultType, constructorArgTypes, constructorArgs) : null;
  }

  protected Object createPrimitiveResultObject(ResultSet rs, ResultMap resultMap) throws SQLException {
    Class resultType = resultMap.getType();
    String columnName;
    if (resultMap.getResultMappings().size() > 0) {
      List resultMappingList = resultMap.getResultMappings();
      ResultMapping mapping = (ResultMapping)resultMappingList.get(0);
      columnName = mapping.getColumn();
    } else {
      ResultSetMetaData rsmd = rs.getMetaData();
      columnName = this.configuration.isUseColumnLabel() ? rsmd.getColumnLabel(1) : rsmd.getColumnName(1);
    }
    TypeHandler typeHandler = this.typeHandlerRegistry.getTypeHandler(resultType);
    return typeHandler.getResult(rs, columnName);
  }

  protected Object getNestedQueryMappingValue(ResultSet rs, MetaObject metaResultObject, ResultMapping propertyMapping, ResultLoaderMap lazyLoader)
    throws SQLException
  {
    String nestedQueryId = propertyMapping.getNestedQueryId();
    String property = propertyMapping.getProperty();
    MappedStatement nestedQuery = this.configuration.getMappedStatement(nestedQueryId);
    Class nestedQueryParameterType = nestedQuery.getParameterMap().getType();
    Object nestedQueryParameterObject = prepareParameterForNestedQuery(rs, propertyMapping, nestedQueryParameterType);
    Object value = null;
    if (nestedQueryParameterObject != null) {
      CacheKey key = this.executor.createCacheKey(nestedQuery, nestedQueryParameterObject, RowBounds.DEFAULT);
      if (this.executor.isCached(nestedQuery, key)) {
        this.executor.deferLoad(nestedQuery, metaResultObject, property, key);
      } else {
        ResultLoader resultLoader = new ResultLoader(this.configuration, this.executor, nestedQuery, nestedQueryParameterObject, propertyMapping.getJavaType());
        if (this.configuration.isLazyLoadingEnabled())
          lazyLoader.addLoader(property, metaResultObject, resultLoader);
        else {
          value = resultLoader.loadResult();
        }
      }
    }
    return value;
  }

  protected Object prepareParameterForNestedQuery(ResultSet rs, ResultMapping resultMapping, Class parameterType) throws SQLException {
    if (resultMapping.isCompositeResult()) {
      return prepareCompositeKeyParameter(rs, resultMapping, parameterType);
    }
    return prepareSimpleKeyParameter(rs, resultMapping, parameterType);
  }

  protected Object prepareSimpleKeyParameter(ResultSet rs, ResultMapping resultMapping, Class parameterType)
    throws SQLException
  {
    TypeHandler typeHandler;
    if (this.typeHandlerRegistry.hasTypeHandler(parameterType))
      typeHandler = this.typeHandlerRegistry.getTypeHandler(parameterType);
    else {
      typeHandler = this.typeHandlerRegistry.getUnknownTypeHandler();
    }
    return typeHandler.getResult(rs, resultMapping.getColumn());
  }

  protected Object prepareCompositeKeyParameter(ResultSet rs, ResultMapping resultMapping, Class parameterType) throws SQLException {
    Object parameterObject = instantiateParameterObject(parameterType);
    MetaObject metaObject = this.configuration.newMetaObject(parameterObject);
    for (ResultMapping innerResultMapping : resultMapping.getComposites()) {
      Class propType = metaObject.getSetterType(innerResultMapping.getProperty());
      TypeHandler typeHandler = this.typeHandlerRegistry.getTypeHandler(propType);
      Object propValue = typeHandler.getResult(rs, innerResultMapping.getColumn());
      metaObject.setValue(innerResultMapping.getProperty(), propValue);
    }
    return parameterObject;
  }

  protected Object instantiateParameterObject(Class parameterType) {
    if (parameterType == null) {
      return new HashMap();
    }
    return this.objectFactory.create(parameterType);
  }

  public ResultMap resolveDiscriminatedResultMap(ResultSet rs, ResultMap resultMap)
    throws SQLException
  {
    Set pastDiscriminators = new HashSet();
    Discriminator discriminator = resultMap.getDiscriminator();
    while (discriminator != null) {
      Object value = getDiscriminatorValue(rs, discriminator);
      String discriminatedMapId = discriminator.getMapIdFor(String.valueOf(value));
      if (!this.configuration.hasResultMap(discriminatedMapId)) break;
      resultMap = this.configuration.getResultMap(discriminatedMapId);
      Discriminator lastDiscriminator = discriminator;
      discriminator = resultMap.getDiscriminator();
      if ((discriminator == lastDiscriminator) || (!pastDiscriminators.add(discriminatedMapId)))
      {
        break;
      }

    }

    return resultMap;
  }

  protected Object getDiscriminatorValue(ResultSet rs, Discriminator discriminator) throws SQLException {
    ResultMapping resultMapping = discriminator.getResultMapping();
    TypeHandler typeHandler = resultMapping.getTypeHandler();
    if (typeHandler != null) {
      return typeHandler.getResult(rs, resultMapping.getColumn());
    }
    throw new ExecutorException("No type handler could be found to map the property '" + resultMapping.getProperty() + "' to the column '" + resultMapping.getColumn() + "'.  One or both of the types, or the combination of types is not supported.");
  }
}