package com.niveksys.recipeapp.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.converter.RecipeCommandToRecipe;
import com.niveksys.recipeapp.converter.RecipeToRecipeCommand;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.RecipeRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private RecipeCommandToRecipe recipeCommandToRecipe;
    private RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
            RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
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

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detectedRecipe = this.recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = this.recipeRepository.save(detectedRecipe);
        log.debug("Saved Recipe ID: " + savedRecipe.getId());
        return this.recipeToRecipeCommand.convert(savedRecipe);
    }

}
