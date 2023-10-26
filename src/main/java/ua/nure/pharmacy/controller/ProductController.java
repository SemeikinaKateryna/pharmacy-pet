package ua.nure.pharmacy.controller;

import com.mysql.cj.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.pharmacy.entity.*;
import ua.nure.pharmacy.service.impl.ManufacturerService;
import ua.nure.pharmacy.service.impl.PackService;
import ua.nure.pharmacy.service.impl.ProductCategoryService;
import ua.nure.pharmacy.service.impl.ProductService;
import ua.nure.pharmacy.util.PriceComparator;

import java.util.*;


@Controller
public class ProductController {
    ProductService productService;
    ProductCategoryService productCategoryService;
    PackService packService;
    ManufacturerService manufacturerService;
    LoginController loginController;

    public ProductController(ProductService productService,
                             ProductCategoryService productCategoryService,
                             PackService packService, ManufacturerService manufacturerService,
                             LoginController loginController) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.packService = packService;
        this.manufacturerService = manufacturerService;
        this.loginController = loginController;
    }

    @GetMapping("/all_products")
    public String products(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  products) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }
        model.addAttribute("productsPacks", productsPacks);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/products_client";
        }
        if(LoginController.isOperator) {
            model.addAttribute("user", loginController.operator);
            return "/operator/products_operator";
        }
        if(LoginController.isStorekeeper) {
            model.addAttribute("user", loginController.storekeeper);
            return "/storekeeper/products_storekeeper";
        }
        else
            return "/guest/products";
    }

    @GetMapping("/product")
    public String productById(@RequestParam int id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);

        List<Pack> packsByProduct = packService.findByProductId(id);
        model.addAttribute("packsByProduct", packsByProduct);

        Set<Dose> uniqueDoses = new HashSet<>();
        for (Pack pack : packsByProduct) {
            uniqueDoses.add(pack.getDose());
        }
        List<Dose> dosesList = uniqueDoses.stream().toList();
        model.addAttribute("dosesList", dosesList);


        List<Category> categoriesByProduct =
                productCategoryService.findByIdProductId(id);
        model.addAttribute("categoriesByProduct", categoriesByProduct);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/product_client";
        }
        if(LoginController.isOperator) {
            model.addAttribute("user", loginController.operator);
            return "/operator/product_operator";
        }
        if(LoginController.isStorekeeper)
            return "/storekeeper/product_storekeeper";
        else
            return "/guest/product";

    }

    @GetMapping("/sortByNameASC")
    public String sortByNameASC(Model model){
        List<Product> products = productService.sortAllByNameAsc();
        model.addAttribute("products", products);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  products) {
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

    @GetMapping("/sortByNameDESC")
    public String sortByNameDesc(Model model){
        List<Product> products = productService.sortAllByNameDesc();
        model.addAttribute("products", products);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  products) {
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

    @GetMapping("/sortByPriceASC")
    public String sortByPriceASC(Model model){
        List<Product> products = productService.findAll();

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  products) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }

        List<Pack> packList = new ArrayList<>();
        for (Product product :  products) {
            packList.add(productsPacks.get(product).get(0));
        }

        packList.sort(new PriceComparator());
        model.addAttribute("packList", packList);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/sorted_products_client";
        }
        if(LoginController.isOperator)
            return "/operator/products_operator";
        if(LoginController.isStorekeeper)
            return "/storekeeper/products_storekeeper";
        else
            return "/guest/sorted_products";
    }

    @GetMapping("/sortByPriceDESC")
    public String sortByPriceDESC(Model model){
        List<Product> products = productService.findAll();

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  products) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }

        List<Pack> packList = new ArrayList<>();
        for (Product product :  products) {
            packList.add(productsPacks.get(product).get(0));
        }

        packList.sort(new PriceComparator().reversed());

        model.addAttribute("packList", packList);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/sorted_products_client";
        }
        if(LoginController.isOperator)
            return "/operator/products_operator";
        if(LoginController.isStorekeeper)
            return "/storekeeper/products_storekeeper";
        else
            return "/guest/sorted_products";
    }

    @GetMapping("/filter_by_amount")
    public String filterByPackAmount(@RequestParam(required = false) Boolean flexSwitchPackAmount,
                                     @RequestParam(required = false) Integer inputFromPack,
                                     @RequestParam(required = false) Integer inputToPack,
                                     @RequestParam(required = false) Boolean flexSwitchDoseAmount,
                                     @RequestParam(required = false) Integer inputFromDose,
                                     @RequestParam(required = false) Integer inputToDose,
                                     Model model) {
        Set<Product> filteredProducts = new HashSet<>();
        if (flexSwitchPackAmount != null
                && flexSwitchPackAmount
                && flexSwitchDoseAmount == null) {
            filteredProducts
                    = productService.filterByPackAmount(inputFromPack, inputToPack);
        } else if (flexSwitchDoseAmount != null
                && flexSwitchDoseAmount
                && flexSwitchPackAmount == null) {
            filteredProducts =
                    productService.filterByDoseAmount(inputFromDose, inputToDose);
        } else if (flexSwitchPackAmount != null
                && flexSwitchPackAmount
                && flexSwitchDoseAmount != null
                && flexSwitchDoseAmount) {
            filteredProducts = productService.
                    filterByPackAndDoseAmount(inputFromPack, inputToPack, inputFromDose, inputToDose);

        } else {
            if (LoginController.isClient) {
                model.addAttribute("user", loginController.currentCustomer);
                return "/client/products_client";
            }
            else
                return "/guest/products";
        }

        model.addAttribute("products", filteredProducts);

        Map<Product, List<Category>> productsCategories = new HashMap<>();
        for (Product product : filteredProducts) {
            List<Category> productCategories
                    = productCategoryService.findByIdProductId(product.getId());
            productsCategories.put(product, productCategories);
        }
        model.addAttribute("productsCategories", productsCategories);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product : filteredProducts) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }
        model.addAttribute("productsPacks", productsPacks);

        if (LoginController.isClient) {
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

    @GetMapping("/manufacturer")
    public String manufacturerById(@RequestParam Integer id, Model model){

        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("name",manufacturer.getName());

        List<Product> products = productService.findAllByManufacturerId(id);
        model.addAttribute("products", products);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product :  products) {
            List<Pack> productPacks
                    = packService.findByProductId(product.getId());
            productsPacks.put(product, productPacks);
        }
        model.addAttribute("productsPacks", productsPacks);

        if(LoginController.isClient) {
            model.addAttribute("user", loginController.currentCustomer);
            return "/client/products_by_manufacturer_client";
        }
        if(LoginController.isOperator)
            return "/operator/products_operator";
        if(LoginController.isStorekeeper)
            return "/storekeeper/products_storekeeper";
        else
            return "/guest/products_by_manufacturer";
    }
}
