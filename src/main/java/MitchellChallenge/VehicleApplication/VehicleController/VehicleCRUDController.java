/**
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

/**
 * VehicleCRUDController
 */
@RestController
public class VehicleCRUDController {

    @Autowired(required = true)
    public VehicleServiceImplementation vehicleServiceImplementation;

   @GetMapping(value="/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Vehicle>> getAllVehicles(){
        List<Vehicle> jsonString ;
        System.out.println("in controller");
        try {
            jsonString = vehicleServiceImplementation.GetAllVehiclesInfo();
        }
        catch(VehiclesInfoNotFoundException | IOException e)
        {
            return  new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
      return new ResponseEntity<List<Vehicle>>(jsonString,HttpStatus.ACCEPTED);
    }

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
