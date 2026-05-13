package com.tduluard.atWok;

public class Employee extends Thread {
    
    private final Buffet buffet;
    private final SimulationLogger logger;

    public Employee(Buffet buffet, SimulationLogger logger) {
        this.buffet = buffet;
        this.logger = logger;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            boolean hasRefilled = false;
            Buffet.Component[] components = this.buffet.getComponents();
            
            for (Buffet.Component component : components) {
                int amount = component.getAmount();
                if (amount < 200) {
                    component.addFood(1000 - amount);
                    hasRefilled = true;
                }
            }

            if (hasRefilled) {
                logger.sendEmployeeLog("EMP_ACTION", "L'employé a rechargé le buffet.");
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}