package com.niveksys.recipeapp.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import com.niveksys.recipeapp.model.Notes;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.service.RecipeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {
    @MockBean
    RecipeService recipeService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void getRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Notes notes = new Notes();
        notes.setRecipeNotes("Notes");
        recipe.setNotes(notes);

        when(this.recipeService.findById(anyLong())).thenReturn(recipe);

        this.mockMvc.perform(get("/recipe/show/1")).andExpect(status().isOk()).andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }
}
