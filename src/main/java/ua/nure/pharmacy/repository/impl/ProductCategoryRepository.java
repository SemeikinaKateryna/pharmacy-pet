package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Category;
import ua.nure.pharmacy.entity.Product;
import ua.nure.pharmacy.entity.ProductCategory;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductCategoryRepository implements ResultSetExtractor<ProductCategory> {
    ProductRepository productRepository = new ProductRepository();
    CategoryRepository categoryRepository = new CategoryRepository();
    ManufacturerRepository manufacturerRepository = new ManufacturerRepository();

    public List<Category> findByIdProductId(int product_id) {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductCategoryRepositorySQL.FIND_BY_ID_PRODUCT_ID)) {
            statement.setInt(1, product_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setParent(categoryRepository.findById(resultSet.getInt("parent_id")));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    public List<Product> findByIdCategoryId (int category_id) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductCategoryRepositorySQL.FIND_BY_ID_CATEGORY_ID)) {
            statement.setInt(1, category_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setManufacturer(manufacturerRepository
                        .findById(resultSet.getInt("manufacturer_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<ProductCategory> findAll() {
        List<ProductCategory> productsCategories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductCategoryRepositorySQL.SELECT_ALL_PRODUCT_CATEGORY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductCategory productCategory = extractFromResultSet(resultSet);
                productsCategories.add(productCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsCategories;
    }

    public boolean insert(ProductCategory productCategory) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductCategoryRepositorySQL.INSERT_PRODUCT_CATEGORY)) {
            statement.setInt(1, productCategory.getProduct().getId());
            statement.setInt(2, productCategory.getCategory().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ProductCategory productCategory) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductCategoryRepositorySQL.UPDATE_PRODUCT_CATEGORY)) {
            statement.setInt(1,productCategory.getProduct().getId());
            statement.setInt(2,productCategory.getCategory().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(ProductCategory productCategory) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductCategoryRepositorySQL.DELETE_PRODUCT_CATEGORY)) {
            statement.setInt(1, productCategory.getProduct().getId());
            statement.setInt(2,productCategory.getCategory().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ProductCategory extractFromResultSet(ResultSet resultSet) throws SQLException {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProduct(productRepository.findById(resultSet.getInt("product_id")));
        productCategory.setCategory(categoryRepository.findById(resultSet.getInt("category_id")));
        return productCategory;
    }

}
