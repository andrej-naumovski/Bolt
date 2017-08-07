package mk.edu.ukim.feit.bolt.api;

import mk.edu.ukim.feit.bolt.api.controllers.ControllerScanMarker;
import mk.edu.ukim.feit.bolt.api.models.Authority;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.repositories.RepositoryScanMarker;
import mk.edu.ukim.feit.bolt.api.services.ServiceScanMarker;
import mk.edu.ukim.feit.bolt.api.services.UserService;
import mk.edu.ukim.feit.bolt.api.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@SpringBootApplication
public class BoltApiApplication {

    static final Logger logger = LoggerFactory.getLogger(BoltApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BoltApiApplication.class, args);
		logger.info("Logging successfully initialized!");
		logger.error("Oops, an error occurred!");
		logger.debug("Debugging error!");
	}
}
