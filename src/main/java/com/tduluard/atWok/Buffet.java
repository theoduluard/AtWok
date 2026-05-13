package com.tduluard.atWok;

import java.util.Random;

public class Buffet {

    private final Component[] components;

    public Buffet(SimulationLogger logger) {
        components = new Component[4];
        components[0] = new Component("Fish", 1000, logger);
        components[1] = new Component("Meat", 1000, logger);
        components[2] = new Component("Vegetables", 1000, logger);
        components[3] = new Component("Noodles", 1000, logger);
    }

    public Component[] getComponents() {
        return components;
    }

    public void takeFood() throws InterruptedException {
        Random random = new Random();
        for (Component component : components) {
            int amount = random.nextInt(80) + 20;
            component.takeFood(amount);
        }
    }

    public static class Component {
        private final String name;
        private int amount;
        private final SimulationLogger logger;

        public Component(String name, int amount, SimulationLogger logger) {
            this.name = name;
            this.amount = amount;
            this.logger = logger;
        }

        public int getAmount() {
            return this.amount;
        }

        public synchronized void takeFood(int amount) throws InterruptedException {
            while (this.amount < amount) {
                wait();
            }
            this.amount -= amount;
            logger.sendBuffetLog("BUFFET_TAKE", amount + "g de " + this.name + " pris. Reste : " + this.amount + "g");
        }

        public synchronized void addFood(int amount) {
            synchronized (this) {
                this.amount += amount;
                logger.sendBuffetLog("BUFFET_REFILL", amount + "g de " + this.name + " ajoutés. Reste : " + this.amount + "g");
                notifyAll();
            }
        }
    }
}