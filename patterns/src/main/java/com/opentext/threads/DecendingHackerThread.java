package com.opentext.threads;

public class DecendingHackerThread extends HackerThread{
    public static final int Max_PASSWORD = 999999999;
    public DecendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int i = Max_PASSWORD; i >= 0; i--) {
            if (vault.isCorrectPassword(i)) {
                System.out.println(this.getName() + " hacked the vault! The code was " + i);
                System.exit(0);
            }
        }
    }
}
