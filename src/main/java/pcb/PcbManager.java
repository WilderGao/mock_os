package pcb;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WilderGao
 * time 2018-12-31 16:27
 * motto : everything is no in vain
 * description
 */
@Data
public class PcbManager {
    /**
     * 允许同时运行的进程数量
     */
    public static AtomicInteger innerSizeMax = new AtomicInteger(10);

    /**
     * 当前时间
     */
    public static AtomicInteger current = new AtomicInteger(0);

    /**
     * 处于外存中的作业队列
     */
    public static List<Pcb> outerDiskPcbList = new LinkedList<>();
    /**
     * 处于内存中的作业队列
     */
    public static List<Pcb> insideDiskPcbList = new LinkedList<>();

    /**
     * 已运行完成的进程队列
     */
    public static List<Pcb> finishPcbList = new LinkedList<>();
}
