package ru.kata.spring.boot_security.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kata.spring.boot_security.demo.configs.MyConfig;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = acac.getBean("communication", Communication.class);
        ///получение всех пользователей
        List<User> userList = communication.getAllUser();
        System.out.println(userList);
        // создане пользователя
        User user3 = new User();
        user3.setName("James");  //Thomas
        user3.setLastName("Brown"); //Shelby
        user3.setAge((byte) 67);
        user3.setId(3L);
        communication.saveUser(user3);
        // изминения пользователя
        user3.setId(3L);
        user3.setLastName("Shelby");
        user3.setName("Thomas");
        communication.update(user3);
        // удаление пользователя
        communication.deleteUser(user3);
        //        5ebfebe7cb975dfcf9

    }
}
