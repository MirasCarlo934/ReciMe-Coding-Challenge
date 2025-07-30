# ReciMe-Coding-Challenge

## Assumptions Made
1. The recipe exists as an isolated entity and does not have any dependencies on other entities. (i.e. ingredients are 
stored _as-is_ as described in the recipe upon creation)
2. Recipes are identified solely by their unique ID. This is to allow multiple recipes for the same dish but with 
different ingredients or instructions.
3. There is no checking for duplicate recipes. This is to allow users to create similar recipes with slight variations. 
This is also the current implementation in the ReciMe app.
