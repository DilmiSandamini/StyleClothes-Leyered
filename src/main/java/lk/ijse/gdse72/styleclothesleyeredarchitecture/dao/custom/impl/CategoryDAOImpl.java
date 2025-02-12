package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.CategoryDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CategoryDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select categoryID from category order by categoryID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("T%03d", newIdIndex);
        }
        return "T003";
    }

    @Override
    public ArrayList<Category> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from category");

        ArrayList<Category> categoryS = new ArrayList<>();

        while (rst.next()) {
            Category category = new Category(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            categoryS.add(category);
        }
        return categoryS;
    }

    public ArrayList<String> getAllCategoryIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT categoryID FROM category");

        ArrayList<String> categoryIds = new ArrayList<>();

        while (rst.next()) {
            categoryIds.add(rst.getString(1));
        }

        return categoryIds;
    }

    public String getCategoryNameById(String categoryId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select categoryName from category where categoryID=?", categoryId);

        if (rst.next()) {
            return rst.getString("categoryName");
        }
        return null;
    }

    @Override
    public boolean save(Category entity) throws SQLException {
        return SQLUtil.execute(
                "insert into category values (?,?,?)",
                entity.getCategoryID(),
                entity.getCategoryName(),
                entity.getDescription()
        );
    }

    @Override
    public boolean update(Category entity) throws SQLException {
        return SQLUtil.execute(
                "update category set categoryName=?, description=? where categoryID=?",
                entity.getCategoryName(),
                entity.getDescription(),
                entity.getCategoryID()
        );
    }

    @Override
    public boolean delete(String categoryId) throws SQLException {
        return SQLUtil.execute("delete from category where categoryID=?", categoryId);
    }
}
