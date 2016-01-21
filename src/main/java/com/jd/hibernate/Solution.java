package com.jd.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jd.hibernate.util.HibernateUtil;

public class Solution {

  public static List<Student> findAllStudent() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Student> result = null;
    try {
      transaction = session.beginTransaction();
      Query q = (Query) session.createQuery("from Student");
      result = q.list();
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      e.printStackTrace();
    }
    return result;
  }

  public static void saveStudent() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try {
      transaction = session.beginTransaction();

      Set<Course> courses = new HashSet<Course>();
      courses.add(new Course("Maths"));
      courses.add(new Course("Computer Science"));

      Student student1 = new Student("Eswar", courses);
      Student student2 = new Student("Joe", courses);
      session.save(student1);
      session.save(student2);

      transaction.commit();
    } catch (HibernateException e) {
      transaction.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void main(String[] args) {
    saveStudent();

    List<Student> students = findAllStudent();
    for (Student stu : students) {
      for (Course course : stu.getCourses()) {
        System.out.println(stu.getStudentName() + " === " + course.getCourseName());
      }
    }
  }
}
