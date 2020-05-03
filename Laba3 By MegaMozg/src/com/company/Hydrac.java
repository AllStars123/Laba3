package com.company;

import java.util.HashMap;
import java.util.concurrent.*;

public class Hydrac {
    public final static int STUDENT_AMOUNT = 1;

    public static volatile boolean GENERATION_FINISHED = false;

    private BlockingQueue<Student> students;

    private HashMap<Subject, Thread> robots;

    public Hydrac() {
        this.students = new ArrayBlockingQueue<>(10);
        this.robots = new HashMap<>(3);

        setUpRobotsAndThreads();
    }

    public void start() throws InterruptedException {
        fillStudents();
        startRobots();
    }

    private void fillStudents() {
        new Thread(() -> {
            for (int i = 0; i < STUDENT_AMOUNT; ++i) {
                try {
                    students.put(StudentFactory.getStudent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            GENERATION_FINISHED = true;
        }).start();
    }

    private void startRobots() throws InterruptedException {
        for (var subj : Subject.values()) {
            robots.get(subj).start();
        }
        waitRobots();
    }

    private void waitRobots() throws InterruptedException {
        for (var subj : Subject.values()) {
            robots.get(subj).join();
        }
    }

    private void setUpRobotsAndThreads() {
        for (var subj : Subject.values()) {
            robots.put(subj, new Thread(new Robot(subj, students)));
        }
    }
}