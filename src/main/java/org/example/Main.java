package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hillel-persistence-unit");
        EntityManager em = emf.createEntityManager();

        StudentDaoImpl studentDao = new StudentDaoImpl(em);

        Student student = new Student("Ivan", "Petrov", "ivan.petrov@example.com");
        studentDao.save(student);
        System.out.println("Saved student: " + student);

        Student found = studentDao.findById(student.getId());
        System.out.println("Found by ID: " + found);

        found.setLastName("Petrenko");
        studentDao.update(found);
        System.out.println("Updated: " + studentDao.findById(found.getId()));

        boolean deleted = studentDao.deleteById(found.getId());
        System.out.println("Deleted: " + deleted);

        em.close();
        emf.close();
    }
}
