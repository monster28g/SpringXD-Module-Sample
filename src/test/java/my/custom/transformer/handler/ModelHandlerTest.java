package my.custom.transformer.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.custom.transformer.model.BaseModel;
import org.junit.Test;
import org.springframework.integration.transformer.MessageTransformationException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={
//        "file:src/main/resources/config/spring-module.xml"})
public class ModelHandlerTest {

    ModelHandler modelHandler = new ModelHandler();

    String msg = "{\n" +
            "    \"Hull/Compartments/CargoTanks/Tank11.Temperature.val\": [1456991309592, \"12.600002\", 1, 1456991359597, \"12.600002\", 1],\n" +
            "    \"Hull/Compartments/BallastWaterTanks/Tank12.Level.val\": [1456991344597, \"0.11362\", 1],\n" +
            "    \"Hull/Compartments/BallastWaterTanks/Tank8.Level.val\": [1456991344597, \"0.1\", 1],\n" +
            "    \"Mechanical/Machinery/DieselEngine/MainEngine1/AirCooler1.Inlet1.Temperature.val\": [1456991334600, \"2.016641\", 1],\n" +
            "    \"Hull/Compartments/CargoTanks/Tank10.Weight.val\": [1456991304607, \"16.676998\", 1, 1456991354598, \"16.676998\", 1],\n" +
            "    \"Mechanical/Machinery/DieselEngine/MainEngine1/ScavAirSystemSensor1.Pressure.val\": [1456991324598, \"0.0\", 1],\n" +
            "    \"Mechanical/Machinery/DieselEngine/MainEngine1/MainLOPump1.Inlet1.Pressure.val\": [1456991329596, \"0.0\", 1],\n" +
            "    \"Hull/Compartments/CargoTanks/Tank14.Volume.val\": [1456991304607, \"0.0\", 1, 1456991354598, \"0.0\", 1],\n" +
            "    \"Mechanical/Machinery/DieselEngine/MainEngine1/Cylinder6.CFWOutlet1.Temperature.val\": [1456991334600, \"17.720623\", 1],\n" +
            "    \"Hull/Compartments/CargoTanks/Tank2.Level.val\": [1456991304607, \"16.675999\", 1, 1456991354598, \"16.675999\", 1],\n" +
            "    \"Mechanical/Machinery/DieselEngine/MainEngine1/Piston3.COOutlet1.Temperature.val\": [1456991329596, \"17.967743\", 1],\n" +
            "    \"Mechanical/Machinery/DieselEngine/GeneratorEngine1/TurboCharger1.ExhGasOutlet1.Temperature.val\": [1456991314605, \"24.810745\", 1],\n" +
            "    \"Hull/Compartments/FuelOilTanks/Tank3.Level.val\": [1456991334600, \"0.37\", 1],\n" +
            "    \"Hull/Compartments/BallastWaterTanks/Tank11.Volume.val\": [1456991344597, \"5.71\", 1],\n" +
            "    \"Hull/Compartments/CargoTanks/Tank6.Pressure.val\": [1456991309592, \"0.09\", 1, 1456991359597, \"0.09\", 1],\n" +
            "    \"Hull/Compartments/BallastWaterTanks/Tank6.Volume.val\": [1456991344597, \"800.29004\", 1],\n" +
            "	 \"Hull/Bottom/Bilge/Well5.Level.hi.stVal\": [1456991339594, \"true\", 1, 1456991340594, \"false\", 1],\n" +
            " \"Mechanical/Machinery/DieselEngine/MainEngine1/Cylinder2.LinerWall.Temperature2.val\": [1456991339594, \"null\", 1,1456991339594, \" \", 1]\n" +
            "}";

    @Test
    public void testProcess() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> tweet = mapper.readValue(msg, new TypeReference<Map<String, Object>>() {
            });
            List<BaseModel> result = modelHandler.process(tweet);

            assertTrue(result.size() > 0);


        }catch (IOException e) {
                throw new MessageTransformationException("Unable to transform tweet: " + e.getMessage(), e);
            }
    }
}