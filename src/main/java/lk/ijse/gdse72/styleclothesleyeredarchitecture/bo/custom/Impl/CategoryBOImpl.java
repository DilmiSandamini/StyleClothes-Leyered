package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.CategoryBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.CategoryDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CategoryDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBOImpl implements CategoryBO {

    CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CATEGORY);

    @Override
    public ArrayList<Category> getAllCategoryS() throws SQLException{
        ArrayList<Category> allEntityData = categoryDAO.getAll();
        ArrayList<CategoryDTO> allDTOData = new ArrayList<>();
        for (Category c : allEntityData) {
            allDTOData.add(new CategoryDTO(
                    c.getCategoryID(),
                    c.getCategoryName(),
                    c.getDescription()
            ));
        }
        return allEntityData;
    }

    @Override
    public boolean deleteCategory(String categoryId) throws SQLException{
        return categoryDAO.delete(categoryId);
    }

    @Override
    public boolean saveCategory(CategoryDTO categoryDTO) throws SQLException{
        return categoryDAO.save(new Category(
                categoryDTO.getCategoryID(),
                categoryDTO.getCategoryName(),
                categoryDTO.getDescription()
        ));
    }

    @Override
    public boolean updateCategory(CategoryDTO categoryDTO) throws SQLException{
        return categoryDAO.update(new Category(
                categoryDTO.getCategoryID(),
                categoryDTO.getCategoryName(),
                categoryDTO.getDescription()
        ));
    }

    @Override
    public String getNextCategoryId() throws SQLException{
        return categoryDAO.getNextId();
    }

}
