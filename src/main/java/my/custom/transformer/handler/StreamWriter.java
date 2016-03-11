package my.custom.transformer.handler;

import my.custom.transformer.model.BaseModel;
import org.apache.commons.collections.CollectionUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class StreamWriter {

    public static final String VALUE = "value";
    private String url;

    private String port;

    private String dbname;

    private String user;


    private String password;

    private BatchPoints batchPoints;

    public StreamWriter() {

        // TODO module args 로.
        url = "10.100.16.66";
        port = "8086";
        dbname = "hivaas";
        user = "root";
        password = "root";
       batchPoints = BatchPoints
                .database(dbname)
                .tag("host", url)
                .retentionPolicy("default")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

    }

    public List<BaseModel> flush(List<BaseModel> payload) {
        if(CollectionUtils.isNotEmpty(payload)) {
            org.influxdb.InfluxDB influxDB = InfluxDBFactory.connect("http://10.100.16.66:8086", "root", "root");
            influxDB.setWriteTimeout(10000L, TimeUnit.MILLISECONDS);

            if (!influxDB.describeDatabases().contains(dbname)) {
                influxDB.createDatabase(dbname);
            }

            payload.stream().forEach(e -> {
                Point point1 = Point.measurement(e.getKey())
                        .time(e.getTimestamp(), TimeUnit.MILLISECONDS)
                        .field(VALUE, e.getValue())
                        .build();
                batchPoints.point(point1);
            });

            try {
                influxDB.write(batchPoints);
            }catch (RuntimeException e)
            {
                // TODO: 3/10/16 need to retry (retry queue?)
            }
        }
        return payload;
    }
}