package com.niveksys.recipeapp.service;

import java.util.Set;

import com.niveksys.recipeapp.model.Recipe;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);
}
