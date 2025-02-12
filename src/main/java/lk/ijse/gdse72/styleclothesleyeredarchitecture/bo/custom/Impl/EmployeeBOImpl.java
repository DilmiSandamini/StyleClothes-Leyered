package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.EmployeeBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.EmployeeDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.EmployeeDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public String getNextEmployeeId() throws SQLException {
        return employeeDAO.getNextId();
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException {
        ArrayList<Employee> all = employeeDAO.getAll();
        ArrayList<EmployeeDTO> allEmployees = new ArrayList<>();
        for (Employee e : all) {
            allEmployees.add(new EmployeeDTO(
                    e.getEmployeeID(),
                    e.getName(),
                    e.getContact(),
                    e.getPosition()
            ));
        }
    return allEmployees;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.save(new Employee(
                employeeDTO.getEmployeeID(),
                employeeDTO.getName(),
                employeeDTO.getContact(),
                employeeDTO.getPosition()
        ));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.update(new Employee(
                employeeDTO.getEmployeeID(),
                employeeDTO.getName(),
                employeeDTO.getContact(),
                employeeDTO.getPosition()
        ));
    }

    @Override
    public boolean deleteEmployee(String employeeID) throws SQLException {
        return employeeDAO.delete(employeeID);
    }
}
