-- Sample seed data for recipes
INSERT INTO recipe (id, title, description, servings) VALUES
('809389cf-8785-4956-ab79-53b66b911f4f', 'Pork Adobo', 'The classic Filipino dish', 4),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Vegetable Stir Fry', 'Quick and healthy vegetable dish', 2),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Spaghetti Carbonara', 'Classic Italian pasta dish', 4)
ON CONFLICT (id) DO NOTHING;

-- Sample ingredients for Pork Adobo
INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian) VALUES
('809389cf-8785-4956-ab79-53b66b911f4f', 'pork belly', 1.0, 'kg', false),
('809389cf-8785-4956-ab79-53b66b911f4f', 'garlic', 2.0, 'clove', true),
('809389cf-8785-4956-ab79-53b66b911f4f', 'bay leaf', 5.0, null, true),
('809389cf-8785-4956-ab79-53b66b911f4f', 'vinegar', 4.0, 'tbsp', true),
('809389cf-8785-4956-ab79-53b66b911f4f', 'soy sauce', 0.5, 'cup', true),
('809389cf-8785-4956-ab79-53b66b911f4f', 'peppercorn', 1.0, 'tbsp', true),
('809389cf-8785-4956-ab79-53b66b911f4f', 'water', 2.0, 'cup', true)
ON CONFLICT DO NOTHING;

-- Sample ingredients for Vegetable Stir Fry
INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian) VALUES
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'broccoli', 200.0, 'g', true),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'carrots', 150.0, 'g', true),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'bell pepper', 1.0, 'piece', true),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'soy sauce', 2.0, 'tbsp', true),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'garlic', 2.0, 'clove', true),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'vegetable oil', 1.0, 'tbsp', true)
ON CONFLICT DO NOTHING;

-- Sample ingredients for Spaghetti Carbonara
INSERT INTO recipe_ingredients (recipe_id, name, amount, unit, vegetarian) VALUES
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'spaghetti', 400.0, 'g', true),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'bacon', 150.0, 'g', false),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'eggs', 3.0, 'piece', true),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'parmesan cheese', 100.0, 'g', true),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'black pepper', 1.0, 'tsp', true)
ON CONFLICT DO NOTHING;

-- Sample instructions for Pork Adobo
INSERT INTO recipe_instructions (recipe_id, instruction, step) VALUES
('809389cf-8785-4956-ab79-53b66b911f4f', 'Cut pork belly into chunks', 0),
('809389cf-8785-4956-ab79-53b66b911f4f', 'Marinate pork with soy sauce and vinegar', 1),
('809389cf-8785-4956-ab79-53b66b911f4f', 'Sauté garlic until fragrant', 2),
('809389cf-8785-4956-ab79-53b66b911f4f', 'Add pork and brown on all sides', 3),
('809389cf-8785-4956-ab79-53b66b911f4f', 'Add marinade, bay leaves, and peppercorns', 4),
('809389cf-8785-4956-ab79-53b66b911f4f', 'Simmer for 30-45 minutes until tender', 5)
ON CONFLICT DO NOTHING;

-- Sample instructions for Vegetable Stir Fry
INSERT INTO recipe_instructions (recipe_id, instruction, step) VALUES
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Heat oil in a wok or large pan', 0),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Add garlic and stir-fry for 30 seconds', 1),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Add harder vegetables first (carrots, broccoli)', 2),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Add bell pepper and stir-fry for 2-3 minutes', 3),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Add soy sauce and toss everything together', 4),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Serve immediately while hot', 5)
ON CONFLICT DO NOTHING;

-- Sample instructions for Spaghetti Carbonara
INSERT INTO recipe_instructions (recipe_id, instruction, step) VALUES
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Cook spaghetti according to package instructions', 0),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Cook bacon until crispy, reserve fat', 1),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Beat eggs with grated parmesan and black pepper', 2),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Drain pasta, reserving some pasta water', 3),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Toss hot pasta with bacon and fat', 4),
('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Remove from heat, add egg mixture, toss quickly', 5)
ON CONFLICT DO NOTHING;
