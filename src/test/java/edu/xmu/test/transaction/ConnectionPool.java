package edu.xmu.test.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Created by kunlun.ykl on 2019/4/10.
 */
public class ConnectionPool  {
    private GenericObjectPool<Connection> pool;

    public ConnectionPool() {
        PoolableObjectFactory<Connection> connFactory = new PoolableObjectFactory<Connection>() {
            @Override
            public Connection makeObject() throws Exception {
                return initConnection();
            }

            @Override
            public void destroyObject(Connection connection) throws Exception {
                connection.close();
            }

            @Override
            public boolean validateObject(Connection connection) {
                try {
                    return connection.isValid(10);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void activateObject(Connection connection) throws Exception {
                connection.setAutoCommit(false);
            }

            @Override
            public void passivateObject(Connection connection) throws Exception {
            }
        };
        pool =  new GenericObjectPool(connFactory);
    }

    /**
     * 开启事务时获取到连接
     * @return
     */
    public Connection getConnection() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交/回滚事务时归还连接
     * @param conn
     */
    public void returnConnection(Connection conn) {
        try {
            pool.returnObject(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection initConnection() {
        String url = "jdbc:mysql://localhost:3306/houyiregiondb";
        String user = "root";
        String password = "KunLun0519@sohu";
        String driverClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverClass);
            Properties info = new Properties();
            info.setProperty("user", user);
            info.setProperty("password", password);
            return DriverManager.getConnection(url, info);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
