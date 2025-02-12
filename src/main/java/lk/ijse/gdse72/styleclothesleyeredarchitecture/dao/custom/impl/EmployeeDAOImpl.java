package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.EmployeeDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.EmployeeDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    //EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT employeeID FROM employee ORDER BY employeeID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            int newIdIndex = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        ArrayList<Employee> employeeS = new ArrayList<>();

        while (rst.next()) {
            employeeS.add(new Employee(
                    rst.getString("employeeID"),
                    rst.getString("name"),
                    rst.getString("contact"),
                    rst.getString("position")
            ));
        }
        return employeeS;
    }

    @Override
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO employee VALUES (?, ?, ?, ?)",
                entity.getEmployeeID(),
                entity.getName(),
                entity.getContact(),
                entity.getPosition()
        );
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE employee SET name=?, contact=?, position=? WHERE employeeID=?",
                entity.getName(),
                entity.getContact(),
                entity.getPosition(),
                entity.getEmployeeID()
        );
    }

    @Override
    public boolean delete(String employeeId) throws SQLException {
        return SQLUtil.execute("DELETE FROM employee WHERE employeeID=?", employeeId);
    }

    @Override
    public ArrayList<String> getAllEmployeeIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT employeeID FROM employee");
        ArrayList<String> employeeIds = new ArrayList<>();
        while (rst.next()) {
            employeeIds.add(rst.getString(1));
        }
        return employeeIds;
    }
}
