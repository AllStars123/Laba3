package com.company;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.company.Hydrocorpus.GENERATION_FINISHED;


public class Robot extends Thread {
        private BlockingQueue<Student> students;

        public Robot() {
            this.students = new ArrayBlockingQueue(10);
        }

        @Override
        public void run() {
            try {
                while (!GENERATION_FINISHED || !students.isEmpty()) {
                    var student = students.take();

                    processTasks(student);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void put(Student student) throws InterruptedException {
            this.students.put(student);
        }

        private void processTasks(Student student) {
            if (student.getSubject().equals(Subject.FAKE)) {
                return;
            }

            final var tasks = student.getTasks();

            final String name = student.getName();
            final int taskAmount = tasks.size();
            int checked = 0;

            while (!tasks.isEmpty()) {
                for (int i = 0; i < 5; ++i) {
                    tasks.remove();

                    System.out.printf("ROBOT: Student: %-15s Subject: %-12s Tasks:%s/%s\n",
                            name,
                            student.getSubject().name(),
                            ++checked,
                            taskAmount);
                }
            }
        }
    }


