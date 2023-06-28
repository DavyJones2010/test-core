package edu.xmu.test.framework.spring.spel;

import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

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
    @Test
    public void evalTest() {
        ExpressionParser parser = new SpelExpressionParser();

        String key
                = "#usr.name + '#' + #usr.pwd + '#' + #usr.getAge() + '#' + (#usr.sex == null ? 'male' : #usr.sex)";
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
    }

    class User {
        String name;
        String pwd;
        Integer age;
        String sex;

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
    }

}
