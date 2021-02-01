package covid.plattform.consumer.controller;
import covid.plattform.consumer.entities.User;
import covid.plattform.consumer.repositories.UserRepository;
import covid.plattform.consumer.security.Encryptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/covid")
public class CovidController {
    // Autowiring Interface for CRUD operations on the database
    @Autowired
    UserRepository userRepository;
    // Autowiring RestTemplate defined in the ConsumerApplication class
    @Autowired
    RestTemplate restTemplate;
    // Autowiring the Encryptor for encrypting and decrypting the data.
    @Autowired
    Encryptor encryptor;

    // REST Endpoint for querying the base64 encrypted Plot
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/kmeans")
    public String getKmeans() throws IOException, InterruptedException {
        String result;
        // executing the python script for reading the RFID-Chip
        Runtime.getRuntime().exec("python3 Reader.py");
        TimeUnit.SECONDS.sleep(5);
        // storing the result of the RFID-Chip
        File myFile = new File("user_id.txt");
        Scanner myScanner = new Scanner(myFile);
        String id = myScanner.nextLine();
        // Querying the Database for the encrypted RFID-Chip-id
        Optional<User> user = userRepository.findById(encryptor.encrypt(id));
        // If a user is found the then call the producer api
        if (user.isPresent()){
           result = restTemplate.getForObject("http://123414132rr3r.432z65hrtgerfw.3243t5ztgrerf.324t5rfw:2345terfw/covid/kmeans/" + encryptor.decode(user.get().getLocation()), String.class);
        }
        // if theres no user found then return "token got rejected" message
        else {
            result = "token got rejected";
        }
        Runtime.getRuntime().exec("rm user_id.txt");
        return result;
    }
}

