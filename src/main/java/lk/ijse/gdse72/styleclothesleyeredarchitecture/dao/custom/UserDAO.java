package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.User;

public interface UserDAO extends CrudDAO<User> {
    boolean ValidUser(String username, String password) throws Exception;
}
