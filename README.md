# e-commerce app

A basic e-commerce application where you can browse products, add them to the cart and place orders. The application does not have users, so we have one mock global user. The frontend application allows you to use only some of the functionalities of the backend rest API, you can search for products, add them to the cart, place an order and view order history. There are also endpoints for adding and editing products, categories, etc., but they are not covered on the frontend.

## Starting the application

To run the application, we first need to turn on the database. To do this, we must have Docker installed on our computer. If we have it installed, just go to the docker folder and type `docker compose up -d` in the terminal.

To run the backend part, we need to open our application in our favorite IDE and run the application. However, if we want to do it from the terminal, just enter the command `mvn spring-boot:run` in the root folder.

To run the frontend part, just go to the frontend folder and enter the command `npm run dev`

We could also build our frontend part and serve it statically through the server, but here we turn on the frontend development server.

If we managed to run the application correctly, we should have 3 things running:
- PostgreSQL on port :5432
- backend server on port :8080
- frontend on port :5173

The application does not use SSL certificates, so everything is simply on http.

To get to the backend documentation just go to `http://localhost:8080/swagger-ui` in your browser, or you can use the included Postman collection.

To get to the app's home page, simply go to `http://localhost:5173/` in your browser.

The application will have data because each time the backend is starting, it feeds the database with dummy data.

## Technology stack

Database:
- PostgreSQL

Backend:
- Java 17 Adoptium
- Spring

Frontend
- JavaScript
- Vue 3 with Vite
