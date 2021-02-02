package com.niveksys.recipeapp.service;

import java.util.Set;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.model.Recipe;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(Long id);
}
