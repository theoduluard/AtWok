package com.tduluard.atWok;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Restaurant {
    
    private static final int MAX_PLACES = 5;
    private final Semaphore seat = new Semaphore(MAX_PLACES);

    private final Buffet buffet;
    private final Stand stand;
    private final SimulationLogger logger;

    public Restaurant(SimulationLogger logger) {
        this.logger = logger;
        this.buffet = new Buffet(logger);
        this.stand = new Stand(logger);
        
        Employee employee = new Employee(this.buffet, logger);
        employee.start();
    }

    public void takeASeat() throws InterruptedException {
        seat.acquire();
    }

    public void leaveTheSeat() {
        seat.release();
    }

    public Buffet getBuffet() { return buffet; }
    public Stand getStand() { return stand; }

    /**
     * Cette méthode remplace ton ancien "public static void main".
     * Elle est appelée par le SimulationController.
     */
    public void startService(int nbClients) {
        new Thread(() -> {
            logger.sendRestaurantLog("INFO", "=== DÉBUT DU SERVICE ===");
            List<Client> clients = new ArrayList<>();
            long beginning = System.currentTimeMillis();

            for (int i = 1; i <= nbClients; i++) {
                Client client = new Client(this, i, logger);
                client.start();
                clients.add(client);
                
                try { Thread.sleep(300); } catch (InterruptedException _) {}
            }

            for (Client client : clients) {
                try {
                    client.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            long end = System.currentTimeMillis();
            long diff = end - beginning;
            logger.sendRestaurantLog("INFO", "🏁 SERVICE TERMINÉ EN : " + (diff / 1000) + " secondes.");
            
        }).start();
    }
}