package project.practicepos.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.practicepos.category.model.CategoryResponse;
import project.practicepos.category.service.CategoryService;
import project.practicepos.product.model.ProductRequest;
import project.practicepos.util.Response;
import project.practicepos.product.model.ProductResponse;
import project.practicepos.product.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView getProducts() {
        ModelAndView modelAndView = new ModelAndView("pages/product/index");
        List<ProductResponse> result = productService.get();
        modelAndView.addObject("products", result);
        return modelAndView;
    }

    @GetMapping("/data")
    public ResponseEntity<Response> getProductsData() {
        List<ProductResponse> result = productService.get();
        return ResponseEntity.ok().body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .data(result)
                        .total(result.size())
                        .build()
        );
    }

    private void addObject(ModelAndView view) {
        List<CategoryResponse> result = categoryService.get();
        view.addObject("category", result);
    }

    @GetMapping("/add")
    public ModelAndView addProduct() {
        ModelAndView modelAndView = new ModelAndView("pages/product/add");
        modelAndView.addObject("product", new ProductRequest());
        addObject(modelAndView);
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute("product") @Valid ProductRequest product, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("pages/product/add");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("product", product);
            addObject(modelAndView);
            return modelAndView;
        }

        productService.save(product);
        return new ModelAndView("redirect:/product");
    }
}
