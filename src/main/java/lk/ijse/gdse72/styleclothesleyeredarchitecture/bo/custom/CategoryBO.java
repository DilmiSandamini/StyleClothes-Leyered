package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CategoryDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryBO extends SuperBO {

    String getNextCategoryId() throws SQLException;
    ArrayList<Category> getAllCategoryS() throws SQLException;
    boolean deleteCategory(String categoryId) throws SQLException;
    boolean saveCategory(CategoryDTO categoryDTO) throws SQLException;
    boolean updateCategory(CategoryDTO categoryDTO) throws SQLException;
}
