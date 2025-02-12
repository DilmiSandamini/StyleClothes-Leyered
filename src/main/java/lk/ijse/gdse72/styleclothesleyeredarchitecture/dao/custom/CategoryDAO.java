package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CategoryDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryDAO extends CrudDAO<Category> {
    ArrayList<String> getAllCategoryIds() throws SQLException;
    String getCategoryNameById(String categoryId) throws SQLException;
}
