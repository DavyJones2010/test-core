package edu.xmu.test.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

/**
 * Created by kunlun.ykl on 2019/3/21.
 */
public class MyTransaction implements Transaction {
    Connection conn;

    public MyTransaction(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void commit() {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delistResource(XAResource xaRes, int flag) throws IllegalStateException, SystemException {
        return false;
    }

    @Override
    public boolean enlistResource(XAResource xaRes) throws RollbackException, IllegalStateException, SystemException {
        return false;
    }

    @Override
    public int getStatus() throws SystemException {
        try {
            if(conn.isValid(10)) {
                return Status.STATUS_ACTIVE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Status.STATUS_UNKNOWN;
    }

    @Override
    public void registerSynchronization(Synchronization sync)
        throws RollbackException, IllegalStateException, SystemException {

    }

    public void execute(String sql) {
        try {
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() throws IllegalStateException, SystemException {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRollbackOnly() throws IllegalStateException, SystemException {

    }

}
