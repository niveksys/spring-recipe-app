package com.niveksys.recipeapp.controller;

import com.niveksys.recipeapp.service.RecipeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({ "", "/", "/index" })
    public String getIndexPage(Model model) {
        log.debug("=> IndexController.getIndexPage()");
        model.addAttribute("recipes", this.recipeService.getRecipes());
        return "index";
    }

}
