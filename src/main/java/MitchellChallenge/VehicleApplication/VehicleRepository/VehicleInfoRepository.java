/**
 * MitchellChallenge.VehicleApplication.VehicleRepository inscibes an interface VehicleInfoRepository which extends CrudRepository<Vehicle,Integer> inturn containing all the functionality to interact with the database
 * @author Keerti Keerti
 * @version 1.0
 * @since 25-Feb-2020
 */
package MitchellChallenge.VehicleApplication.VehicleRepository;

import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleInfoRepository extends CrudRepository<Vehicle,Integer> {

}
