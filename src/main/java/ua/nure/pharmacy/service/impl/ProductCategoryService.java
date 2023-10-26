package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Category;
import ua.nure.pharmacy.entity.Product;
import ua.nure.pharmacy.entity.ProductCategory;
import ua.nure.pharmacy.repository.impl.ProductCategoryRepository;

import java.util.List;

@Service
public class ProductCategoryService {
    ProductCategoryRepository productCategoryRepository = new ProductCategoryRepository();
    public List<Category> findByIdProductId(int id) {
        return productCategoryRepository.findByIdProductId(id);
    }
    public List<Product> findByIdCategoryId(int id) {
        return productCategoryRepository.findByIdCategoryId(id);
    }

    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    public void insert(ProductCategory productCategory) {
        productCategoryRepository.insert(productCategory);
    }

    public void update(ProductCategory productCategory) {
        productCategoryRepository.update(productCategory);
    }

    public void delete(ProductCategory productCategory) {
        productCategoryRepository.delete(productCategory);
    }
}
