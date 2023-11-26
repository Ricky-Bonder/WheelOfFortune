package RdF.Client;

public class ThreadTimer extends Thread{
    int timeToWait;

    public ThreadTimer(int millis) {
        timeToWait = millis;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
