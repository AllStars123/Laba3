package com.company;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


    public class Hydrocorpus {
        public final static int STUDENT_AMOUNT = 10;
        public static volatile boolean GENERATION_FINISHED = false;
        private BlockingQueue<Student> students;
        private HashMap<Subject, Robot> robots;

        public Hydrocorpus() {
            this.students = new ArrayBlockingQueue<>(10);
            this.robots = new HashMap<>(3);

            setUpRobotsAndThreads();
        }

        public void start() {
            try {
                fillStudents();
                separateTasks();
                startRobots();
                waitRobots();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void fillStudents() {
            new Thread(() -> {
                try {
                    for (int i = 0; i < STUDENT_AMOUNT; ++i) {
                        students.put(StudentFactory.getStudent());
                    }

                    GENERATION_FINISHED = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        private void setUpRobotsAndThreads() {
            for (var subj : Subject.values()) {
                robots.put(subj, new Robot());
            }
        }

        private void startRobots() {
            for (var subj : Subject.values()) {
                robots.get(subj).start();
            }
        }

        private void waitRobots() throws InterruptedException {
            for (var subj : Subject.values()) {
                robots.get(subj).join();
            }
        }

        private void separateTasks() {
            new Thread(() -> {
                Student student;

                try {
                    while (!GENERATION_FINISHED || !students.isEmpty()) {
                        student = students.take();

                        robots.get(student.getSubject()).put(student);
                    }

                    for (var subj : Subject.values()) {
                        robots.get(subj).put(new Student(Subject.FAKE, 0));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


