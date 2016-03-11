package my.custom.transformer.model;

public class BaseModel {
    private String key;
    private float value;
    private long timestamp;

    public BaseModel(String key, Long timestamp, Float value) {
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}