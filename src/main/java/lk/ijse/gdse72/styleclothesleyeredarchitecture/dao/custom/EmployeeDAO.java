package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.EmployeeDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee> {
    ArrayList<String> getAllEmployeeIds() throws SQLException;
}
