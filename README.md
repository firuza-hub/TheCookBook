# TheCookBook


The Cook Book is a recipe app, where recipes are provided from a remote API.
User can  preview recipe, and upload pictures of own cooked meals to the recipes.
Users can see each other's meal images by selecting a recipe and long pressing the recipe header image.

There is a recipe list, recipe details, user meal images and camera screens in the app. These are the fragments attached to a main activity. Besides there, there is a login activity. User must be authorized to enter the app. Navigation is implemented via Navigation Controller.

Data is fetched from a firebase API, and there is a daily worker service that is saving data to local db. Only main recipe data is saved, user meal images are not saved, because that data is heavy.


Motion layout is implemented in recipe details page, when scrolling down the view.

The images in recipe list and in user meal images page are loaded using Glide, with an animated loader placeholder.

MVVM pattern is used, and the packaging of files is implemented by context, not by layer, to make the app scalable for future development.

For camera usage permission is requested, and user has two accept the permission to use user meal image functionality fully. User may still use the app without camera.

