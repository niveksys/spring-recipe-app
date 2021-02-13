package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.niveksys.recipeapp.command.IngredientCommand;
import com.niveksys.recipeapp.converter.IngredientCommandToIngredient;
import com.niveksys.recipeapp.converter.IngredientToIngredientCommand;
import com.niveksys.recipeapp.converter.UnitOfMeasureCommandToUnitOfMeasure;
import com.niveksys.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.niveksys.recipeapp.model.Ingredient;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.RecipeRepository;
import com.niveksys.recipeapp.repository.UnitOfMeasureRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTests {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientService ingredientService;

    public IngredientServiceImplTests() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
                new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
                new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    public void setUp() {
        this.ingredientService = new IngredientServiceImpl(this.recipeRepository, this.unitOfMeasureRepository,
                this.ingredientToIngredientCommand, this.ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // nwhen
        IngredientCommand returnCommand = this.ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        // the
        assertEquals(Long.valueOf(3L), returnCommand.getId());
        assertEquals(Long.valueOf(1L), returnCommand.getRecipeId());
        verify(this.recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void saveIngredientCommand() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(this.recipeRepository.save(any())).thenReturn(savedRecipe);

        // when
        IngredientCommand savedCommand = this.ingredientService.saveIngredientCommand(ingredientCommand);

        // then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(this.recipeRepository, times(1)).findById(anyLong());
        verify(this.recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void deleteById() throws Exception {
        // given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // when
        this.ingredientService.deleteById(1L, 3L);

        // then
        verify(this.recipeRepository, times(1)).findById(anyLong());
        verify(this.recipeRepository, times(1)).save(any(Recipe.class));
    }
}
