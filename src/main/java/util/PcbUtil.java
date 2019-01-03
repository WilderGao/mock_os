package util;

import com.sun.scenario.effect.impl.prism.PrImage;
import enums.PcbStatus;
import pcb.Pcb;
import pcb.PcbManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author WilderGao
 * time 2019-01-02 17:46
 * motto : everything is no in vain
 * description 进程工具类
 */
public class PcbUtil {
    private static int threadId = 1;
    private static final int TIME_MAX = 20;
    private static final int TIME_MIN = 4;
    private static final int SIZE_MAX = 150;
    private static final int SIZE_MIN = 50;

    /**
     * 随机产生指定数量的进程
     * @param num   进程数量
     * @return  进程集合
     */
    public static List<Pcb> randomProducePcb(int num){
        Random random = new Random();
        List<Pcb> result = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            Pcb pcb = new Pcb();
            pcb.setStatus(PcbStatus.WAIT.getState());
            pcb.setArriveTime(PcbManager.current.get());
            pcb.setName(threadId++);
            pcb.setRunTime(random.nextInt(TIME_MAX - TIME_MIN)+TIME_MIN);
            pcb.setNeedSize(random.nextInt(SIZE_MAX-SIZE_MIN)+SIZE_MIN);
            result.add(pcb);
        }
        return result;
    }
}
