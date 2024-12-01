# CSC207 for Group #13

Team Name: Genshin Impact 

Domain: Recipe Organizer, Management, and Recommendation

## Authors and Contributors

Team Members:

- Tim(Zhouquan) Liu

- Pengze Ai

- Yi Chen Liu

- Yinjunnan Xiong

## Summary of the Project's Purpose

The purpose of this project is to create a recipe organizer application that allows users to manage their recipes effectively, explore new recipes, and get recommendations based on their preferences and available ingredients. This project aims to provide an easy and accessible way for users to organize meal planning, discover new culinary ideas, and accommodate dietary needs. The goal is to solve the problem of keeping track of various recipes, managing meal types, and finding suitable recipes based on individual needs.


## Software Specification

The recipe organizer program should be capable of:

Allowing users to add, edit, and delete recipes.

Enabling users to categorize recipes by type (e.g., breakfast, lunch, dinner, dessert) and dietary preferences (e.g., vegetarian, vegan, gluten-free).

Providing a search function for users to find recipes by ingredients, recipe name, or category.

Allowing users to save favorite recipes for easy access.

Optionally suggesting recipes based on ingredients the user currently has available.

## User Stories:

Tim(Zhoquan) Liu: As a user, I need to log in into my account and create my own recipes. The recipe should contain a name, ingredients with ingredient names and quantity, and instructions. 
The application will store all recipes that I create.

User Story 2: As a user, I want to categorize my recipes by meal type and dietary preferences, so that I can easily find recipes that suit my needs or dietary restrictions.

User Story 3: As a user who doesn’t have my own recipes, I want to explore more recipes based on the ingredients I currently have and my preferences. I want to get recommendations by providing some keywords.

User Story 4: As a user, I want to mark recipes as my favorites so I can easily access them later when planning meals.

## Proposed Entities for the Domain:

### 1. Recipe:

Instance Variables:

name: The name of the recipe.

ingredients: A list of ingredients required for the recipe.

instructions: The steps required to prepare the dish.

category: Type of meal (e.g., breakfast, lunch, dinner, dessert).

dietary_preferences: Tags for dietary preferences (e.g., vegetarian, vegan).

### 2. User:

Instance Variables:

username: The name of the user.

saved_recipes: A list of recipes that the user has saved as favorites.

created_recipes: A list of recipes that the user has personally created.

### 3. Ingredient:

Instance Variables:

name: The name of the ingredient.

quantity: The amount required.

unit: The unit of measurement (e.g., grams, cups).

4. Category:

Instance Variables:

name: The name of the category (e.g., breakfast, lunch).

description: A description of the type of recipes in this category.

5. Search:

Instance Variables:

ingredient_list: A list of ingredients provided for searching.

category: The type of recipe being searched for.

dietary_preference: Dietary preferences to filter search results.

Features

The main features of the recipe organizer software include:

Recipe Management: Add, edit, and delete recipes.

Categorization: Categorize recipes by meal type and dietary preferences.

Search Functionality: Search for recipes by ingredients, name, or category.

Favorites: Save favorite recipes for easy access.

Recipe Suggestions: Get recipe recommendations based on available ingredients.