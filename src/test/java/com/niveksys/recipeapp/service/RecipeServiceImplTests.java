package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.RecipeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RecipeServiceImplTests {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getRecipes() throws Exception {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeData = new HashSet<Recipe>();
        recipeData.add(recipe);

        when(this.recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = this.recipeService.getRecipes();
        assertEquals(1, recipes.size());

        verify(this.recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe returnRecipe = this.recipeService.findById(1L);

        assertNotNull(returnRecipe, "Null recipe returned");
        verify(this.recipeRepository, times(1)).findById(anyLong());
        verify(this.recipeRepository, never()).findAll();
    }
}
