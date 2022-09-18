import lombok.Data;

import java.util.List;

@Data
public class TestSpeedEntity {

    private String key;

    private String title;

    private List<TestSpeedEntity> children;

}
