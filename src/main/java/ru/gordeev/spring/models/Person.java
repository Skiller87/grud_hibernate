package ru.gordeev.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/*import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;*/

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty") //аннотация @NotEmpty от hibernatevalidator добавляет валидацию полей
    @Size(min=2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name ="name")
    private String name;
    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name ="age")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    //private String surname;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public Person() {
    }

/*    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

 */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
