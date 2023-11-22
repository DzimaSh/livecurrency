# Telegram Cryptocurrency Price Change Notifier Bot

## Overview

This project aims to create a Telegram bot that notifies users about cryptocurrency price changes. The bot monitors cryptocurrency prices from an API endpoint and alerts users if the price of a selected cryptocurrency increases or decreases by more than N percent since a specified time.

### Task Details

- **Notification Criteria:** Users receive notifications if a cryptocurrency's price changes by more than N percent since the start time.
- **API Endpoint:** Cryptocurrency prices are fetched from [MEXC API](https://api.mexc.com/api/v3/ticker/price).
- **Refresh Interval:** The application refreshes its state by making periodic requests to the API every S seconds.
- **User Interaction:** Users can restart the monitoring algorithm. The bot supports K users, and the K+1 user receives a notification about the bot's unavailability.

### Implemented Commands

The bot supports the following commands:

- **/start:** Start the bot and initiate monitoring.
- **/settings:** Access settings to configure the bot.
- **/check_currency:** Check the price of a given cryptocurrency.
- **/subscribe_currency:** Subscribe to receive notifications for a specific cryptocurrency.

### Technologies Used

- Java 21
- Spring Boot

## Setup and Usage

### Prerequisites

- Java 21 JDK installed
- Gradle build tool
- PostgresSQL server started

### Steps to Run

1. **Clone the Repository:**
   ```bash
   git clone <repository_url>
   cd telegram-crypto-bot
   ```

2. **Configure Application:**
   - Configure the Telegram bot token obtained from BotFather in `application.properties`.

3. **Build and Run:**

   Using Gradle:
   ```bash
   gradle build
   java -jar build/libs/<jar_file_name>.jar
   ```

5. **Interacting with the Bot:**
   - Start the bot by interacting with it on Telegram.
   - Select cryptocurrencies and set the notification threshold percentage.
   - The bot will notify users if the price changes by more than the specified percentage.

## Configuration

- Update `application.properties`:
  - `telegram.bot.token`: Telegram bot token obtained from BotFather.
  - Other configuration parameters as needed.

## Future Improvements

- Implement a database to store user preferences and monitor multiple cryptocurrencies.
- Enhance error handling and user notifications.
- Support additional functionalities such as subscribing to specific cryptocurrencies.
