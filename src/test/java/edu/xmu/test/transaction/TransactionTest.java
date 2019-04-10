package edu.xmu.test.transaction;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 * Created by kunlun.ykl on 2019/3/21.
 */
public class TransactionTest {

    public static void main(String[] args) {
        String sql = "update vm set can_recover=1, status='1', gmt_modify=now() where name=\"BVT-Resize-S-io7-04zowdvc5cyk8g1vwzsr\"";
        final MyTransactionManager transactionManager = new MyTransactionManager();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    transactionManager.begin();
                    transactionManager.execute(sql);
                    transactionManager.commit();
                } catch (NotSupportedException e) {
                    e.printStackTrace();
                } catch (SystemException e) {
                    e.printStackTrace();
                } catch (HeuristicMixedException e) {
                    e.printStackTrace();
                } catch (HeuristicRollbackException e) {
                    e.printStackTrace();
                } catch (RollbackException e) {
                    e.printStackTrace();
                }
            }
        };

        String sql2 = "update vm set can_recover=0, status='1', gmt_modify=now() where name=\"BVT-Resize-S-io7-04zowdvc5cyk8g1vwzsr\"";
        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    transactionManager.begin();
                    transactionManager.execute(sql2);
                    transactionManager.commit();
                } catch (NotSupportedException e) {
                    e.printStackTrace();
                } catch (SystemException e) {
                    e.printStackTrace();
                } catch (HeuristicMixedException e) {
                    e.printStackTrace();
                } catch (HeuristicRollbackException e) {
                    e.printStackTrace();
                } catch (RollbackException e) {
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        t2.start();
    }

}

