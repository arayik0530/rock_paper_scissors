# Rock Paper Scissors Game API

Welcome to the **Rock Paper Scissors Game API**! This project implements a RESTful API for playing the classic game of Rock Paper Scissors against an AI. The AI uses various strategies to challenge human players, and the application is optimized for high availability and performance using Kubernetes with multiple replicas and load balancing.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
    - [Authentication](#authentication)
    - [Game Management](#game-management)
- [AI Strategies](#ai-strategies)
- [Kubernetes Deployment](#kubernetes-deployment)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Multi-Strategy AI**: The AI adapts using different strategies to improve its chances of winning against human players.
- **Authentication**: Users must register and log in to play the game, ensuring a secure and personalized experience.
- **Statistics**: Track game statistics such as win/loss records.
- **Kubernetes Optimized**: Deployed using Kubernetes with multiple replicas and load balancing to ensure scalability and availability.
- **Spring Boot 3**: Built using Spring Boot 3, providing a robust and modern Java framework for building scalable web applications.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- Docker (for containerization)
- Kubernetes (for deployment)


Once the application is running, it will be available at `http://localhost:8090`.

## API Endpoints

### Authentication

- **Register**: `/api/auth/signup`
    - Method: `POST`
    - Body: `{ "name": "testUser", "password": "testPassword" }`

- **Login**: `/api/auth/login`
    - Method: `POST`
    - Body: `{ "name": "testUser", "password": "testPassword" }`
    - Response: `{ "token": "bearerToken" }`

### Game Management

- **Start Game**: `/api/game/start`
    - Method: `GET`
    - Headers: `Authorization: Bearer <token>`
    - Response: `{ "gameId": 1 }`

- **Play**: `/api/game/play`
    - Method: `POST`
    - Headers: `Authorization: Bearer <token>`
    - Body: `{ "playerMove": "ROCK", "playerLastMove": "PAPER", "gameId": 1 }`
    - Response: `{ "outcome": "WIN", "gameResult": { "playerScore": 1, "computerScore": 0 } }`

- **Finish Game**: `/api/game/{gameId}finish`
    - Method: `GET`
    - Headers: `Authorization: Bearer <token>`
    - Response: `{ "playerScore": 2, "computerScore": 1 }`

- **Get Statistics**: `/api/game/get-statistics`
    - Method: `GET`
    - Headers: `Authorization: Bearer <token>`
    - Response: `{ "totalGames": 10, "totalWins": 6, "totalLosses": 4 }`

## AI Strategies

The AI in this application uses various strategies to play against the human player, including:

1. **Random Selection**: The AI randomly selects a move.
2. **Pattern Recognition**: The AI tries to recognize patterns in the human player's moves and predict the next move.
3. **Frequency Analysis**: The AI analyzes the frequency of the human player's past moves to make a decision.
4. **Counter Strategy**: The AI counters the most frequent moves made by the player.

These strategies are combined and used dynamically to make the game more challenging.

## Kubernetes Deployment

The application is optimized for deployment on Kubernetes with the following features:

- **Multiple Replicas**: The application runs with multiple replicas to ensure high availability.
- **Load Balancing**: Kubernetes load balancers distribute the traffic evenly across replicas.
- **Scalability**: The application can be scaled horizontally by increasing the number of replicas.

## Running Tests

To run the unit and integration tests:

```bash
mvn test
