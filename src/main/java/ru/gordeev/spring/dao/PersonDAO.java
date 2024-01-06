package ru.gordeev.spring.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gordeev.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final SessionFactory session;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(SessionFactory session, JdbcTemplate jdbcTemplate) {
        this.session = session;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        List<Person> people = session.getCurrentSession().createQuery("select t from Person t", Person.class).getResultList();
        return people;
    }

/*    public List<Person> index() {

        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class)); //BeanPropertyRowMapper заменяет стандартный маппер когда название параметров класса совпадают с названиями колонок
    }*/

    @Transactional(readOnly = true)
    public Person show(int id) {
/*        return jdbcTemplate.query("SELECT * FROM Person WHERE id =?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);*/
        return  session.getCurrentSession().get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
          Person newPerson = person;
/*        List<Person> people = new ArrayList<Person>(session.getCurrentSession().createQuery("select t from Person t", Person.class).getResultList());
          people.add(person);*/
          session.getCurrentSession().save(newPerson);
 /*       jdbcTemplate.update("INSERT INTO Person(name,age,email) VALUES (?,?,?)",person.getName(), person.getAge(), person.getEmail());
    */}

    @Transactional
    public void update(int id, Person updatePerson) {
        session.getCurrentSession().update(updatePerson);
/*
        Person person = session.getCurrentSession().get(Person.class,updatePerson.getId());
        System.out.println(person);
        person.setAge(updatePerson.getAge());
        person.setName(updatePerson.getName());
*/

    }

    @Transactional
    public void delete(int id) {
        session.getCurrentSession().remove(session.getCurrentSession().get(Person.class,id));
        //session.getCurrentSession().delete(person);
/*        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    */}

    @Transactional
    public void deleteAll() {
  /*      jdbcTemplate.update("DELETE FROM Person");
   */ }

/*    //////////////////////////////////////////////
    //Тестим производительность пакетной вставки//
    //////////////////////////////////////////////
    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person person: people
             ) {
            jdbcTemplate.update("INSERT INTO Person(name,age,email) VALUES (?,?,?)", person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Время на вставку 1000 записей: "+(after-before));
    }
    public void testBachUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person(name,age,email) VALUES (?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,people.get(i).getName());
                preparedStatement.setInt(2,people.get(i).getAge());
                preparedStatement.setString(3,people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        long after = System.currentTimeMillis();
        System.out.println("Время на вставку 1000 записей: "+(after-before));
    }


    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i,"person" + i, 30, "email" +i+ "@mail.ru"));
        }
        return people;
    }*/

}
