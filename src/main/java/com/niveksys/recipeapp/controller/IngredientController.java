package com.niveksys.recipeapp.controller;

import com.niveksys.recipeapp.service.IngredientService;
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
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    public String list(@PathVariable String recipeId, Model model) {
        log.debug("LIST all ingredients.");
        model.addAttribute("recipe", this.recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipes/ingredients/list";
    }

    @GetMapping("recipes/{recipeId}/ingredients/{id}/show")
    public String show(@PathVariable String recipeId, @PathVariable String id, Model model) {
        log.debug("SHOW info about a specific recipe.");
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipes/ingredients/show";
    }
}
