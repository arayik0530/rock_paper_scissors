# Rock-Paper-Scissors REST API

## Overview
This application is a web-based Rock-Paper-Scissors game designed to handle millions of daily active users. The game is optimized for performance and includes a PostgreSQL database to track game statistics.

## Features
- **Play Rock-Paper-Scissors:** The server plays against a human player, with the computer's moves slightly influenced by the player's previous choices.
- **Statistics Tracking:** The application records the total number of games played, as well as the number of wins, losses, and draws.

## Optimizations
- **Code-Level Optimizations:**
    - The game logic uses a predictive algorithm to exploit human patterns in decision-making.
    - The application uses efficient data structures and minimal resource consumption in processing game outcomes.

- **Database Integration:**
    - PostgreSQL is used for persisting game statistics, ensuring data integrity and allowing for historical data analysis.

## API Endpoints
- `POST /game/play`: Play a game by sending the player's move. Returns the result.
- `GET /game/statistics`: Retrieve the current statistics of all games played.
- host:8090/swagger-ui/index.html
## Setup
1. **Configure PostgreSQL Database:**
    - Ensure you have a PostgreSQL instance running.
    - Update the `application.properties` with your database credentials.

2. **Build and Run the Application:**
   ```bash
   mvn clean install
   mvn spring-boot:run
