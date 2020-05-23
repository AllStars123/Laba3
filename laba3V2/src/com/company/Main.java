package com.company;

public class Main {
    public static void main(String[] args) {
        try {
            Hydrocorpus hydrocorpus = new Hydrocorpus();
            hydrocorpus.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

