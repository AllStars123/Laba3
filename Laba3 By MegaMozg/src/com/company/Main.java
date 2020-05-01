package com.company;

public class Main {

    public static void main(String[] args) {
            try {
                Hydrac hydrac = new Hydrac();
                hydrac.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

