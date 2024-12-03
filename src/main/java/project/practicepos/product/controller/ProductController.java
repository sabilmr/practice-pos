package project.practicepos.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.practicepos.product.model.ProductResponse;
import project.practicepos.product.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ModelAndView getProducts() {
        ModelAndView modelAndView = new ModelAndView("pages/product/index");
        List<ProductResponse> result = productService.get();
        modelAndView.addObject("products", result);
        return modelAndView;
    }
}
