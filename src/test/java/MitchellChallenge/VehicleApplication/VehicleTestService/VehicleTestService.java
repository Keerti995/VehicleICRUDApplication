package MitchellChallenge.VehicleApplication.VehicleTestService;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import MitchellChallenge.VehicleApplication.VehicleException.VehiclesInfoNotFoundException;
import MitchellChallenge.VehicleApplication.VehicleModel.Vehicle;
import MitchellChallenge.VehicleApplication.VehicleRepository.VehicleInfoRepository;
import MitchellChallenge.VehicleApplication.VehicleService.VehicleServiceImplementation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.Id;

@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleTestService {
    @Mock
    private VehicleInfoRepository vehicleInfoRepository;

    @InjectMocks
    private VehicleServiceImplementation vehicleServiceImplementation;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetAllVehicles() throws IOException, VehiclesInfoNotFoundException {
        List<Vehicle> toDoList = new ArrayList<Vehicle>();
        toDoList.add(new Vehicle(1, 2019, "350-GT","Lamborgini"));
        toDoList.add(new Vehicle(2, 2019, "450-GT","Lamborgini"));
        when(vehicleInfoRepository.findAll()).thenReturn(toDoList);

        List<Vehicle> result = vehicleServiceImplementation.GetAllVehiclesInfo();
        assertEquals(2,result.size());
    }
    @Test
    public void testGetVehicleById() throws IOException, VehiclesInfoNotFoundException {
        Vehicle vehicle = new Vehicle(1, 2019, "A01","Audi");
        when(vehicleInfoRepository.findById(1)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleServiceImplementation.VehicleInfoById(1);
        assertEquals(new IntNode(1),result.getId());
        assertEquals(new IntNode(2019),result.getYear());
        assertEquals("A01",result.getMake());
        assertEquals("Audi",result.getModel());
    }
    @Test
    public void testCreateVehicleDS() throws VehiclesInfoNotFoundException {
        Vehicle vehicle = new Vehicle(5,2020,"A1","Audi");
        when(vehicleInfoRepository.save(vehicle)).thenReturn(vehicle);
        Vehicle result = vehicleServiceImplementation.createNewVehicleDS(vehicle);
        assertEquals(5,result.getId());
        assertEquals(2020,result.getYear());
        assertEquals("A1",result.getMake());
        assertEquals("Audi",result.getModel());
    }
    @Test
    public void testUpdateVehicleInfo() throws VehiclesInfoNotFoundException {
        Vehicle vehicle = new Vehicle(2,2020,"A2","Audi");
        when(vehicleInfoRepository.findById(2)).thenReturn(Optional.of(vehicle));
        when(vehicleInfoRepository.save(vehicle)).thenReturn(vehicle);
        Vehicle result = vehicleServiceImplementation.updateVehicleInfoService(vehicle);
        assertEquals(2,result.getId());
        assertEquals(2020,result.getYear());
        assertEquals("A2",result.getMake());
        assertEquals("Audi",result.getModel());
    }
    @Test
    public void testDeleteVehicleInfo() throws Exception {
        Vehicle vehicle = new Vehicle(5,2020,"A1","Audi");
        //Vehicle vehicle2 = new Vehicle(5,2020,"A1","Audi");
        when(vehicleInfoRepository.findById(5)).thenReturn(Optional.of(vehicle));
        vehicleServiceImplementation.deleteVehicleInfoService(5);
        verify(vehicleInfoRepository, times(1)).deleteById(5);
    }
}
