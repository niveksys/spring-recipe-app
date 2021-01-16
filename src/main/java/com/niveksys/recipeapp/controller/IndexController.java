package com.niveksys.recipeapp.controller;

import java.util.Optional;

import com.niveksys.recipeapp.model.Category;
import com.niveksys.recipeapp.model.UnitOfMeasure;
import com.niveksys.recipeapp.repository.CategoryRepository;
import com.niveksys.recipeapp.repository.UnitOfMeasureRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({ "", "/", "/index" })
    public String getIndexPahe() {

        Optional<Category> categoryOptional = this.categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = this.unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("CAT ID is: " + categoryOptional.get().getId());
        System.out.println("UOM ID is: " + unitOfMeasureOptional.get().getId());

        return "index";
    }

}
