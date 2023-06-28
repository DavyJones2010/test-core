package edu.xmu.test.framework.spring.spel;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 由于Spring的CacheKey表达式是使用SpEL进行解析.
 * <p>
 * 针对复杂场景, 例如if等条件语句, 需要单独测试下.
 * <p>
 * 详细参见文档: <a href="https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html">Spring 表达式语言
 * (SpEL)</a >
 */
public class SpelTest {

    /**
     * 测试最简单的用法, 需要注意null的处理
     */
    @Test
    public void evalTest() {
        ExpressionParser parser = new SpelExpressionParser();

        String key = "#usr.name + '#' + #usr.pwd + '#' + #usr.getAge() + '#' + (#usr.sex == null ? 'male' : #usr.sex)";
        User usr = new User("kunlun", "1234", 33, null);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("usr", usr);

        Object value = parser.parseExpression(key).getValue(context);
        assertEquals("kunlun#1234#33#male", value.toString());

        usr = new User("hanting", "4321", 28, "female");
        context = new StandardEvaluationContext();
        context.setVariable("usr", usr);

        value = parser.parseExpression(key).getValue(context);
        assertEquals("hanting#4321#28#female", value.toString());

        usr = new User("hanting", null, null, "female");
        context = new StandardEvaluationContext();
        context.setVariable("usr", usr);

        value = parser.parseExpression(key).getValue(context);
        assertEquals("hanting#null#null#female", value.toString());
    }

    /**
     * 测试复杂的方法(例如引入了FastJson)
     */
    @Test
    public void evalTest_withFastJson() {
        ExpressionParser parser = new SpelExpressionParser();
        String key = "#usr.toJson()"; // 注意: 这里之前的方法名称为 getJson(), 从而导致了在 getJson() 方法执行时, 以为json是个字段, 因此又一次执行了getJson(), 从而导致了无限的递归
        User usr = new User("kunlun", "1234", 33, null);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("usr", usr);

        Object value = parser.parseExpression(key).getValue(context);
        assertEquals("{\"age\":33,\"name\":\"kunlun\",\"pwd\":\"1234\"}", value.toString());
    }

    /**
     * 测试复杂的方法, 使用简单数组(实际key就是使用数组类型的toString)
     */
    @Test
    public void evalTest_withArray() {
        ExpressionParser parser = new SpelExpressionParser();
        String key = "#usr.acls";
        User usr = new User("kunlun", "1234", 33, null);
        List<String> acls = Lists.newArrayList("/home", "/root", "/etc");
        usr.setAcls(acls);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("usr", usr);

        Object value = parser.parseExpression(key).getValue(context);
        assertEquals("[/home, /root, /etc]", acls.toString());
        assertEquals("[/home, /root, /etc]", value.toString());
    }

}

class User {
    String name;
    String pwd;
    Integer age;
    String sex;

    List<String> acls;

    public User(String name, String pwd, Integer age, String sex) {
        this.name = name;
        this.pwd = pwd;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public String getSex() {
        return sex;
    }

    public Integer getAge() {
        return age;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public List<String> getAcls() {
        return acls;
    }

    public void setAcls(List<String> acls) {
        this.acls = acls;
    }
}