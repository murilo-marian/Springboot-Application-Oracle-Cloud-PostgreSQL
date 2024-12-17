package com.projetocurvello.projetocurvello.product;

import com.projetocurvello.projetocurvello.OracleCloud.OCIObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.lang.System.currentTimeMillis;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    private final OCIObjectStorageService storageService;

    @Autowired
    public ProductController(ProductService productService, OCIObjectStorageService storageService) {
        this.productService = productService;
        this.storageService = storageService;
    }

//    @Autowired
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }

    @GetMapping
    public String showProductsPage(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size, Model model) {
        Page<Product> productsPage = productService.getAllProducts(page, size);
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("newProduct", new Product());
        return "index";
    }

    @PostMapping
    public String addNewProduct(@RequestParam("image") MultipartFile file, @ModelAttribute Product product) throws IOException {
        String fileName = currentTimeMillis() + "_" + product.getName();
        String imageUrl = storageService.uploadFile(fileName, file.getInputStream(), file.getSize());

        product.setImgUrl(imageUrl);
        productService.addNewProduct(product);
        return "redirect:/products";
    }

//    @PostMapping
//    public String addNewProduct(Product product) {
//        productService.addNewProduct(product);
//        return "redirect:/products";
//    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, Product product,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        productService.updateProduct(id, product);
        return "redirect:/products?page=" + page + "&size=" + size;
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return "redirect:/products?page=" + page + "&size=" + size;
        }
        return "redirect:/products?page=" + page + "&size=" + size;
    }
}
