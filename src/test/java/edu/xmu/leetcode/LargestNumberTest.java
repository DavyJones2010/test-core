package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link <a href="https://leetcode.com/problems/largest-number/">Largest Number</a>}
 */
public class LargestNumberTest {
    @Test
    public void largestNumberTest() {
        LargestNumberTest instance = new LargestNumberTest();
        assertEquals("321", instance.largestNumber(new int[] {1, 2, 3}));
        assertEquals("311211", instance.largestNumber(new int[] {11, 12, 31}));
        assertEquals("9534330", instance.largestNumber(new int[] {3, 30, 34, 5, 9}));
        assertEquals("12121", instance.largestNumber(new int[] {121, 12}));
        assertEquals("111", instance.largestNumber(new int[] {1, 1, 1}));
        assertEquals("0", instance.largestNumber(new int[] {0, 0}));
    }

    public String largestNumber(int[] nums) {
        List<String> list = new ArrayList<>();
        for (int num : nums) {
            list.add(String.valueOf(num));
        }
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o2 + o1).compareTo(o1 + o2);
            }
        });
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
        }
        return sb.toString().startsWith("0") ? "0" : sb.toString();
    }

    static class OPK {
        private Date gmtCreate;

        public OPK(Date gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public Date getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(Date gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<OPK> opks = Lists.newArrayList();
        opks.add(new OPK(Calendar.getInstance().getTime()));
        opks.add(new OPK(Calendar.getInstance().getTime()));
        opks.add(new OPK(Calendar.getInstance().getTime()));
        opks.add(new OPK(Calendar.getInstance().getTime()));
        opks.add(new OPK(Calendar.getInstance().getTime()));
        opks.add(new OPK(Calendar.getInstance().getTime()));
        opks.add(new OPK(Calendar.getInstance().getTime()));
        opks.add(new OPK(Calendar.getInstance().getTime()));
        Collections.sort(opks, new Comparator<OPK>() {
            @Override
            public int compare(OPK o1, OPK o2) {
                return o1.gmtCreate.compareTo(o2.gmtCreate);
            }
        });
        System.out.println(JSON.toJSONString(opks));
    }
}
