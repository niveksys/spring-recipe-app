package com.niveksys.recipeapp.controller;

import java.util.Optional;

import com.niveksys.recipeapp.model.Category;
import com.niveksys.recipeapp.model.UnitOfMeasure;
import com.niveksys.recipeapp.repository.CategoryRepository;
import com.niveksys.recipeapp.repository.UnitOfMeasureRepository;
import com.niveksys.recipeapp.service.RecipeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({ "", "/", "/index" })
    public String getIndexPahe(Model model) {
        model.addAttribute("recipes", this.recipeService.getRecipes());
        return "index";
    }

}
