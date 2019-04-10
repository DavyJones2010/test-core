package edu.xmu.test.transaction;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

/**
 * Created by kunlun.ykl on 2019/3/21.
 */
public class MyTransactionManager implements TransactionManager {
    ThreadLocal<MyTransaction> localTransaction = new ThreadLocal<>();
    ConnectionPool pool = new ConnectionPool();

    @Override
    public void begin() throws NotSupportedException, SystemException {
        MyTransaction myTransaction = localTransaction.get();
        if(null == myTransaction) {
            localTransaction.set(new MyTransaction(pool.getConnection()));
        }
    }

    @Override
    public void commit()
        throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException,
        IllegalStateException, SystemException {
        localTransaction.get().commit();
    }

    // FIXME: find better place for execute
    public void execute(String sql) {
        localTransaction.get().execute(sql);
    }
    @Override
    public int getStatus() throws SystemException {
        return 0;
    }

    @Override
    public javax.transaction.Transaction getTransaction() throws SystemException {
        return localTransaction.get();
    }

    @Override
    public void resume(javax.transaction.Transaction tobj)
        throws InvalidTransactionException, IllegalStateException, SystemException {
    }

    @Override
    public void rollback() throws IllegalStateException, SecurityException, SystemException {
        localTransaction.get().rollback();
    }

    @Override
    public void setRollbackOnly() throws IllegalStateException, SystemException {
        localTransaction.get().setRollbackOnly();
    }

    @Override
    public void setTransactionTimeout(int seconds) throws SystemException {
    }

    @Override
    public javax.transaction.Transaction suspend() throws SystemException {
        return null;
    }
}
