package MitchellChallenge.VehicleApplication.VehicleService;

import MitchellChallenge.VehicleApplication.VehicleException.VehiclesInfoNotFoundException;
import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import MitchellChallenge.VehicleApplication.VehicleRepository.VehicleInfoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
    public List<JsonNode> GetAllVehiclesInfo() throws IOException, VehiclesInfoNotFoundException {
        List<Vehicle> allVehicleInfo =(List<Vehicle>) vehicleInfoRepository.findAll();  // fetches all vehicle info from Data Reposiotory
        Vehicle vehicleList=new Vehicle();
        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonNode> jsonList=new ArrayList<JsonNode>();
        System.out.println("Json list"+jsonList+" size: "+allVehicleInfo.size());
        for(Vehicle info: allVehicleInfo){
            vehicleList.setId(info.getId());
            vehicleList.setYear(info.getYear());
            vehicleList.setMake(info.getMake());
            vehicleList.setModel(info.getModel());
            String vehiclesListJsonString = objectMapper.writeValueAsString(vehicleList);
            JsonNode jsonNode = objectMapper.readTree(vehiclesListJsonString);
            jsonList.add(jsonNode);
        }
        if(jsonList==null) throw new VehiclesInfoNotFoundException("No Vehicles Information Found");

        return jsonList;
    }
    public JsonNode VehicleInfoById(Integer Id) throws IOException, VehiclesInfoNotFoundException {
        Optional<Vehicle>  findByIdResult = vehicleInfoRepository.findById(Id);
        if(!findByIdResult.isPresent())
         throw new VehiclesInfoNotFoundException("No Vehicle Information Found for the given ID");
        ObjectMapper objectMapper = new ObjectMapper();
        String vehiclesInfoJsonString = objectMapper.writeValueAsString(findByIdResult.get());
        JsonNode jsonNode = objectMapper.readTree(vehiclesInfoJsonString);
        if(jsonNode == null) throw new VehiclesInfoNotFoundException("No Vehicle Information Found for the given ID");
        return jsonNode;
    }
    public Vehicle createNewVehicleDS(Vehicle vehicle) throws VehiclesInfoNotFoundException {
        Optional<Vehicle> findByIdResult = vehicleInfoRepository.findById(vehicle.getId());
            if(!findByIdResult.isPresent())
                return vehicleInfoRepository.save(vehicle);
            else
                throw new VehiclesInfoNotFoundException("Please give new Id to create/insert it");
    }
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
    public void deleteVehicleInfoService(Integer Id) throws VehiclesInfoNotFoundException{
        Optional<Vehicle> findByIdResult = vehicleInfoRepository.findById(Id);

        if(findByIdResult.isPresent())
            vehicleInfoRepository.deleteById(Id);
        else
            throw new VehiclesInfoNotFoundException("Vehicle Info not found to delete");
    }
}

