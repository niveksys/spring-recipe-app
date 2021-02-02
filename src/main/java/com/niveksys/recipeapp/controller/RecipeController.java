package com.niveksys.recipeapp.controller;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.service.RecipeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({ "", "/" })
    public String list(Model model) {
        log.debug("=> RecipeController.list()");
        model.addAttribute("recipes", this.recipeService.getRecipes());
        return "recipes/index";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        log.debug("=> RecipeController.newRecipe()");
        model.addAttribute("recipe", new RecipeCommand());
        return "recipes/edit";
    }

    @PostMapping({ "", "/" })
    public String createOrUpdate(@ModelAttribute RecipeCommand command) {
        log.debug("=> RecipeController.createOrUpdate()");
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);
        return "redirect:/recipes/" + savedCommand.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable String id, Model model) {
        log.debug("=> RecipeController.show()");
        model.addAttribute("recipe", this.recipeService.findById(Long.valueOf(id)));
        return "recipes/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        log.debug("=> RecipeController.edit()");
        model.addAttribute("recipe", this.recipeService.findCommandById(Long.valueOf(id)));
        return "recipes/edit";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {

        log.debug("=> RecipeController.delete()");

        this.recipeService.deleteById(Long.valueOf(id));
        return "redirect:/recipes";
    }

}
