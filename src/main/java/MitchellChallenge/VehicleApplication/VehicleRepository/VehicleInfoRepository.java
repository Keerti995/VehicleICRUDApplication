/**
 * @author Keerti Keerti
 * @version 1.0
 * @since 02-March-2020
 */
package MitchellChallenge.VehicleApplication.VehicleRepository;

import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleInfoRepository extends CrudRepository<Vehicle,Integer> {

}
