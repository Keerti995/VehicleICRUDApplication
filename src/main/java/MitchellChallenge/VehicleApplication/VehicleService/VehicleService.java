/**
 * MitchellChallenge.VehicleApplication.VehicleService package describes an interface for all the CRUD methods to be implemented for Vehicle Application
 * @author Keerti Keerti
 * @version 1.0
 * @since 25-Feb-2020
 */
package MitchellChallenge.VehicleApplication.VehicleService;

import MitchellChallenge.VehicleApplication.VehicleException.VehiclesInfoNotFoundException;
import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import java.io.IOException;
import java.util.List;

/**
 * VehicleService Interface lists out the CRUD methods, which needs to be implemented in the services for Vehicle Application
 */
public interface VehicleService {
    public List<Vehicle> GetAllVehiclesInfo() throws IOException, VehiclesInfoNotFoundException;
    public Vehicle VehicleInfoById(Integer Id) throws IOException, VehiclesInfoNotFoundException;
    public Vehicle createNewVehicleDS(Vehicle vehicle) throws VehiclesInfoNotFoundException;
    public Vehicle updateVehicleInfoService(Vehicle vehicleUpdInfo) throws VehiclesInfoNotFoundException;
    public void deleteVehicleInfoService(Integer Id) throws VehiclesInfoNotFoundException;
}
