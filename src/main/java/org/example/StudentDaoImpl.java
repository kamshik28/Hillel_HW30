package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class StudentDaoImpl implements GenericDao<Student, Long> {

    private final EntityManager em;

    public StudentDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Student entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public Student findById(Long id) {
        return em.find(Student.class, id);
    }

    @Override
    public Student findByEmail(String email) {
        try {
            return em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Student> findAll() {
        return em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();
    }

    @Override
    public Student update(Student entity) {
        em.getTransaction().begin();
        Student updated = em.merge(entity);
        em.getTransaction().commit();
        return updated;
    }

    @Override
    public boolean deleteById(Long id) {
        Student student = findById(id);
        if (student != null) {
            em.getTransaction().begin();
            em.remove(student);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }
}
