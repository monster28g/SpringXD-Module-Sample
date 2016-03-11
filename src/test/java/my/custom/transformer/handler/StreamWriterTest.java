package my.custom.transformer.handler;

import my.custom.transformer.model.BaseModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class StreamWriterTest {
    private StreamWriter writer = new StreamWriter();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testFlush() throws Exception {
        List<BaseModel> models = new ArrayList<>();
        models.add(new BaseModel("Hull/Compartments/BallastWaterTanks/Tank12.Level.val", 1456991344597L, 0.11362f));
        assertNotNull(writer.flush(models));

    }
}