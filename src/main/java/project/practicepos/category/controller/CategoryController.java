package project.practicepos.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.practicepos.category.response.CategoryResponse;
import project.practicepos.category.service.CategoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("pages/category/index");
        List<CategoryResponse> result = categoryService.get();
        modelAndView.addObject("category", result);
        return modelAndView;
    }
}
