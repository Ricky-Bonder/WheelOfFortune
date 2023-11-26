package RdF.Client;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CreaPartita {
    final int numPlayers = 3;
    private CyclicBarrier startGameBarrier;

    /**
     * Create Game
     */
    public void CreaPartita() {
    }

    //Classe per quando viene creata l'istanza partita e si attendono playersù

    /**
     * Wait player
     */
    public class WaitPlayers implements Runnable {
        @Override
        /**
         * Run
         */
        public void run() {
            String thisPlayer = Thread.currentThread().getName();
            //TODO iscrivere player alla partita creata - lato DB
            try {
                System.out.println(thisPlayer + " waiting for others to reach barrier.");
                startGameBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {}
        }
    }

    //Classe per quando la Barrier viene superata

    /**
     * If lobby is full
     */
    class PlayersReady implements Runnable {
        @Override
        /**
         * Run
         */
        public void run() {
            String thisPlayer = Thread.currentThread().getName();
            System.out.println("I 3 giocatori sono arrivati. starto partita...");
            //TODO avviare Partita
        }
    }

    /**
     * Simulation
     */
    public void runSimulation() {
        startGameBarrier = new CyclicBarrier(numPlayers, new PlayersReady());
        System.out.println("Spawning " + numPlayers + " worker threads");
        for (int i = 0; i < numPlayers; i++) {
            Thread player = new Thread(new WaitPlayers());
            player.setName("Thread " + i);
            player.start();
        }
    }

}
