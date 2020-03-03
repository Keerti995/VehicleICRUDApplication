package MitchellChallenge.VehicleApplication.VehicleController;

import MitchellChallenge.VehicleApplication.VehicleException.VehiclesInfoNotFoundException;
import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import MitchellChallenge.VehicleApplication.VehicleService.VehicleServiceImplementation;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VehicleCRUDController {

    @Autowired(required = true)
    public VehicleServiceImplementation vehicleServiceImplementation;

   @GetMapping(value="/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<JsonNode>> getAllVehicles(){
        List<JsonNode> jsonString ;
        System.out.println("in controller");
        try {
            jsonString = vehicleServiceImplementation.GetAllVehiclesInfo();
        }
        catch(VehiclesInfoNotFoundException | IOException e)
        {
            return  new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
      return new ResponseEntity<List<JsonNode>>(jsonString,HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/vehicles/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getVehiclesById(@PathVariable("Id") int Id){
        JsonNode jsonString;
        try {
            jsonString = vehicleServiceImplementation.VehicleInfoById(Id);
        }
        catch(VehiclesInfoNotFoundException | IOException e)
        {
            return  new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<JsonNode>(jsonString,HttpStatus.ACCEPTED);
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
