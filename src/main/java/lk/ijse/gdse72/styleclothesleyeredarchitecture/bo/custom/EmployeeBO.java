package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    String getNextEmployeeId() throws SQLException;
    ArrayList<EmployeeDTO> getAllEmployees() throws SQLException;
    boolean deleteEmployee(String employeeId) throws SQLException;
    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException;
    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException;
}
