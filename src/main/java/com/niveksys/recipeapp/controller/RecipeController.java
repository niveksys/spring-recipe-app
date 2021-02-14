package com.niveksys.recipeapp.controller;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.exception.NotFoundException;
import com.niveksys.recipeapp.service.RecipeService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

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
        log.debug("LIST all recipes.");
        model.addAttribute("recipes", this.recipeService.getRecipes());
        return "recipes/list";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        log.debug("NEW recipe form.");
        model.addAttribute("recipe", new RecipeCommand());
        return "recipes/edit";
    }

    @PostMapping({ "", "/" })
    public String createOrUpdate(@ModelAttribute RecipeCommand command) {
        log.debug("CREATE a new recipe, or UPDATE a specific recipe, then redirect to SHOW.");
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);
        return "redirect:/recipes/" + savedCommand.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable String id, Model model) {
        log.debug("SHOW info about a specific recipe.");
        model.addAttribute("recipe", this.recipeService.findById(Long.valueOf(id)));
        return "recipes/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        log.debug("EDIT form for a specific recipe.");
        model.addAttribute("recipe", this.recipeService.findCommandById(Long.valueOf(id)));
        return "recipes/edit";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        log.debug("DELETE a specific recipe, then redirect to LIST.");
        this.recipeService.deleteById(Long.valueOf(id));
        return "redirect:/recipes";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException() {
        log.error("Handling Not Found Exception.");
        return "404error";
    }
}
