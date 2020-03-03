/**
 * MitchellChallenge.VehicleApplication.VehicleTestController package inscribes a class VehicleTestController which tests the VehicleCRUDController class
 * @author Keerti Keerti
 * @version 1.0
 * @since 25-Feb-2020
 */
package MitchellChallenge.VehicleApplication.VehicleTestController;

import MitchellChallenge.VehicleApplication.VehicleApplication;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * VehicleTestController class tests the methods of VehicleCRUDController class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VehicleApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleTestController {

        private MockMvc mockMvc;

        @Autowired
        private WebApplicationContext wac;

        /**
         * Method setup initializes Mockito.
         */
        @Before
        public void setup() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
            MockitoAnnotations.initMocks(this);
        }

        /**
         * verifyGetAllVehicles checks when the get URI specified is called, the number of entities returned is 3 or not, since initially, total entities are 3 in DB
         * @throws Exception
         */
        @Test
        public void verifyGetAllVehicles() throws Exception{
                mockMvc.perform(MockMvcRequestBuilders.get("/vehicles").accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(3))).andDo(print());
        }

        /**
         * verifyGetVehicleById method checks whether vehicle application controller is returning the vehicle object with the specified ID(1)
         * @throws Exception
         */
        @Test
        public void verifyGetVehicleById() throws Exception{
                mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/1").accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$.id").exists())
                        .andExpect( jsonPath("$.year").exists())
                        .andExpect( jsonPath("$.make").exists())
                        .andExpect( jsonPath("$.model").exists())
                        .andExpect( jsonPath("$.id").value(1))
                        .andExpect( jsonPath("$.year").value(2019))
                        .andExpect( jsonPath("$.make").value("Veneno"))
                        .andExpect( jsonPath("$.model").value("350-GT"))
                        .andDo(print());
        }

        /**
         * verifyInvalidGetVehicleById method checks whether the vehicle application controller catches the exception when invalid ID is passed
         * @throws Exception
         */
        @Test
        public void verifyInvalidGetVehicleById() throws Exception{
                mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/111").accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("No Vehicle Information Found for the given ID"))
                        .andDo(print());
        }

        /**
         * verifyCreateNewVehicleDS method checks whether the vehicle application controller is creating the passed vehicle object successfully.
         * @throws Exception
         */
        @Test
        public void verifyCreateNewVehicleDS() throws Exception {
                 mockMvc.perform(MockMvcRequestBuilders.post("/createVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"9\", \"year\" : \"2020\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$").value("New Vehicle Data Successfully inserted"))
                        .andDo(print());
        }

        /**
         * verifyInvalidCreateNewVehicleDS2 method checks whether vehicle application controller catches the exception when the make of the vehicle object passed is empty
         * @throws Exception
         */
        @Test
        public void verifyInvalidCreateNewVehicleDS2() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/createVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"999\", \"year\" : \"2020\", \"make\" : \"\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$").value("Please check: Make & Model of a Vehicle should not be empty and the year should range between 1950 and 2050"))
                        .andDo(print());
        }

        /**
         * verifyInvalidCreateNewVehicleDS method checks whether the vehicle application controller catches the execption when we are trying to create vehicle object with already existing Id
         * @throws Exception
         */
        @Test
        public void verifyInvalidCreateNewVehicleDS() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/createVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"year\" : \"2020\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$").value("Please give new Id to create/insert it"))
                        .andDo(print());
        }

        /**
         * verifyDeleteVehicleInfo method checks whether the vehicle application controller is returning valid message after deleting successfully.
         * @throws Exception
         */
        @Test
        public void verifyDeleteVehicleInfo() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/deleteVehicleInfo/3").accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("Deleted Info Successfully"))
                        .andDo(print());
        }

        /**
         * verifyInvalidDeleteVehicleInfo method checks whether the vehicle application controller is returning valid message after it was unable to delete it.
         * @throws Exception
         */
        @Test
        public void verifyInvalidDeleteVehicleInfo() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/deleteVehicleInfo/99").accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$").value("Vehicle Info not found to delete"))
                        .andDo(print());
        }

        /**
         * verifyUpdateVehicleInfo method checks whether the vehicle application controller is able to update the passed vehicle object upon sending valid vehicle object
         * @throws Exception
         */
        @Test
        public void verifyUpdateVehicleInfo() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.put("/updateVehicleInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"year\" : \"2020\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("Updated Info Successfully"))
                        .andDo(print());
        }
        /**
         * verifyInvalidUpdateVehicleInfo2 method checks whether the vehicle application controller is able to return valid message when its unabel to update the object upon encountering problem-1950<year<2050
         * @throws Exception
         */
        @Test
        public void verifyInvalidUpdateVehicleInfo2() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.put("/updateVehicleInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"year\" : \"2070\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("Please check: Make & Model of a Vehicle should not be empty and the year should range between 1950 and 2050"))
                        .andDo(print());
        }

        /**
         * verifyInvalidUpdateVehicleInfo method checks whether the vehicle application controller is able to return valid message when its unabel to update the object upon encountering some problem - Id is not existing to update
         * @throws Exception
         */
        @Test
        public void verifyInvalidUpdateVehicleInfo() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.put("/updateVehicleInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"10\", \"year\" : \"2020\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("Vehicle Info not found to update"))
                        .andDo(print());
        }
}
