package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Manufacturer;
import ua.nure.pharmacy.entity.Product;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;
import ua.nure.pharmacy.service.impl.ProductService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ProductRepository implements CRUDOperation<Product>,
        ResultSetExtractor<Product> {
    ManufacturerRepository manufacturerRepository = new ManufacturerRepository();

    @Override
    public Product findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.FIND_BY_ID_PRODUCT)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.SELECT_ALL_PRODUCT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = extractFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean insert(Product product) {
        if(product.getId() != null) {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement
                         (SQLQuery.ProductRepositorySQL.INSERT_PRODUCT)) {
                statement.setInt(1, product.getId());
                statement.setString(2, product.getName());
                statement.setString(3, product.getDescription());
                statement.setInt(4, product.getManufacturer().getId());
                statement.setString(5, product.getPhotoUrl());
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return insertWithoutId(product);
        }
    }

    private boolean insertWithoutId(Product product) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.INSERT_PRODUCT_WITHOUT_ID)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getManufacturer().getId());
            statement.setString(4, product.getPhotoUrl());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.UPDATE_PRODUCT)) {
            statement.setInt(1,product.getId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getManufacturer().getId());
            statement.setString(5, product.getPhotoUrl());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.DELETE_PRODUCT)) {
            statement.setInt(1, product.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Product extractFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setManufacturer(manufacturerRepository
                .findById(resultSet.getInt("manufacturer_id")));
        product.setPhotoUrl(resultSet.getString("photo_url"));
        return product;
    }

    public List<Product> findAllByName(String param) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.SELECT_ALL_PRODUCTS_BY_NAME)) {
            statement.setString(1, param);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = extractFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> findAllByManufacturer(Manufacturer manufacturer) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.SELECT_ALL_PRODUCTS_BY_MANUFACTURER_ID)) {
                statement.setInt(1, manufacturer.getId());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Product product = extractFromResultSet(resultSet);
                    products.add(product);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> sortAllBy(String SQL_STATEMENT) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQL_STATEMENT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = extractFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Set<Product> filterByPackAmount(Integer from, Integer to) {
        Set<Product> products = new HashSet<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.FILTER_ALL_BY_PACK_AMOUNT)) {
            statement.setInt(1, from);
            statement.setInt(2,to);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = extractFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Set<Product> filterByDoseAmount(Integer from, Integer to) {
        Set<Product> products = new HashSet<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.FILTER_ALL_BY_DOSE_AMOUNT)) {
            statement.setInt(1, from);
            statement.setInt(2,to);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = extractFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Set<Product> filterByPackAndDoseAmount(Integer inputFromPack, Integer inputToPack,
                                                  Integer inputFromDose, Integer inputToDose) {
        Set<Product> products = new HashSet<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.FILTER_ALL_BY_PACK_AND_DOSE_AMOUNT)) {
            statement.setInt(1, inputFromPack);
            statement.setInt(2, inputToPack);
            statement.setInt(3, inputFromDose);
            statement.setInt(4, inputToDose);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = extractFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> findAllByCategory(String category) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.SELECT_ALL_PRODUCT_BY_CATEGORY_NAME)) {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = extractFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> findAllByManufacturerId(Integer id) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.SELECT_ALL_PRODUCTS_BY_MANUFACTURER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = extractFromResultSet(resultSet);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product findLast() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ProductRepositorySQL.FIND_LAST)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
