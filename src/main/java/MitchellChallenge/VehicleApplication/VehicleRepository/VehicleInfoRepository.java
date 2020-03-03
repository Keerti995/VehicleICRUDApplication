package MitchellChallenge.VehicleApplication.VehicleRepository;

import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleInfoRepository extends CrudRepository<Vehicle,Integer> {

}
