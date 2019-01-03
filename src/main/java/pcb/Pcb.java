package pcb;

import lombok.Data;

/**
 * @author WilderGao
 * time 2018-12-31 16:15
 * motto : everything is no in vain
 * description 进程和作业类
 */
@Data
public class Pcb implements Comparable<Pcb>{
    /**
     * 进程名
     */
    private int name;
    /**
     * 到达时间
     */
    private int arriveTime;
    /**
     * 开始时间
     */
    private int startTime;
    /**
     * 运行时间
     */
    private int runTime;

    /**
     * 用于时间片轮转调度算法，保存这个时间片还有多少时间
     */
    private int leftUnit;

    /**
     * 结束时间
     */
    private int endTime;
    /**
     * 已经运行时间
     */
    private int usedTime;
    /**
     * 进程状态
     */
    private int status;

    /**
     * 需要的空间大小
     */
    private int needSize;

    @Override
    public int compareTo(Pcb o) {
        if (this.arriveTime < o.arriveTime){
            return -1;
        }else {
            return 1;
        }
    }

    /**
     * 外存打印机制
     * @return 打印结果
     */
    public String toStringOuter() {
        return "进程名： Thread" + name +
                ", 到达时间：" + arriveTime +
                ", 状态： " + statusName(status) +
                ", 所需空间：" + needSize ;
    }

    /**
     * 内存打印机制
     * @return 打印结果
     */
    public String toStringInner() {
        return "进程名： Thread" + name +
                ", 开始时间：" + startTime +
                ", 需要运行时间：" + runTime +
                ", 剩余时间片长度：" + leftUnit +
                ", 已经运行时间：" + usedTime +
                ", 状态：" + statusName(status) +
                ", 所需空间" + needSize ;
    }

    /**
     * 完成进程打印机制
     * @return 打印结果
     */
    public String toStringFinish() {
        return "进程名：Thread" + name +
                ", 开始时间： " + startTime +
                ", 结束时间：" + endTime +
                ", 状态：" + statusName(status);
    }

    private String statusName(int status){
        switch (status){
            case -1: return "等待";
            case 0: return "就绪";
            case 1: return "运行";
            case 2: return "完成";
            default: return "未知状态";
        }
    }
}
