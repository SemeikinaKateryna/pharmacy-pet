package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Manufacturer;
import ua.nure.pharmacy.entity.Product;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.ProductRepository;

import java.util.List;
import java.util.Set;

@Service
public class ProductService implements CRUDOperation<Product> {
    ProductRepository productRepository = new ProductRepository();
    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    @Override
    public boolean insert(Product product) {
        return productRepository.insert(product);
    }
    @Override
    public boolean update(Product product) {
        return productRepository.update(product);
    }

    @Override
    public boolean delete(Product product) {
        return productRepository.delete(product);
    }

    public List<Product> findAllByName(String parameterFinal) {
        return productRepository.findAllByName(parameterFinal);
    }

    public List<Product> findAllByManufacturer(Manufacturer byName) {
        return productRepository.findAllByManufacturer(byName);
    }

    public List<Product> findAllByManufacturerId(Integer id) {
        return productRepository.findAllByManufacturerId(id);
    }

    public List<Product> sortAllByNameAsc() {
        return productRepository.sortAllBy(SQLQuery.ProductRepositorySQL.ORDER_BY_NAME_ASC);
    }

    public List<Product> sortAllByNameDesc() {
        return productRepository.sortAllBy(SQLQuery.ProductRepositorySQL.ORDER_BY_NAME_DESC);
    }
    public Set<Product> filterByPackAmount(Integer from, Integer to) {
        return productRepository.filterByPackAmount(from,to);
    }
    public Set<Product> filterByDoseAmount(Integer from, Integer to) {
        return productRepository.filterByDoseAmount(from, to);
    }

    public Set<Product> filterByPackAndDoseAmount(Integer inputFromPack, Integer inputToPack, Integer inputFromDose, Integer inputToDose) {
        return productRepository.filterByPackAndDoseAmount(inputFromPack, inputToPack, inputFromDose,inputToDose);
    }

    public List<Product> findAllByCategory(String category) {
        return productRepository.findAllByCategory(category);
    }

    public Product findLast(){
        return productRepository.findLast();
    }
}
