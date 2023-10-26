package ua.nure.pharmacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.pharmacy.entity.*;
import ua.nure.pharmacy.service.impl.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NavbarController {
    WorkerService workerService;
    ProductService productService;
    ProductCategoryService productCategoryService;
    PackService packService;
    ManufacturerService manufacturerService;
    ProductController productController;
    LoginController loginController;

    public NavbarController(WorkerService workerService, ProductService productService,
                            ProductCategoryService productCategoryService,
                            PackService packService, ManufacturerService manufacturerService,
                            ProductController productController, LoginController loginController) {
        this.workerService = workerService;
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.packService = packService;
        this.manufacturerService = manufacturerService;
        this.productController = productController;
        this.loginController = loginController;
    }

    @GetMapping("/about_us")
    public String aboutUs(Model model) {
        if (LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/about_us_client";
        }
        else
            return "/guest/about_us";

    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        Worker operator = workerService.findById(1);
        if (operator != null) {
            model.addAttribute("operator", operator);
        }
        if (LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/contacts_client";
        }
        else
            return "/guest/contacts";
    }

    @GetMapping("/search")
    public String search(@RequestParam("param") String param, Model model){
        List<Product> searchByName = searchByName(param);
        model.addAttribute("parameter", param);
        if(!searchByName.isEmpty()) {
            model.addAttribute("products", searchByName);
            Map<Product, List<Category>> productsCategories = new HashMap<>();
            for (Product product : searchByName) {
                List<Category> productCategories
                        = productCategoryService.findByIdProductId(product.getId());
                productsCategories.put(product, productCategories);
            }
            model.addAttribute("productsCategories", productsCategories);

            Map<Product, List<Pack>> productsPacks = new HashMap<>();
            for (Product product : searchByName) {
                List<Pack> productPacks
                        = packService.findByProductId(product.getId());
                productsPacks.put(product, productPacks);
            }
            model.addAttribute("productsPacks", productsPacks);

            if(LoginController.isClient) {
                model.addAttribute("user", loginController.currentCustomer);
                return "/client/found_products_client";
            }
            if(LoginController.isOperator)
                return "/operator/found_products_operator";
            if(LoginController.isStorekeeper)
                return "/storekeeper/found_products_storekeeper";
            else
                return "/guest/found_products";
        }else {
            List<Product> searchByManufacturer = searchByManufacturerName(param);
            if(!searchByManufacturer.isEmpty()) {
                model.addAttribute("products", searchByManufacturer);
                Map<Product, List<Category>> productsCategories = new HashMap<>();
                for (Product product : searchByManufacturer) {
                    List<Category> productCategories
                            = productCategoryService.findByIdProductId(product.getId());
                    productsCategories.put(product, productCategories);
                }
                model.addAttribute("productsCategories", productsCategories);

                Map<Product, List<Pack>> productsPacks = new HashMap<>();
                for (Product product : searchByManufacturer) {
                    List<Pack> productPacks
                            = packService.findByProductId(product.getId());
                    productsPacks.put(product, productPacks);
                }
                model.addAttribute("productsPacks", productsPacks);
                if(LoginController.isClient) {
                    model.addAttribute("user", loginController.currentCustomer);
                    return "/client/found_products_client";
                }
                if(LoginController.isOperator)
                    return "/operator/found_products_operator";
                if(LoginController.isStorekeeper)
                    return "/storekeeper/found_products_storekeeper";
                else
                    return "/guest/found_products";
            }else{
                return productController.products(model);
            }
        }
    }

    public List<Product> searchByName(String param) {
        String parameter = "%";
        String parameterParam = parameter.concat(param);
        String parameterFinal = parameterParam.concat("%");
        return productService.findAllByName(parameterFinal);
    }

    public List<Product> searchByManufacturerName(String param) {
        String parameter = "%";
        String parameterParam = parameter.concat(param);
        String parameterFinal = parameterParam.concat("%");
        Manufacturer byName = manufacturerService.findByName(parameterFinal);
        List<Product> products = new ArrayList<>();
        if(byName != null)
            products = productService.findAllByManufacturer(byName);
        return products;
    }

    @GetMapping("/hard")
    public String hardProducts(Model model){
        List<Product> hardProducts = productService.findAllByCategory("Тверді");
        model.addAttribute("products", hardProducts);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  hardProducts) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }
        model.addAttribute("productsPacks", productsPacks);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/products_client";
        }
        if(LoginController.isOperator)
            return "/operator/products_operator";
        if(LoginController.isStorekeeper)
            return "/storekeeper/products_storekeeper";
        else
            return "/guest/products";
    }
    @GetMapping("/soft")
    public String softProducts(Model model){
        List<Product> hardProducts = productService.findAllByCategory("М`які");
        model.addAttribute("products", hardProducts);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  hardProducts) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }
        model.addAttribute("productsPacks", productsPacks);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/products_client";
        }
        if(LoginController.isOperator)
            return "/operator/products_operator";
        if(LoginController.isStorekeeper)
            return "/storekeeper/products_storekeeper";
        else
            return "/guest/products";
    }
    @GetMapping("/liquid")
    public String liquidProducts(Model model){
        List<Product> hardProducts = productService.findAllByCategory("Рідкі");
        model.addAttribute("products", hardProducts);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  hardProducts) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }
        model.addAttribute("productsPacks", productsPacks);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/products_client";
        }
        if(LoginController.isOperator)
            return "/operator/products_operator";
        if(LoginController.isStorekeeper)
            return "/storekeeper/products_storekeeper";
        else
            return "/guest/products";
    }
    @GetMapping("/gaseous")
    public String gaseousProducts(Model model){
        List<Product> hardProducts = productService.findAllByCategory("Газоподібні");
        model.addAttribute("products", hardProducts);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  hardProducts) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }
        model.addAttribute("productsPacks", productsPacks);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/products_client";
        }
        if(LoginController.isOperator)
            return "/operator/products_operator";
        if(LoginController.isStorekeeper)
            return "/storekeeper/products_storekeeper";
        else
            return "/guest/products";
    }
}
