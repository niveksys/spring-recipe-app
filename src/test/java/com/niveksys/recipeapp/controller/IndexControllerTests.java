package com.niveksys.recipeapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.service.RecipeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

public class IndexControllerTests {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @InjectMocks
    IndexController controller;

    @Captor
    ArgumentCaptor<Set<Recipe>> recipesCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getIndexPage() {

        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipes.add(recipe1);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe2);

        when(this.recipeService.getRecipes()).thenReturn(recipes);

        String viewName = this.controller.getIndexPage(this.model);
        assertEquals("index", viewName);

        verify(this.recipeService, times(1)).getRecipes();
        verify(this.model, times(1)).addAttribute(eq("recipes"), recipesCaptor.capture());
        Set<Recipe> capturedArgs = recipesCaptor.getValue();
        assertEquals(2, capturedArgs.size());
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }
}
