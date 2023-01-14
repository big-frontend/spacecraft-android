package structural;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/03/2018  Fri
 * <p>
 * Flyweight patterns针对大数据的优化。为了降低内存消耗，通常在get数据之前都会查找是否之前创建过该数据。典型的Integer.valueOf就是按照这种逻辑实现的
 */
public class JavaFlyweight {
    Map<Integer, String> map = new HashMap();

    public String valueOf(Integer t) {
        return map.containsKey(t) ? map.get(t) : "fly weight";

    }
}
