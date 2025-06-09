# Travel Gig – Hotel Reservation & AI Chatbot

A Spring Boot–based hotel reservation system with:
- **Search & filter** for hotels (by location, dates, star-rating, price, amenities)  
- **Room browsing** & **cart** to select multiple rooms and pay together via Stripe  
- **AI-powered chatbot** (OpenAI) to answer natural-language queries or extract search parameters  
- **JWT-secured** REST API  

---

## 🚀 Features

- **Hotel Search & Filter**  
  - Location, check-in/out, rooms, guests, star ratings, price range, amenities  
- **Room Listings & Cart**  
  - Browse available rooms per hotel  
  - Add multiple rooms to a client-side cart (localStorage)  
  - Multi-item checkout (Stripe Checkout Session)  
- **AI Chatbot**  
  - Natural-language dialog (“find me 3-star hotels with breakfast under $200 in Chicago”)  
  - Delegates parameter extraction to OpenAI, then runs your search backend  
- **Security**  
  - JWT-based authentication for all `/api/**` endpoints except login, public pages  
- **Persistence**  
  - Spring Data JPA entities for Hotel, Room, Booking, User  
- **Modular Code**  
  - Controllers → Services → Repositories → Database  
  - Chatbot integration lives in its own controller + service  

---

## 🛠 Tech Stack

| Layer           | Technology                        |
| --------------- | --------------------------------- |
| Framework       | Spring Boot 3, Spring MVC         |
| Data Access     | Spring Data JPA (Hibernate)      |
| Database        | MySQL / PostgreSQL                |
| Security        | Spring Security + jjwt (JWT)      |
| AI Integration  | OpenAI Chat API via spring-ai     |
| Payments        | Stripe Java SDK + Stripe Checkout |
| Frontend        | HTML, Bootstrap 4, jQuery        |
| Build & Tools   | Maven, Lombok, Jackson            |

---

## 📂 Project Modules

- **`controller`**  
  - `HotelController`  
  - `ChatbotController`  
  - `AuthController` (login/signup)  
  - `PaymentController`  
- **`service`**  
  - Business logic for Hotels, Bookings, Users, Payments  
- **`repository`**  
  - Spring Data JPA repos for each entity  
- **`model`**  
  - JPA `@Entity` definitions: `Hotel`, `Room`, `Booking`, `User`  
- **`config`**  
  - `SecurityConfig`, `OpenAiConfig`, `StripeConfig`  
- **`component`**  
  - `JwtTokenProvider`, `JwtAuthenticationFilter`, etc.  
- **`dto`**  
  - Request/response payloads (e.g. `ChatRequest`, `AuthRequest`)  

---

## 🏁 Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- MySQL or PostgreSQL
- A free/OpenAI API key
- Stripe account & API keys

### Clone & Build

```bash
git clone https://github.com/your-org/travel-gig.git
cd travel-gig
mvn clean package
