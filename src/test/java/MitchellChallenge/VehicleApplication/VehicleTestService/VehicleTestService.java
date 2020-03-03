/**
 * MitchellChallenge.VehicleApplication.VehicleService package inscribes a class which implements CRUD operations from VehicleService Interface
 * @author Keerti Keerti
 * @version 1.0
 * @since 25-Feb-2020
 */
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

    /**
     * initializes the mockito
     */
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * testGetAllVehicles method checks whether the vehicle application service method is returning all the vehicles upon calling that method.
     * Values returned are from Mockito instead of hitting the Database
     * @throws IOException
     * @throws VehiclesInfoNotFoundException
     */
    @Test
    public void testGetAllVehicles() throws IOException, VehiclesInfoNotFoundException {
        List<Vehicle> toDoList = new ArrayList<Vehicle>();
        toDoList.add(new Vehicle(1, 2019, "350-GT","Lamborgini"));
        toDoList.add(new Vehicle(2, 2019, "450-GT","Lamborgini"));
        when(vehicleInfoRepository.findAll()).thenReturn(toDoList);

        List<Vehicle> result = vehicleServiceImplementation.GetAllVehiclesInfo();
        assertEquals(2,result.size());
    }

    /**
     * testGetVehicleById method checks whether the vehicle application service method is returning the valid vehicle object upon calling that method.
     * Values returned are from Mockito instead of hitting the Database
     * @throws IOException
     * @throws VehiclesInfoNotFoundException
     */
    @Test
    public void testGetVehicleById() throws IOException, VehiclesInfoNotFoundException {
        Vehicle vehicle = new Vehicle(1, 2019, "A01","Audi");
        when(vehicleInfoRepository.findById(1)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleServiceImplementation.VehicleInfoById(1);
        assertEquals(1,result.getId());
        assertEquals(2019,result.getYear());
        assertEquals("A01",result.getMake());
        assertEquals("Audi",result.getModel());
    }

    /**
     * testCreateVehicleDS method checks whether the vehicle application service method is returning the valid vehicle object upon creating that object in Database.
     * Values returned are from Mockito instead of hitting the Database
     * @throws IOException
     * @throws VehiclesInfoNotFoundException
     */
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

    /**
     * testUpdateVehicleInfo method checks whether the vehicle application service method is updating the valid vehicle object upon calling that method.
     * Values returned are from Mockito instead of hitting the Database
     * @throws IOException
     * @throws VehiclesInfoNotFoundException
     */
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

    /**
     * testDeleteVehicleInfo method checks whether the vehicle application service method is able to identify the correct vehicle object that needs to be deleted and hits the DB once
     * Values returned are from Mockito instead of hitting the Database
     * @throws IOException
     * @throws VehiclesInfoNotFoundException
     */
    @Test
    public void testDeleteVehicleInfo() throws Exception {
        Vehicle vehicle = new Vehicle(5,2020,"A1","Audi");
        when(vehicleInfoRepository.findById(5)).thenReturn(Optional.of(vehicle));
        vehicleServiceImplementation.deleteVehicleInfoService(5);
        verify(vehicleInfoRepository, times(1)).deleteById(5);
    }
}
