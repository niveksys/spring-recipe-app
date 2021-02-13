package com.niveksys.recipeapp.controller;

import com.niveksys.recipeapp.service.ImageService;
import com.niveksys.recipeapp.service.RecipeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/{id}/image/new")
    public String newImage(@PathVariable String id, Model model) {
        log.debug("NEW image form.");
        model.addAttribute("recipe", this.recipeService.findCommandById(Long.valueOf(id)));
        return "recipes/imageForm";
    }

    @PostMapping("/recipes/{id}/image")
    public String createOrUpdate(@PathVariable String id, @RequestParam("imageFile") MultipartFile file) {
        log.debug("CREATE a new image, or UPDATE a specific image, then redirect to SHOW.");
        this.imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipes/" + id;
    }

}
