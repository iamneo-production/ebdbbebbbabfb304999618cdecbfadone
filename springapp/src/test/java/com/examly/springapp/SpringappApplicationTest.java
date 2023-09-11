package com.examly.springapp;

import com.examly.springapp.entity.Student;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpringappApplicationTest {
    private SessionFactory sessionFactory;

    @Before
    public void setup() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
    }

    @After
    public void cleanup() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testSaveAndRetrieveStudent() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Student student = new Student();
            student.setFirstName("Alice");
            student.setLastName("Smith");
            student.setEmail("alice@example.com");

            session.save(student);

            session.getTransaction().commit();

            session.beginTransaction();

            Long studentId = student.getId();
            Student retrievedStudent = session.get(Student.class, studentId);

            assertEquals("Alice", retrievedStudent.getFirstName());
            assertEquals("Smith", retrievedStudent.getLastName());
            assertEquals("alice@example.com", retrievedStudent.getEmail());

            session.getTransaction().commit();
        }
    }
}
