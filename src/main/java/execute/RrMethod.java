package execute;

import enums.PcbStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import memory.MemoryManager;
import pcb.Pcb;
import pcb.PcbManager;
import ui.MyFrame;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WilderGao
 * time 2019-01-02 15:19
 * motto : everything is no in vain
 * description 时间片轮转调度算法
 */
@AllArgsConstructor
@NoArgsConstructor
public class RrMethod {
    /**
     * 时间片
     */
    private int unit;

    public void doSchedule() {
        List<Pcb> workingPcbList = PcbManager.insideDiskPcbList;
        AtomicInteger currentTime = PcbManager.current;
        if (workingPcbList.size() != 0) {
            Pcb workPcb = workingPcbList.get(0);
            MyFrame.instance().getCurrentPcb().setText("运行进程： "+workPcb.getName());
            if (workPcb.getStatus() == PcbStatus.WAIT.getState()) {
                //进入这里说明这个线程是第一次开始的
                workPcb.setStartTime(currentTime.get());
                workPcb.setStatus(PcbStatus.RUN.getState());
                workPcb.setLeftUnit(unit);
            }
            increaseRunningTime(workPcb);
            if (workPcb.getUsedTime() == workPcb.getRunTime()) {
                //说明这时候已经运行完毕
                workPcb.setEndTime(currentTime.get());
                workPcb.setStatus(PcbStatus.FINISH.getState());
                //将pcb从运行队列中移出放到完成队列中
                workingPcbList.remove(workPcb);
                PcbManager.finishPcbList.add(workPcb);

                //回收内存
                int collectIndex = MemoryManager.innerZoneMap.get(workPcb.getName());
                MemoryManager.memory.collection(collectIndex);
                MemoryManager.innerZoneMap.remove(workPcb.getName());
                return;
            }

            if (workPcb.getLeftUnit() == 0) {
                //说明时间片轮询完了，但是进程还没有执行完，将它状态修改为就绪态并放到队尾
                workPcb.setStatus(PcbStatus.READY.getState());
                PcbManager.insideDiskPcbList.remove(workPcb);
                workPcb.setLeftUnit(unit);
                PcbManager.insideDiskPcbList.add(workPcb);
            }
        }
    }

    private void increaseRunningTime(Pcb pcb) {
        int usedTime = pcb.getUsedTime();
        pcb.setUsedTime(++usedTime);
        int leftUnit = pcb.getLeftUnit();
        pcb.setLeftUnit(--leftUnit);
    }

}
