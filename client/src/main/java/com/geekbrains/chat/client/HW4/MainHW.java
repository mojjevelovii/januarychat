package com.geekbrains.chat.client.HW4;

/*Создать три потока, каждый из которых выводит
определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
Используйте wait/notify/notifyAll.
 */

public class MainHW {
    private final Object monitor = new Object();
    private char currentLetter = 'A';

    public void printA() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A') {
                        monitor.wait();
                    }
                    System.out.println("A");
                    currentLetter = 'B';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void printB() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') {
                        monitor.wait();
                    }
                    System.out.println("B");
                    currentLetter = 'C';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C') {
                        monitor.wait();
                    }
                    System.out.println("C");
                    currentLetter = 'A';
                    monitor.notifyAll();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MainHW abc = new MainHW();
        new Thread(() -> {
            abc.printA();
        }).start();
        new Thread(() -> {
            abc.printB();
        }).start();
        new Thread(() -> {
            abc.printC();
        }).start();
    }
}
