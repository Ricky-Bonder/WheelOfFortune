package RdF.Server;

public class ThreadGameStart extends Thread {
    int waitMin;

    /**
     * Game Start
     *
     * @param millis
     */
    public ThreadGameStart(int millis) {
        waitMin = millis;
    }

    /**
     * Run
     */
    public void run(){
        int user=0;
        try {
            sleep(waitMin);
        } catch (InterruptedException e) {
            if (user != 1) {
                //Server.serverError(client);
                e.printStackTrace();
            }
        }
    }
}