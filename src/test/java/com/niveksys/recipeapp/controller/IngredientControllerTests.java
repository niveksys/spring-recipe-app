package com.niveksys.recipeapp.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.niveksys.recipeapp.command.IngredientCommand;
import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.command.UnitOfMeasureCommand;
import com.niveksys.recipeapp.service.IngredientService;
import com.niveksys.recipeapp.service.RecipeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTests {

    @MockBean
    RecipeService recipeService;

    @MockBean
    IngredientService ingredientService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void list() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        when(this.recipeService.findCommandById(anyLong())).thenReturn(command);

        // when
        this.mockMvc.perform(get("/recipes/1/ingredients")).andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/list")).andExpect(model().attributeExists("recipe"));

        // then
        verify(this.recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void show() throws Exception {
        // given
        IngredientCommand command = new IngredientCommand();
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        command.setUom(uomCommand);
        when(this.ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        // when
        this.mockMvc.perform(get("/recipes/1/ingredients/2/show")).andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/show")).andExpect(model().attributeExists("ingredient"));

        // then
        verify(this.ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }
}
