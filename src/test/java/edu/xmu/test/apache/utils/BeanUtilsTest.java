package edu.xmu.test.apache.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

/**
 * Created by kunlun.ykl on 2018/10/15.
 */
public class BeanUtilsTest {
    static class C {
        private Date createDate;
        private String name;
        private String bSpecial;
        private Long no;

        public C() {
        }

        public C(Date createDate, String name, Long no) {
            this.createDate = createDate;
            this.name = name;
            this.no = no;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getNo() {
            return no;
        }

        public void setNo(Long no) {
            this.no = no;
        }

        public String getbSpecial() {
            return bSpecial;
        }

        public void setbSpecial(String bSpecial) {
            this.bSpecial = bSpecial;
        }
    }

    static class A {
        private String name;
        private String aSpecial;
        private Date createDate;
        private int score;
        private Long no;
        private C c;

        public A() {
        }

        public A(String name, Date createDate, int score, Long no, C c) {
            this.name = name;
            this.createDate = createDate;
            this.score = score;
            this.no = no;
            this.c = c;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Long getNo() {
            return no;
        }

        public void setNo(Long no) {
            this.no = no;
        }

        public C getC() {
            return c;
        }

        public void setC(C c) {
            this.c = c;
        }

        public String getaSpecial() {
            return aSpecial;
        }

        public void setaSpecial(String aSpecial) {
            this.aSpecial = aSpecial;
        }
    }

    static class B {
        private String name;
        private Date createDate;
        private int score;
        private Long no;
        private C c;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Long getNo() {
            return no;
        }

        public void setNo(Long no) {
            this.no = no;
        }

        public C getC() {
            return c;
        }

        public void setC(C c) {
            this.c = c;
        }
    }

    @Test
    public void apacheBeanUtils() throws Exception {
        C c = new C();
        System.out.println("----------------");
        System.out.println("a: " + c.hashCode());

        A a = new A("aaa", Calendar.getInstance().getTime(), 100, 10L, c);
        a.setaSpecial("hello A");
        B b = new B();
        BeanUtils.copyProperties(b, a);
        System.out.println("b: " + a.getC().hashCode());
        //System.out.println("c: " + b.getC().hashCode());

        System.out.println("----------------");
        System.out.println("a: " + c.hashCode());
        org.springframework.beans.BeanUtils.copyProperties(a, b);
        System.out.println("b: " + a.getC().hashCode());
        System.out.println("c: " + b.getC().hashCode());
    }
}
