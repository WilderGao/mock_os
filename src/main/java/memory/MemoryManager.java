package memory;

import pcb.Pcb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WilderGao
 * time 2019-01-03 16:44
 * motto : everything is no in vain
 * description 内存管理
 */
public class MemoryManager {
    public static Memory memory = new Memory(1000);

    public static Map<Integer, Integer> innerZoneMap = new ConcurrentHashMap<>(16);
}
