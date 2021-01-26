package com.niveksys.recipeapp.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.RecipeRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("=> RecipeServiceImpl.getReceipes()");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().forEach(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe Not Found");
        }

        return recipeOptional.get();
    }

}
