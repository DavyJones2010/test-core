package edu.xmu.test.javase.jmockit;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    UserDao userDao;

    public boolean save(List<User> usrs) {
        boolean allSuccess = true;
        for (User usr : usrs) {
            try {
                userDao.insert(usr);
            } catch (Exception e) {
                e.printStackTrace();
                allSuccess = false;
            }
        }
        return allSuccess;
    }

    public boolean checkUser(User usr) {
        userDao.format(usr);
        // 理论上checkUser应该始终返回true
        return usr.getAge() <= 150 && usr.getAge() >= 0;
    }


}
