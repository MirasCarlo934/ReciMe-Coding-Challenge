-- Seed database with initial data for recipes and ingredients

-- Insert recipes
INSERT INTO recipe (id, title, description, servings) VALUES
('809389cf-8785-4956-ab79-53b66b911f4f', 'Pork Adobo', 'The classic Filipino dish', 4),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Vegetable Stir Fry', 'Quick and healthy vegetable dish', 2),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Spaghetti Carbonara', 'Classic Italian pasta dish', 4)
ON CONFLICT (id) DO NOTHING;

-- Pork Adobo ingredients
INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'pork belly', 1.0, 'kg', false
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND name = 'pork belly'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'garlic', 2.0, 'clove', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND name = 'garlic'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'bay leaf', 5.0, null, true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND name = 'bay leaf'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'vinegar', 4.0, 'tbsp', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND name = 'vinegar'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'soy sauce', 0.5, 'cup', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND name = 'soy sauce'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'peppercorn', 1.0, 'tbsp', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND name = 'peppercorn'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'water', 2.0, 'cup', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND name = 'water'
);

-- Vegetable Stir Fry ingredients
INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'broccoli', 200.0, 'g', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND name = 'broccoli'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'carrots', 150.0, 'g', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND name = 'carrots'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'bell pepper', 1.0, 'piece', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND name = 'bell pepper'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'soy sauce', 2.0, 'tbsp', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND name = 'soy sauce'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'garlic', 2.0, 'clove', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND name = 'garlic'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'vegetable oil', 1.0, 'tbsp', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND name = 'vegetable oil'
);

-- Spaghetti Carbonara ingredients
INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'spaghetti', 400.0, 'g', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND name = 'spaghetti'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'bacon', 150.0, 'g', false
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND name = 'bacon'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'eggs', 3.0, 'piece', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND name = 'eggs'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'parmesan cheese', 100.0, 'g', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND name = 'parmesan cheese'
);

INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'black pepper', 1.0, 'tsp', true
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_ingredients
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND name = 'black pepper'
);

-- Insert instructions with proper conditional logic
-- Pork Adobo instructions
INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'Cut pork belly into chunks', 0
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND step = 0
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'Marinate pork with soy sauce and vinegar', 1
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND step = 1
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'Sauté garlic until fragrant', 2
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND step = 2
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'Add pork and brown on all sides', 3
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND step = 3
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'Add marinade, bay leaves, and peppercorns', 4
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND step = 4
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT '809389cf-8785-4956-ab79-53b66b911f4f', 'Simmer for 30-45 minutes until tender', 5
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = '809389cf-8785-4956-ab79-53b66b911f4f' AND step = 5
);

-- Vegetable Stir Fry instructions
INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Heat oil in a wok or large pan', 0
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND step = 0
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Add garlic and stir-fry for 30 seconds', 1
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND step = 1
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Add harder vegetables first (carrots, broccoli)', 2
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND step = 2
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Add bell pepper and stir-fry for 2-3 minutes', 3
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND step = 3
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Add soy sauce and toss everything together', 4
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND step = 4
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Serve immediately while hot', 5
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'a1b2c3d4-e5f6-7890-abcd-ef1234567890' AND step = 5
);

-- Spaghetti Carbonara instructions
INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Cook spaghetti according to package instructions', 0
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND step = 0
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Cook bacon until crispy, reserve fat', 1
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND step = 1
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Beat eggs with grated parmesan and black pepper', 2
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND step = 2
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Drain pasta, reserving some pasta water', 3
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND step = 3
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Toss hot pasta with bacon and fat', 4
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND step = 4
);

INSERT INTO recipe_instructions (recipe_id, instruction, step)
SELECT 'b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Remove from heat, add egg mixture, toss quickly', 5
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_instructions
    WHERE recipe_id = 'b2c3d4e5-f6g7-8901-bcde-f23456789012' AND step = 5
);
