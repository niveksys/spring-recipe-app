package com.niveksys.recipeapp.controller;

import com.niveksys.recipeapp.service.RecipeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    public String list(@PathVariable String recipeId, Model model) {
        log.debug("=> IngredientController.list()");
        model.addAttribute("recipe", this.recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipes/ingredients/list";
    }
}
