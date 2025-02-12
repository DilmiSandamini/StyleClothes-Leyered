package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO userDTO) throws SQLException;
    boolean updateUser(UserDTO userDTO) throws SQLException;
    boolean deleteUser(String userID) throws SQLException;
    ArrayList<UserDTO> getAllUsers() throws SQLException;

    boolean isValidUser(String username, String password) throws Exception;

    String getNextUserId() throws SQLException;
}
