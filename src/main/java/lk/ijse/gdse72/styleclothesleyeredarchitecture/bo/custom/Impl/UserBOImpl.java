package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.UserBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.UserDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.UserDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException {
        return userDAO.save(new User(
                userDTO.getUserID(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword()
        ));
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException {
        return userDAO.update(new User(
                userDTO.getUserID(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword()
        ));
    }

    @Override
    public boolean deleteUser(String userID) throws SQLException {
        return userDAO.delete(userID);
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException {
        ArrayList<User> allEntityData = userDAO.getAll();
        ArrayList<UserDTO> allDTOData = new ArrayList<>();
        for (User u : allEntityData) {
            allDTOData.add(new UserDTO(
                    u.getUserID(),
                    u.getName(),
                    u.getEmail(),
                    u.getPassword()
            ));
        }
        return allDTOData;
    }

    @Override
    public boolean isValidUser(String username, String password) throws Exception {
        return userDAO.ValidUser(username, password);
    }

    @Override
    public String getNextUserId() throws SQLException {
        return userDAO.getNextId();
    }
}
