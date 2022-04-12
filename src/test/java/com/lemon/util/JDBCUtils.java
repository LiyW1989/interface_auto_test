package com.lemon.util;

import com.lemon.data.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2022-02-17 22:41
 * @Desc： sql工具类
 **/

public class JDBCUtils {
    public static Connection getConnection() {//定义数据库连接
//Oracle：jdbc:oracle:thin:@localhost:1521:DBName
//SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
//MySql：jdbc:mysql://localhost:3306/DBName
        String url ="jdbc:mysql://"+ Constants.DB_BASE_URI + Constants.DB_NAME +"?useUnicode=true&characterEncoding=utf-8";
        String user = Constants.DB_USERNAME;
        String password = Constants.DB_PWD;
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user,password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /*//测试用
    public  static void main(String[] args)  {
        //1、建立数据库连接
        Connection connection = getConnection();
        //2、实例化数据库操作对象
        QueryRunner queryRunner = new QueryRunner();
        //String sql_insert = "insert into member value(887854,'xx','13325q2543623634','13313151925',1,9999,'2022-02-17 23:02:00')";
        String sql_update = "update member set reg_name='shangz' where id = 887854";
        //3、对数据库进行更新操作
        *//*try {
            queryRunner.update(connection,sql_update);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*//*
        //4、查询数据库操作
        String sql_query = "select count(*) from member where id < 20;";
        try {
            //查询要有返回结果
            Long result = queryRunner.query(connection, sql_query, new ScalarHandler<Long>());
            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/

    /**
     * 关闭数据库连接
     * @param connection 数据库连接对象
     */
        public static void closeConnection(Connection connection){
            //判空
            if (connection != null) {
                //关闭数据库连接
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    /**
     * Sql 的更新操作（包括了增加+修改+删除）
     * @param sql   要执行的sql语句
     */
    public static void update(String sql){
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            queryRunner.update(connection,sql);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭数据库连接
            closeConnection(connection);
        }
    }

    /**
     * Sql的查询操作，查询所有的结果集
     * @param sql 要执行的sql语句
     * @retrun 返回的结果集
     */
    public static List<Map<String, Object>> queryAll(String sql){
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        List<Map<String,Object>> result = null;
        try {
            result = queryRunner.query(connection,sql,new MapListHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭数据库连接
            closeConnection(connection);
        }
        return result;
    }

    /**
     * 查询结果集中的第一条
     * @param sql 要执行的sql语句
     * @retrun 返回的结果集
     */
    public static Map<String,Object> queryOne(String sql){
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        Map<String,Object> result = null;
        try {
            result = queryRunner.query(connection,sql,new MapHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭数据库连接
            closeConnection(connection);
        }
        return result;
    }

    /**
     * 查询单条的数据
     * @param sql 要执行的sql语句
     * @retrun 返回的结果集
     */
    public static Object querySingleData(String sql){
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        Object result = null;
        try {
            result = queryRunner.query(connection,sql,new ScalarHandler<Object>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭数据库连接
            closeConnection(connection);
        }
        return result;
    }
}
























