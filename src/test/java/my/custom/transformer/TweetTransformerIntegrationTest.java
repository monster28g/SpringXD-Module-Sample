/*
 * Copyright 2014 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package my.custom.transformer;

/**
 * @author David Turanski
 */

import my.custom.transformer.model.BaseModel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.xd.dirt.server.singlenode.SingleNodeApplication;
import org.springframework.xd.dirt.test.SingleNodeIntegrationTestSupport;
import org.springframework.xd.dirt.test.SingletonModuleRegistry;
import org.springframework.xd.dirt.test.process.SingleNodeProcessingChain;
import org.springframework.xd.module.ModuleType;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.xd.dirt.test.process.SingleNodeProcessingChainSupport.chain;

/**
 * Unit tests a module deployed to an XD single node container.
 */
public class TweetTransformerIntegrationTest {

	private static SingleNodeApplication application;

	private static int RECEIVE_TIMEOUT = 5000;

	private static String moduleName = "tweet-transformer";

	/**
	 * Start the single node container, binding random unused ports, etc. to not conflict with any other instances
	 * running on this host. Configure the ModuleRegistry to include the project module.
	 */
	@BeforeClass
	public static void setUp() {

		application = new SingleNodeApplication().run();
		SingleNodeIntegrationTestSupport singleNodeIntegrationTestSupport = new SingleNodeIntegrationTestSupport
				(application);
		singleNodeIntegrationTestSupport.addModuleRegistry(new SingletonModuleRegistry(ModuleType.processor,
				moduleName));

	}

	/**
	 * Each test creates a stream with the module under test, or in general a "chain" of processors. The
	 * SingleNodeProcessingChain is a test fixture that allows the test to send and receive messages to verify each
	 * message is processed as expected.
	 */
	@Test
	public void test() {
		String streamName = "tweetTest";
        String tweet = "{\n" +
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
				" 	 \"Mechanical/Machinery/DieselEngine/MainEngine1/Cylinder2.LinerWall.Temperature2.val\": [1456991339594, \"null\", 1,1456991339594, \" \", 1]\n" +
				"}";

		String processingChainUnderTest = moduleName;

		SingleNodeProcessingChain chain = chain(application, streamName, processingChainUnderTest);

		chain.sendPayload(tweet);

        List<BaseModel> result = (List<BaseModel>) chain.receivePayload(RECEIVE_TIMEOUT);

		assertNotNull(result);
		chain.destroy();
	}

}
