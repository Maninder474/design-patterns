package com.opentext.threads;

public class PoliceThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Police Thread is running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Police Thread is finished");
        System.exit(0);
    }

    public static void main(String[] args) {

        Vault vault = new Vault(2500);
        AscendingHackerThread ascendingHackerThread = new AscendingHackerThread(vault);
        DecendingHackerThread descendingHackerThread = new DecendingHackerThread(vault);
        PoliceThread policeThread = new PoliceThread();
        ascendingHackerThread.setPriority(Thread.MAX_PRIORITY);

        ascendingHackerThread.start();
        descendingHackerThread.start();
        policeThread.start();
    }
}
