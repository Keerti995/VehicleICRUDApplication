/**
 * @author Keerti Keerti
 * @version 1.0
 * @since 25-Feb-2020
 */
package MitchellChallenge.VehicleApplication.VehicleService;

import MitchellChallenge.VehicleApplication.VehicleException.VehiclesInfoNotFoundException;
import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import MitchellChallenge.VehicleApplication.VehicleRepository.VehicleInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImplementation {

    @Autowired
    private VehicleInfoRepository vehicleInfoRepository;

    /**
     * GetAllVehiclesInfo gets all the vehicle information from the data repository and return it in the form of List<JsonNode>
     * @return List<JsonNode> : all vehicle information
     * @throws IOException
     * @throws VehiclesInfoNotFoundException
     */
    public List<Vehicle> GetAllVehiclesInfo() throws IOException, VehiclesInfoNotFoundException {
        List<Vehicle> allVehicleInfo =(List<Vehicle>) vehicleInfoRepository.findAll();  // fetches all vehicle info from Data Reposiotory
        if(allVehicleInfo == null)       //if the fetched information from Data Repository is empty then return an exception that there is no vehicle info in repository
            throw new VehiclesInfoNotFoundException("No Vehicles Information Found");
        return allVehicleInfo;
    }

    /**
     * VehicleInfoById method takes the Id as input and fetches that IDs vehicle info from Data Repository and send it in the form of JSONNode
     * @param Id
     * @return Vehicle Info of requested Id in JsonNode
     * @throws IOException
     * @throws VehiclesInfoNotFoundException
     */
    public Vehicle VehicleInfoById(Integer Id) throws IOException, VehiclesInfoNotFoundException {
        Optional<Vehicle> findByIdResult = vehicleInfoRepository.findById(Id);     //Fetch the vehicle info of the requested Id from Database/Repo
        if(!findByIdResult.isPresent()) // If fetched info is empty then return an exception
            throw new VehiclesInfoNotFoundException("No Vehicle Information Found for the given ID");
        return findByIdResult.get();
    }

    /**
     * createNewVehicleDS takes the vehicle object to create, checks whether the new Id passed already exists in the database, if there, return exception else inseert it into database
     * @param vehicle
     * @return Vehicle object created is returned
     * @throws VehiclesInfoNotFoundException
     */
    public Vehicle createNewVehicleDS(Vehicle vehicle) throws VehiclesInfoNotFoundException {
        Optional<Vehicle> findByIdResult = vehicleInfoRepository.findById(vehicle.getId());
            if(!findByIdResult.isPresent())
                return vehicleInfoRepository.save(vehicle);
            else
                throw new VehiclesInfoNotFoundException("Please give new Id to create/insert it");
    }

    /**
     * updateVehicleInfoService method takes vehicle object which needs to be updated, checks whether the passed Id is already in DB, if not then throws exception else updates it.
     * @param vehicleUpdInfo
     * @return updated vehicle object
     * @throws VehiclesInfoNotFoundException
     */
    public Vehicle updateVehicleInfoService(@Valid Vehicle vehicleUpdInfo) throws VehiclesInfoNotFoundException{
        Optional<Vehicle> findByIdResult = vehicleInfoRepository.findById(vehicleUpdInfo.getId());
        if(!findByIdResult.isPresent()){
            throw new VehiclesInfoNotFoundException("Vehicle Info not found to update");
        }else{
            findByIdResult.get().setYear(vehicleUpdInfo.getYear());
            findByIdResult.get().setMake(vehicleUpdInfo.getMake());
            findByIdResult.get().setModel(vehicleUpdInfo.getModel());
            return vehicleInfoRepository.save(findByIdResult.get());
        }
    }

    /**
     * deleteVehicleInfoService method takes Id as the input, checks if it is in the DB, if so then deletes it else throws an exception
     * @param Id
     * @throws VehiclesInfoNotFoundException
     */
    public void deleteVehicleInfoService(Integer Id) throws VehiclesInfoNotFoundException{
        Optional<Vehicle> findByIdResult = vehicleInfoRepository.findById(Id);

        if(findByIdResult.isPresent())
            vehicleInfoRepository.deleteById(Id);
        else
            throw new VehiclesInfoNotFoundException("Vehicle Info not found to delete");
    }
}

