package com.data.spider.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/5/21.
 */
public class DBUtil {

    private static Logger logger = Logger.getLogger(DBUtil.class);

    private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://110.80.136.201:3306/soutuu_server?useUnicode=true&amp;characterEncoding=UTF-8";
    private static String username = "soutuu_server";
    private static String password = "Hmly2014serveromg";


    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(url,
                    username, password);
        } catch (ClassNotFoundException e) {
            logger.error("绫诲姞杞藉け璐�", e);
            e.printStackTrace();
        } catch (SQLException e) {
            logger.error("鑾峰彇杩炴帴澶辫触", e);
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map<String, Object>> query(String sql, List<Object> values) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> result = Lists.newArrayList();
            while (resultSet.next()) {
                Map<String, Object> map = Maps.newHashMap();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int count = metaData.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    map.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                result.add(map);
            }
            preparedStatement.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean execute(String sql) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return false;
    }

}
