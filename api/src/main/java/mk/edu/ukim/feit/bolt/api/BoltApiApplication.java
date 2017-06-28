package mk.edu.ukim.feit.bolt.api;

import mk.edu.ukim.feit.bolt.api.controllers.ControllerScanMarker;
import mk.edu.ukim.feit.bolt.api.repositories.RepositoryScanMarker;
import mk.edu.ukim.feit.bolt.api.services.ServiceScanMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {
		ServiceScanMarker.class,
		ControllerScanMarker.class,
		RepositoryScanMarker.class,
})
public class BoltApiApplication {

    static final Logger logger = LoggerFactory.getLogger(BoltApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BoltApiApplication.class, args);
        logger.info("Logging successfully initialized!");
        logger.error("Oops, an error occurred!");
        logger.debug("Debugging error!");
	}
}
