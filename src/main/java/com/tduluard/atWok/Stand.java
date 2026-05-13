package com.tduluard.atWok;

public class Stand {
    private boolean chiefAvailable = true;
    private final SimulationLogger logger;

    public Stand(SimulationLogger logger) {
        this.logger = logger;
    }

    public synchronized void goToChief(Client client) throws InterruptedException {
        while (!chiefAvailable) {
            wait();
        }
        chiefAvailable = false;
        
        logger.sendStandLog("CHIEF_COOKING", client.index, "Cuisine en cours...");
        
        Thread.sleep(2000); 
        
        logger.sendStandLog("CHIEF_DONE", client.index, "Plat terminé !");
        
        chiefAvailable = true;
        notifyAll();
    }
}