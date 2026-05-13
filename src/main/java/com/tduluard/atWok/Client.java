package com.tduluard.atWok;

import java.util.Random;

public class Client extends Thread {

    private final Restaurant restaurant;
    public final int index;
    private final SimulationLogger logger;

    public Client(Restaurant restaurant, int i, SimulationLogger logger) {
        this.restaurant = restaurant;
        this.index = i;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            sitOnTable();
            chooseFood();
            goToStand();
            eat();
            goOut();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("A thread error occurs when running.");
        }
    }

    private void sitOnTable() throws InterruptedException {
        restaurant.takeASeat();
        logger.sendClientLog("ENTER", index, "S'installe à table.");
        sleep(200);
    }

    private void chooseFood() throws InterruptedException {
        logger.sendClientLog("CHOOSING", index, "Se sert au buffet.");
        
        Thread.sleep(1000); 
        
        restaurant.getBuffet().takeFood();
    }

    private void eat() throws InterruptedException {
        logger.sendClientLog("EATING", index, "Mange.");
        Random random = new Random();
        Thread.sleep((random.nextInt(3000) + 3000)); 
    }

    private void goToStand() throws InterruptedException {
        logger.sendClientLog("WAITING", index, "Attend le chef au stand.");
        restaurant.getStand().goToChief(this);
    }

    private void goOut() {
        restaurant.leaveTheSeat();
        logger.sendClientLog("LEAVING", index, "Quitte le restaurant.");
    }
}