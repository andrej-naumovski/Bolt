package mk.edu.ukim.feit.bolt.api;

import mk.edu.ukim.feit.bolt.api.controllers.ControllerScanMarker;
import mk.edu.ukim.feit.bolt.api.repositories.RepositoryScanMarker;
import mk.edu.ukim.feit.bolt.api.services.ServiceScanMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {
		ServiceScanMarker.class,
		ControllerScanMarker.class,
		RepositoryScanMarker.class
})
public class BoltApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoltApiApplication.class, args);
	}
}
