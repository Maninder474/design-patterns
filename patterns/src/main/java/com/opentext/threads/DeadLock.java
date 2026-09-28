package com.opentext.threads;

import java.util.Random;

public class DeadLock {
    private static Object roadA = new Object();
    private static Object roadB = new Object();
    public static class TrainA extends Thread {
        private DeadLock deadLock;
        private Random random = new Random();

        public TrainA(DeadLock deadLock) {
            this.deadLock = deadLock;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                deadLock.takeRoadA();
            }
        }
    }

    public static class TrainB extends Thread {
        private DeadLock deadLock;
        private Random random = new Random();

        public TrainB(DeadLock deadLock) {
            this.deadLock = deadLock;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(random.nextInt(1));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                deadLock.takeRoadB();
            }
        }
    }
    public void takeRoadA() {
        synchronized (roadA) {
            System.out.println("Road A is locked by " + Thread.currentThread().getName());

            synchronized (roadB) {
                System.out.println("train is passing through road A");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void takeRoadB() {
        synchronized (roadB){
            System.out.println("Road B is locked by " + Thread.currentThread().getName());
            synchronized (roadA){
                System.out.println("Train is passing though road B");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                    }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        TrainA trainA = new TrainA(deadLock);
        TrainB trainB = new TrainB(deadLock);
        trainA.start();
        trainB.start();
    }
}
