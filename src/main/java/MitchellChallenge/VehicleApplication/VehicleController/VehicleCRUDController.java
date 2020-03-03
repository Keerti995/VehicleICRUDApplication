/**
 * This package aims at creating a CRUD operation controllers
 * @author Keerti Keerti
 * @version 1.0
 * @since 25-Feb-2020
 */
package MitchellChallenge.VehicleApplication.VehicleController;

import MitchellChallenge.VehicleApplication.VehicleException.VehiclesInfoNotFoundException;
import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import MitchellChallenge.VehicleApplication.VehicleService.VehicleServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * VehicleCRUDController class creates a controller layer which injects the services from Service layer.
 */
@RestController
public class VehicleCRUDController {

    /**
     * Instantiates VehicleServiceImplementation for accessing the service layer CRUD functionality
     */
    @Autowired(required = true)
    public VehicleServiceImplementation vehicleServiceImplementation;

    /**
     * getAllVehicles method injects the service layer getAllVehicles method & returns list of vehicles fetched also handles the VehicleInfoNotFoundException and IOException
     * @return list of vehicles fetched from Database
     */
   @GetMapping(value="/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
    public ResponseEntity<List<Vehicle>> getAllVehicles(){
        List<Vehicle> jsonString ;
        try {
            jsonString = vehicleServiceImplementation.GetAllVehiclesInfo();
        }
        catch(VehiclesInfoNotFoundException | IOException e)
        {
            return  new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
      return new ResponseEntity<List<Vehicle>>(jsonString,HttpStatus.ACCEPTED);
    }

    /**
     * getVehiclesById method injects the service layer getVehiclesById method & returns vehicle object fetched for the given ID also handles the VehicleInfoNotFoundException and IOException
     * @param Id
     * @return vehicle object fetched for the given ID
     */
    @GetMapping(value="/vehicles/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getVehiclesById(@PathVariable("Id") int Id){
        Vehicle jsonString;
        try {
            jsonString = vehicleServiceImplementation.VehicleInfoById(Id);
        }
        catch(VehiclesInfoNotFoundException | IOException e)
        {
            return  new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Vehicle>(jsonString,HttpStatus.ACCEPTED);
    }

    /**
     * createVehicleDS method injects the service layer createVehicleDS method & returns status with the message, handling VehiclesInfoNotFoundException.
     * @param vehicle
     * @return status and message of the executed method as a Response Entity
     */
    @PostMapping(value="/createVehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createVehicleDS(@Valid @RequestBody Vehicle vehicle) {
        try{
            vehicleServiceImplementation.createNewVehicleDS(vehicle);
            System.out.println("Vehicle Info Inserted Successful ");
        }catch (VehiclesInfoNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity("New Vehicle Data Successfully inserted", HttpStatus.ACCEPTED);
    }

    /**
     * updateVehicleInfo method injects the service layer updateVehicleInfo method & returns status with the message, handling VehiclesInfoNotFoundException.
     * @param vehicleupdinfo
     * @return status and message of the executed method as a Response Entity
     */
    @PutMapping(value = "/updateVehicleInfo", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateVehicleInfo(@Valid @RequestBody Vehicle vehicleupdinfo){
        try{
            vehicleServiceImplementation.updateVehicleInfoService(vehicleupdinfo);
            System.out.println("Updated Info Successfully");
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity("Updated Info Successfully", HttpStatus.ACCEPTED);
    }

    /**
     * deleteVehicleInfo method injects the service layer deleteVehicleInfo method & returns status with the message, handling VehiclesInfoNotFoundException.
     * @param Id
     * @return status and message of the executed method as a Response Entity
     */
    @DeleteMapping(value = "/deleteVehicleInfo/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteVehicleInfo(@PathVariable("Id") int Id) {
       try{
           vehicleServiceImplementation.deleteVehicleInfoService(Id);
           System.out.println("Deleted Info Successfully");
       }catch (VehiclesInfoNotFoundException e){
           return new ResponseEntity(e.getMessage(),HttpStatus.FORBIDDEN);
       }
       return new ResponseEntity("Deleted Info Successfully",HttpStatus.ACCEPTED);
    }

}
