package mvp.cool.master.sqlite;

import com.example.User;
import com.example.UserDao;

import de.greenrobot.dao.query.QueryBuilder;
import mvp.cool.master.App;

/**@\
 * @version  1.0
 * @author  TanHao
 * Created by Administrator on 2017/4/26.
 */

public class DbUtils {

    public static void insertUser(User user){
        UserDao userDao = App.getUserDao();
        userDao.insert(user);
    }

    public static void deleteUser(User user){
        UserDao userDao = App.getUserDao();
        userDao.delete(user);
    }

    public static void updateUser(User user){
        UserDao userDao = App.getUserDao();
        userDao.update(user);
    }

    public static long queryUser(String name){
        QueryBuilder<User> query = App.getUserDao().queryBuilder();
        query.where(UserDao.Properties.UserName.eq(name));
        long count = query.count();
        return count;
    }
}
