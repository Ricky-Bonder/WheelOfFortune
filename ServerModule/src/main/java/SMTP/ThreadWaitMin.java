package SMTP;

public class ThreadWaitMin extends Thread {

    public void run(){
        int waitMin= 600000;

        try {
            sleep(waitMin);
        } catch (InterruptedException e) {
        }

    }
}
