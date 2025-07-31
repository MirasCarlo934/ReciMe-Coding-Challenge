-- Create the recipes table
CREATE TABLE IF NOT EXISTS recipe (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    servings INTEGER NOT NULL
);

-- Create the ingredients table (for @ElementCollection)
CREATE TABLE IF NOT EXISTS recipe_ingredients (
    recipe_id VARCHAR(255) NOT NULL,
    amount REAL NOT NULL,
    unit VARCHAR(100),
    name VARCHAR(255) NOT NULL,
    vegetarian BOOLEAN NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipe(id) ON DELETE CASCADE
);

-- Create the instructions table (for @ElementCollection)
CREATE TABLE IF NOT EXISTS recipe_instructions (
    recipe_id VARCHAR(255) NOT NULL,
    instruction TEXT NOT NULL,
    step INTEGER NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipe(id) ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_recipe_ingredients_recipe_id ON recipe_ingredients(recipe_id);
CREATE INDEX IF NOT EXISTS idx_recipe_instructions_recipe_id ON recipe_instructions(recipe_id);
CREATE INDEX IF NOT EXISTS idx_recipe_ingredients_vegetarian ON recipe_ingredients(vegetarian);
CREATE INDEX IF NOT EXISTS idx_recipes_title ON recipe(title);
