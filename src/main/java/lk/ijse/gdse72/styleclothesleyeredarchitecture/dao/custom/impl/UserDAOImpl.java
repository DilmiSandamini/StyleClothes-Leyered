package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.UserDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select userID from user order by userID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(User entity) throws SQLException {
        return SQLUtil.execute(
                "insert into user values (?,?,?,?)",
                entity.getUserID(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword()
        );
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return SQLUtil.execute(
                "update user set name=?,email=?,password=? where userID=?",
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getUserID()
        );
    }

    @Override
    public boolean delete(String userID) throws SQLException {
        return SQLUtil.execute("delete from user where userID=?", userID);
    }

    @Override
    public boolean ValidUser(String username, String password) throws Exception {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE name = ? AND password = ?", username, password);
        return rst.next();    }
}
