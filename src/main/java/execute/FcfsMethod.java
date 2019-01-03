package execute;

import memory.Memory;
import memory.MemoryManager;
import pcb.Pcb;
import pcb.PcbManager;
import util.PcbUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author WilderGao
 * time 2019-01-02 16:07
 * motto : everything is no in vain
 * description 先来先服务算法
 */
public class FcfsMethod {
    public void doFcfs(){
        List<Pcb> outerPcbList = PcbManager.outerDiskPcbList;
        Collections.sort(outerPcbList);
        if (outerPcbList.size() > 0) {
            if (PcbManager.insideDiskPcbList.size() < PcbManager.innerSizeMax.get()) {
                //可以往运行队列里面加入新的作业，先来先服务算法
                Pcb readyPcb = outerPcbList.get(0);
                //给进程分配内存
                int result = MemoryManager.memory.firstFit(readyPcb.getNeedSize());
                if (result != -1){
                    System.out.println("内存分配成功");
                    //证明分配成功
                    MemoryManager.innerZoneMap.put(readyPcb.getName(), result);
                    PcbManager.outerDiskPcbList.remove(readyPcb);
                    PcbManager.insideDiskPcbList.add(readyPcb);
                }else {
                    System.out.println("内存分配失败");
                }
            }
        }
        if (outerPcbList.size() < PcbManager.innerSizeMax.get()){
            outerPcbList.addAll(PcbUtil.randomProducePcb(1));
        }
    }
}
