package com.company;

import java.util.Random;

    public class StudentFactory {
        private static final int[] taskAmount = {10, 20, 100};

        private static final Random random = new Random();

        private StudentFactory() {
        }

        public static Student getStudent() {
            final int taskCount = taskAmount[random.nextInt(taskAmount.length)];
            final Subject subject = Subject.values()[random.nextInt(Subject.values().length)];

            final var student = new Student(subject, taskCount);

            System.out.printf("GENERATOR: Student: %-15s Subject: %-12s Tasks: %-8s\n",
                    student.getName(),
                    subject.name(),
                    taskCount);

            return student;
        }
    }


