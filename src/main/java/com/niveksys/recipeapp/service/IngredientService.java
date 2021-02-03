package com.niveksys.recipeapp.service;

import com.niveksys.recipeapp.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
