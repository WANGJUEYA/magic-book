import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSpeed {


    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        map();
        System.out.println("map >>>> " + (System.currentTimeMillis() - begin));
        begin = System.currentTimeMillis();
        entity();
        System.out.println("entity >>>> " + (System.currentTimeMillis() - begin));
    }

    public static final String KEY = "KEY";
    public static final String TITLE = "TITLE";
    public static final String CHILDREN = "CHILDREN";

    public static void map() {
        String key, title;
        List<Map<String, Object>> list;
        for (int index = 0; index < 5000; index++) {
            Map<String, Object> map = new HashMap<>();
            map.put(KEY, index + "-");
            map.put(TITLE, index + "-");
            list = new ArrayList<>();
            for (int child = 0; child < 10; child++) {
                key = (String) map.get(KEY);
                title = (String) map.get(TITLE);
                Map<String, Object> c = new HashMap<>();
                c.put(KEY, key + c);
                c.put(TITLE, title + c);
                list.add(c);
            }
            if (CollectionUtils.isEmpty((List) map.get(CHILDREN))) {
                map.put(CHILDREN, list);
            }
        }
    }

    public static void entity() {
        String key, title;
        List<TestSpeedEntity> list;
        for (int index = 0; index < 5000; index++) {
            TestSpeedEntity entity = new TestSpeedEntity();
            entity.setKey(index + "-");
            entity.setTitle(index + "-");
            list = new ArrayList<>();
            for (int child = 0; child < 10; child++) {
                key = entity.getKey();
                title = entity.getTitle();
                TestSpeedEntity c = new TestSpeedEntity();
                c.setKey(key + c);
                c.setTitle(title + c);
                list.add(c);
            }
            if (CollectionUtils.isEmpty(entity.getChildren())) {
                entity.setChildren(list);
            }
        }
    }


}
