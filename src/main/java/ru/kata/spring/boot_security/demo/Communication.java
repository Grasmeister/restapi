package ru.kata.spring.boot_security.demo;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Arrays;
import java.util.List;

@Component
public class Communication {
    private final String URL = "http://94.198.50.185:7081/api/users";
    private String sessionID;
    private RestTemplate restTemplate;
    public String Cod = "";

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUser() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                });
        List<User> users = responseEntity.getBody();
        sessionID = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        return users;
    }

    public void saveUser(User user) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Cookie", sessionID.substring(0, 43));
        HttpEntity requestEntity = new HttpEntity(user, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
//        System.out.println("new user creat");
        Cod = Cod + responseEntity.getBody();
//        System.out.println(responseEntity.getBody());
    }

    public void update(User user) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Cookie", sessionID.substring(0, 43));
        HttpEntity requestEntity = new HttpEntity(user, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);
//        System.out.println("User update");
        Cod = Cod + responseEntity.getBody();
//        System.out.println(responseEntity.getBody());
    }

    public void deleteUser(User user) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + user.getId(), HttpMethod.DELETE, getHttpEntity(user), String.class);
//        System.out.println("User delete");
        Cod = Cod + responseEntity.getBody();
//        System.out.println(responseEntity.getBody());
        System.out.println(Cod);
    }

    public HttpEntity getHttpEntity(User user){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Cookie", sessionID.substring(0, 43));
        HttpEntity requestEntity = new HttpEntity(user, requestHeaders);
        return requestEntity;
    }

}
