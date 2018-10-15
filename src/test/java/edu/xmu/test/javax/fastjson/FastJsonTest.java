package edu.xmu.test.javax.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by davywalker on 16/9/29.
 */
public class FastJsonTest {
    /**
     * fastjson中parseBean为string时
     */
    @Test
    public void parseDateTest() {
        MyBean myBean = new MyBean();
        long currentTimeMillis = System.currentTimeMillis();
        myBean.setToday(currentTimeMillis);
        myBean.setYesterday(DateUtils.addDays(new Date(currentTimeMillis), -1));
        List<Date> dates = Lists.newArrayList(new Date(currentTimeMillis), DateUtils.addDays(new Date(currentTimeMillis), 1));
        myBean.setDates(dates);

        String s = JSON.toJSONString(myBean);
        System.out.println(s);

        s = JSON.toJSONString(myBean, new PropertyNameFilter());
        System.out.println(s);
        s = JSON.toJSONStringWithDateFormat(myBean, "yyyy-MM-dd hh:mm:ss");
        System.out.println(s);

        // 1. 转换Long为Date类型; 2. 设置默认的Date->String的输出格式
        s = JSON.toJSONString(myBean, SerializeConfig.globalInstance, new SerializeFilter[]{new PropertyTypeFilter()}, "yyyy-MM-dd hh:mm:ss", JSON.DEFAULT_GENERATE_FEATURE, new SerializerFeature[]{});
        System.out.println(s);
    }

    static class PropertyTypeFilter implements ContextValueFilter{

        @Override
        public Object process(BeanContext context, Object object, String name, Object value) {
            if(Long.class.isInstance(value)){
                return new Date((long)value);
            }else{
                return value;
            }
        }

    }
    /**
     * 根据propertyName进行转换, 将long->date->string
     */
    static class PropertyNameFilter implements ContextValueFilter {
        @Override
        public Object process(BeanContext context, Object object, String name, Object value) {
            if ("today".equals(name)) {
                return new Date((long) value).toString();
            } else if ("yesterday".equals(name)) {
                return ((Date) value).toString();
            } else if ("dates".equals(name)) {
                List<Date> dates = (List<Date>) value;
                ArrayList<String> dateStrs = new ArrayList<>();
                for (Date date : dates) {
                    dateStrs.add(date.toString());
                }
                return dateStrs;
            } else {
                return value;
            }
        }
    }

    static class MyBean {
        long today;
        Date yesterday;
        List<Date> dates;

        public long getToday() {
            return today;
        }

        public void setToday(long today) {
            this.today = today;
        }

        public Date getYesterday() {
            return yesterday;
        }

        public void setYesterday(Date yesterday) {
            this.yesterday = yesterday;
        }

        public List<Date> getDates() {
            return dates;
        }

        public void setDates(List<Date> dates) {
            this.dates = dates;
        }
    }

}
