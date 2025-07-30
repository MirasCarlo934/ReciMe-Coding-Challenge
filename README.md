# ReciMe-Coding-Challenge

## Local Development Setup
For local development, you can use the following steps in order to set up the environment:

### Option 1: Using Docker
The fastest way to get started is to use Docker. On the project root, run the following command to start the application:

```bash
docker-compose up
```

Or if you want to run it in detached mode:

```bash
docker-compose up -d
```

This will start the environment for local development where the application can be run via the IDE.

## Assumptions Made
1. The recipe exists as an isolated entity and does not have any dependencies on other entities. (i.e. ingredients are 
stored _as-is_ as described in the recipe upon creation)
2. Recipes are identified solely by their unique ID. This is to allow multiple recipes for the same dish but with 
different ingredients or instructions.
3. There is no checking for duplicate recipes. This is to allow users to create similar recipes with slight variations. 
This is also the current implementation in the ReciMe app.

### On being "vegetarian"
It is assumed that a recipe is vegetarian if all of its ingredients are vegetarian.
