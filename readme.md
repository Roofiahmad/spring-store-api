# 🛒 Spring Store App

A streamlined eCommerce backend built with **Spring Boot 4**, featuring JWT security, Stripe payments, and Docker support.

## 🚀 Key Features
* **Catalog:** Full CRUD for products/categories via JPA.
* **Payments:** Integrated Stripe Checkout flow.
* **Security:** Stateless authentication using **JWT**.
* **Operations:** Flyway migrations, Swagger UI docs, and Dockerization.

## 🛠️ Tech Stack
* **Core:** Java 17, Spring Boot 4, MySQL 8.
* **Tools:** Stripe SDK, MapStruct, Lombok, Flyway.
* **DevOps:** Docker, Docker Compose, Maven.

---

## ⚙️ Setup & Execution

### 1. Environment Configuration
Create a `.env` file in the project root:
```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/store
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=yourpassword
STRIPE_SECRET_KEY=sk_test_your_key
APP_JWT_SECRET_KEY=your_64_char_secret
```
### 2. How to Run the Project
#### Option A: Using Docker (Recommended) 
This is the fastest way to get started as it handles the database and networking for you. 
Open your terminal in the project root.

Run the following command:
````bash
docker-compose up --build
````
The app will be live once you see Started StoreApplication in the logs.

#### Option B: Local Development (Manual)
Ensure a MySQL database named store is running locally. Run via Maven:

````bash
./mvnw clean spring-boot:run
````

[!TIP] Live API Documentation: You can view the live production documentation here:

[🔗 Swagger UI - Production](https://store-ws.roofiahmad-homelabs.my.id/swagger-ui/index.html)

[📪 Postman Documentation](https://documenter.getpostman.com/view/11658732/2sBXVbGYsC#24452afe-677e-4fb1-8ca7-c48428df8301)