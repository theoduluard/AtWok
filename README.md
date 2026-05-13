# AtWok

AtWok is a multi-threaded simulation of an all-you-can-eat wok restaurant, built using Java and Spring Boot. It models the concurrent behavior of clients, employees, and restaurant resources like seats, a buffet, and a cooking stand. The simulation's events are broadcast in real-time via WebSockets, allowing for live visualization of the restaurant's activity.

## How It Works

The simulation is built around several core components that interact in a multi-threaded environment:

- **Restaurant**: The main class that orchestrates the simulation. It manages the number of available seats using a Semaphore and initializes the other components. It starts the service for a specified number of clients.

- **Client**: Each client is a Thread that simulates a customer's journey through the restaurant:
  1. Enter: Acquires a seat.
  2. Choose Food: Takes a random amount of ingredients from the Buffet.
  3. Go to Stand: Waits in a queue for the chef to become available at the Stand.
  4. Eat: Spends a random amount of time eating after their food is cooked.
  5. Leave: Releases the seat and exits the restaurant.
  6. Buffet: Manages the stock of four food components: Fish, Meat, Vegetables, and Noodles. It uses synchronized methods to handle concurrent access from multiple clients taking ingredients and from the employee refilling them.

- **Employee**: A background (daemon) thread that continuously monitors the Buffet. If any ingredient amount drops below a threshold (200g), the employee refills it to its maximum capacity (1000g).

- **Stand**: Represents the cooking station with a single chef. It can only serve one client at a time, using synchronized blocks and wait()/notifyAll() to manage the queue of waiting clients.

WebSocket Communication:
- **WebSocketConfig** sets up a STOMP message broker.
- **SimulationController** provides an endpoint (/app/start) to begin the simulation via a WebSocket message.
- **SimulationLogger** sends detailed, structured logs of every action (client entering, employee refilling, chef cooking, etc.) to the /topic/logs WebSocket topic.

## Technology Stack

- **Backend**: Java
- **Framework**: Spring Boot
  - Spring Web
  - Spring WebSocket (with STOMP)
- **Build Tool**: Maven

## Getting Started

### Prerequisites

JDK (Java Development Kit) 17 or later.
Maven

### Running the Application

1. Clone the repository:
```bash
git clone https://github.com/theoduluard/AtWok.git
cd AtWok
```

2. Run the application using the Maven wrapper:
   - On macOS/Linux:
    ```bash
    ./mvnw spring-boot:run
    ```
        
    - On Windows:
    ```bash
    mvnw.cmd spring-boot:run
    ```

The server will start, and the WebSocket endpoint will be available.

### Interacting with the Simulation

To start the simulation and view the logs, you need a WebSocket client. You can use a dedicated tool, a browser extension (like "Simple WebSocket Client" for Chrome), or a simple HTML/JavaScript frontend.

1. Connect your WebSocket client to the following endpoint:
```ws://localhost:8080/ws-restaurant```

2. Subscribe to the topic /topic/logs to receive real-time updates from the simulation.

3. Start the simulation by sending a message (the body can be empty) to the destination:
```/app/start```

4. Observe the JSON-formatted log messages that are broadcast to the /topic/logs subscription, detailing the state and actions of every component in the restaurant.