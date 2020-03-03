/**
 * MitchellChallenge.VehicleApplication package inscibes a SpringBootApplication marking a configuration class VehicleApplication
 * @author Keerti Keerti
 * @version 1.0
 * @since 25-Feb-2020
 */
package MitchellChallenge.VehicleApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * VehicleApplication class triggers auto-configuration and component scanning and the Bean Methods described
 */
@SpringBootApplication
public class VehicleApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleApplication.class, args);
	}
}
