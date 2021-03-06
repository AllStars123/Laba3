package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

 public class Student {
        private String name;
        private static int number = 0;
        private Subject subject;
        private Queue<String> tasks;
        private int taskCount;

        public Student(Subject subject, int taskCount) {
            this.tasks = new LinkedList();
            this.subject = subject;
            this.taskCount = taskCount;

            name = "Student" + ++number;

            doTasks();
        }

        public String getName() {
            return name;
        }

        public Queue<String> getTasks() {
            return tasks;
        }

        public Subject getSubject() {
            return subject;
        }

        private void doTasks() {
            for (int i = 0; i < taskCount; ++i) {
                tasks.add(new Random().toString());
            }
        }
    }


