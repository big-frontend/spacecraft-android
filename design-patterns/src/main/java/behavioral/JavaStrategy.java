package behavioral;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/02/2018  Thu
 */

public interface JavaStrategy {
    void readExternal();
}

class FlyweightStrategy implements JavaStrategy {
    @Override
    public void readExternal() {
        //todo something
        String s = "hello , my name is LiMing";
    }
}

class DefaultStrategy implements JavaStrategy {
    @Override
    public void readExternal() {
        //todo something
        String s = "hello , my name is WangMei";

    }
}

//class Activity {
//    void call(boolean useFlyweightMapStorage) {
//        JavaStrategy strategy;
//        if (useFlyweightMapStorage) {
//            strategy = new FlyweightStrategy();
//        } else {
//            strategy = new DefaultStrategy();
//        }
//        strategy.readExternal();
//    }
//}