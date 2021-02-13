package com.niveksys.recipeapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.service.ImageService;
import com.niveksys.recipeapp.service.RecipeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ImageController.class)
public class ImageControllerTests {
    @MockBean
    ImageService imageService;

    @MockBean
    RecipeService recipeService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void newImage() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        // when
        mockMvc.perform(get("/recipes/1/image/new")).andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        // then
        verify(recipeService, times(1)).findCommandById(anyLong());

    }

    @Test
    public void createOrUpdate() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain",
                "Mock Image Byte Stream".getBytes());

        // when
        mockMvc.perform(multipart("/recipes/1/image").file(multipartFile)).andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipes/1"));

        // then
        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }
}
