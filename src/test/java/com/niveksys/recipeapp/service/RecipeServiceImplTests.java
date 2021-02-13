package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.converter.RecipeCommandToRecipe;
import com.niveksys.recipeapp.converter.RecipeToRecipeCommand;
import com.niveksys.recipeapp.exception.NotFoundException;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.RecipeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTests {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void getRecipes() throws Exception {
        // given
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeSet = new HashSet<Recipe>();
        recipeSet.add(recipe);

        when(this.recipeRepository.findAll()).thenReturn(recipeSet);

        // when
        Set<Recipe> returnRecipeSet = this.recipeService.getRecipes();

        // then
        assertEquals(1, returnRecipeSet.size());
        verify(this.recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void findById() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // when
        Recipe returnRecipe = this.recipeService.findById(1L);

        // then
        assertNotNull(returnRecipe, "Null recipe returned");
        verify(this.recipeRepository, times(1)).findById(anyLong());
        verify(this.recipeRepository, never()).findAll();
    }

    @Test
    public void findByIdNotFound() throws Exception {
        // given
        Optional<Recipe> recipeOptional = Optional.empty();

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // when
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            Recipe returnRecipe = recipeService.findById(1L);
            assertNotNull(returnRecipe, "Null recipe returned");
        });
        assertNotNull(exception.getMessage(), "Exception message should not be Null.");
    }

    @Test
    public void findCommandById() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        // when
        RecipeCommand returnCommand = recipeService.findCommandById(1L);

        // then
        assertNotNull(returnCommand, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void deleteById() throws Exception {
        // given
        Long id = Long.valueOf(2L);
        // no 'when()', since method has void return type

        // when
        this.recipeService.deleteById(id);

        // then
        verify(this.recipeRepository, times(1)).deleteById(anyLong());
    }
}
