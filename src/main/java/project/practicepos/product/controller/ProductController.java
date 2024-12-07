package project.practicepos.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.practicepos.category.model.CategoryResponse;
import project.practicepos.category.service.CategoryService;
import project.practicepos.product.model.ProductRequest;
import project.practicepos.util.Response;
import project.practicepos.product.model.ProductResponse;
import project.practicepos.product.service.ProductService;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("pages/product/edit");
        Optional<ProductResponse> result = productService.getById(id);
        if (result.isPresent()) {
            modelAndView.addObject("product", result.get());
            addObject(modelAndView);
            return modelAndView;
        }

        return new ModelAndView("redirect:/product");
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute("product") @Valid ProductRequest product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("pages/product/edit");
            modelAndView.addObject("product", product);
            addObject(modelAndView);
            return modelAndView;
        }

        productService.update(product.getId(), product);
        return new ModelAndView("redirect:/product");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("pages/product/delete");
        Optional<ProductResponse> result = productService.getById(id);
        if (result.isPresent()) {
            modelAndView.addObject("product", result.get());
            addObject(modelAndView);
            return modelAndView;
        }

        return new ModelAndView("redirect:/product");
    }

    @PostMapping("/remove")
    public ModelAndView remove(@ModelAttribute("product") ProductRequest product) {
        productService.delete(product.getId());
        return new ModelAndView("redirect:/product");
    }
}
