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
         *
         */
        @Before
        public void setup() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void verifyGetAllVehicles() throws Exception{
                mockMvc.perform(MockMvcRequestBuilders.get("/vehicles").accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(3))).andDo(print());
        }

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
        @Test
        public void verifyInvalidGetVehicleById() throws Exception{
                mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/111").accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("No Vehicle Information Found for the given ID"))
                        .andDo(print());
        }
        @Test
        public void verifyCreateNewVehicleDS() throws Exception {
                 mockMvc.perform(MockMvcRequestBuilders.post("/createVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"9\", \"year\" : \"2020\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$").value("New Vehicle Data Successfully inserted"))
                        .andDo(print());
        }
        @Test
        public void verifyInvalidCreateNewVehicleDS2() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/createVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"999\", \"year\" : \"2020\", \"make\" : \"\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$").value("Please check: Make & Model of a Vehicle should not be empty and the year should range between 1950 and 2050"))
                        .andDo(print());
        }
        @Test
        public void verifyInvalidCreateNewVehicleDS() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/createVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"year\" : \"2020\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$").value("Please give new Id to create/insert it"))
                        .andDo(print());
        }
        @Test
        public void verifyDeleteVehicleInfo() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/deleteVehicleInfo/3").accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("Deleted Info Successfully"))
                        .andDo(print());
        }
        @Test
        public void verifyInvalidDeleteVehicleInfo() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/deleteVehicleInfo/99").accept(MediaType.APPLICATION_JSON))
                        .andExpect( jsonPath("$").value("Vehicle Info not found to delete"))
                        .andDo(print());
        }
        @Test
        public void verifyUpdateVehicleInfo() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.put("/updateVehicleInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"year\" : \"2020\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("Updated Info Successfully"))
                        .andDo(print());
        }
        @Test
        public void verifyInvalidUpdateVehicleInfo2() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.put("/updateVehicleInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"year\" : \"2070\", \"make\" : \"A1\", \"model\" : \"Audi\" }")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").value("Please check: Make & Model of a Vehicle should not be empty and the year should range between 1950 and 2050"))
                        .andDo(print());
        }

        //Invalid Tests
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
