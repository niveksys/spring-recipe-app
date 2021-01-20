package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.RecipeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RecipeServiceImplTests {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.recipeService = new RecipeServiceImpl(this.recipeRepository);
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
    }
}
