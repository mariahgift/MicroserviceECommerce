# MicroserviceECommerce
E29 Design your own microservice

What are the functionalities involved, and the microservices you've created

This project has 3 microservices including User, Product, and Cart.

User functionalities
- getAllUsers - show all the users, sellers or buyers.
- getUserById - view the information of specific user.
- addUser - it allows the user to create an account.
- updateUser - it allows the user to change his/her account details.
- deleteUser - delete the existing user.

Product functionalities
- getAllProducts - view all the products added by the seller.
- getProductById - view the product details of a specified product.
- addProduct - it allows only the user that has a role of "seller" to add product.
- updateProduct - update the product details, sellers cannot update the product they don't own.
- deleteProduct - delete product details, sellers cannot update the product they don't own.

Cart functionalities
- addToCart - allows only buyer to add product in the cart.
- removeCart - allows only buyer to remove product in the cart.
- productsInCart - allows the buyer to view the products in his/her cart.

Why do you think that the functionality needs to be isolated from another service?
Answer: The functionalities need to be separated to have an organized service. Through this, the developers will enable to update the system easily and helps them to fix errors without affecting other services. 
