package project.practicepos.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.practicepos.category.model.CategoryRequest;
import project.practicepos.category.model.CategoryResponse;
import project.practicepos.util.Response;
import project.practicepos.category.service.CategoryService;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/data")
    public ResponseEntity<Response> getData() {
        List<CategoryResponse> result = categoryService.get();
        return ResponseEntity.ok().body(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .data(result)
                        .total(result.size())
                        .build()
        );
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("pages/category/add");
        modelAndView.addObject("category", new CategoryRequest());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView create(@ModelAttribute("category") @Valid CategoryRequest category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("pages/category/add");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("category", category);
            return modelAndView;
        }
        this.categoryService.create(category);
        return new ModelAndView("redirect:/category");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("pages/category/edit");
        Optional<CategoryResponse> category = categoryService.getById(id);
        if (category.isPresent()) {
            modelAndView.addObject("category", category.get());
            return modelAndView;
        }
        return new ModelAndView("redirect:/category");
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute("category") @Valid CategoryRequest category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("pages/category/edit");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("category", category);
            return modelAndView;
        }
        this.categoryService.update(category.getId(), category);
        return new ModelAndView("redirect:/category");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView remove(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("pages/category/delete");
        Optional<CategoryResponse> category = categoryService.getById(id);
        if (category.isPresent()) {
            modelAndView.addObject("category", category.get());
            return modelAndView;
        }
        return new ModelAndView("redirect:/category");
    }

    @PostMapping("/remove")
    public ModelAndView remove(@ModelAttribute("category") CategoryRequest category) {
        this.categoryService.delete(category.getId());
        return new ModelAndView("redirect:/category");
    }
}
