package edu.xmu.test.javase.jmockit;

import com.beust.jcommander.internal.Lists;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JMockit.class)
public class JMockitTest {
    @Tested
    UserService userService;
    @Injectable
    UserDao userDao;

    /**
     * 测试对userDao进行mock, 根据不同的输入, userDao返回不同的结果
     */
    @Test
    public void testSaveWithDiffUser() throws UserException {
        User usr1 = new User();
        usr1.setName("Yang");
        User usr2 = new User();
        usr2.setName("Wang");

        List<User> usrs = Lists.newArrayList(usr1, usr2);
        new Expectations() {{
            // 由于这里 userDao 被mock了, 因此不会真正去执行 userDao.insert 方法
            userDao.insert((User) any);
            // 因此使用 Delegate 来根据不同的input来mock userDao.insert的不同output;
            // 如果output为void, 则使用 Delegate<Void>
            result = new Delegate<Void>() {
                // 方法签名需要mock的方法`insert`保持一致
                void insert(User usr) throws UserException {
                    // 这里根据不同的input(usr), 对 userDao.insert 的结果进行mock
                    if (usr.getName().equalsIgnoreCase("Wang")) {
                        System.out.printf("User is Wang!");
                        throw new UserException();
                    }
                }
            };
        }};

        boolean save = userService.save(usrs);
        assertFalse(save);
    }

    /**
     * 测试对userDao进行mock, 测试修改了input的参数
     */
    @Test
    public void testCheckUser() {
        User usr1 = new User();
        usr1.setName("Yang");
        usr1.setAge(151);
        new Expectations() {{
            // 这里没有真正去执行format, 因此没有把age进行规整
            userDao.format((User) any);
        }};
        boolean b = userService.checkUser(usr1);
        // 因此导致出现了正常情况下不可能的false值
        assertFalse(b);

        new Expectations() {{
            // 这里没有真正去执行format, 因此没有把age进行规整
            userDao.format((User) any);
            // 虽然userDao.format无返回结果, 但这里仍然可以使用 result = new Delegate<Void>() {} 对方法执行进行Mock
            result = new Delegate<Void>() {
                // 方法签名需要mock的方法`format`保持一致
                public void format(User usr) {
                    usr.setAge(25);
                }
            };
        }};
        b = userService.checkUser(usr1);
        // 因此导致出现了正常情况下不可能的false值
        assertTrue(b);
        assertEquals(25, usr1.getAge());
    }
}
