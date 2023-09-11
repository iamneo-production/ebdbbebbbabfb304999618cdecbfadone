package com.examly.springapp;


import com.examly.springapp.entity.Student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SpringappApplication {
    public static void main(String[] args) {
        SessionFactory sessionFactory = Hibernateutil.getSessionFactory();
        Session session = sessionFactory.openSession(); // Open a new session
        
        try {
            session.beginTransaction();

            Student student = new Student();
            student.setFirstName("Vikram");
            student.setLastName("V");
            student.setEmail("vikram@example.com");

            session.save(student);

            session.getTransaction().commit();

            // Retrieve student
            session.beginTransaction();

            Long studentId = student.getId();
            Student retrievedStudent = session.get(Student.class, studentId);
            
            // Display retrieved student's values
            System.out.println("Retrieved Student:");
            System.out.println("ID: " + retrievedStudent.getId());
            System.out.println("First Name: " + retrievedStudent.getFirstName());
            System.out.println("Last Name: " + retrievedStudent.getLastName());
            System.out.println("Email: " + retrievedStudent.getEmail());

            session.getTransaction().commit();
        } finally {
            session.close(); // Close the session
            Hibernateutil.closeSessionFactory();
        }
    }
}