package edu.xmu.test.javase.jmockit;

public class UserDao {
    public void insert(User user) throws UserException {
        if (user == null || user.getAge() >= 150 || user.getAge() < 0) {
            throw new UserException();
        }
        // insert into db
    }

    public void format(User usr) {
        if (usr.getAge() > 150) {
            usr.setAge(150);
        }
        if (usr.getAge() < 0) {
            usr.setAge(0);
        }
    }
}
