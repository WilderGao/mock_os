package memory;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * @author WilderGao
 * time 2018-12-25 09:43
 * motto : everything is no in vain
 * description
 */
public class Memory {
    /**
     * 内存大小
     */
    private int size;
    /**
     * 最小剩余分区大小
     */
    private static final int MIN_SIZE = 5;
    /**
     * 内存分区
     */
    private List<Zone> zones;
    /**
     * 上次分配的空闲区位置
     */
    private int pointer;

    /**
     * 默认内存大小为 100 KB
     */
    public Memory() {
        this.size = 100;
        this.pointer = 0;
        this.zones = new LinkedList<>();
        zones.add(new Zone(0, size));
    }

    public Memory(int size) {
        this.size = size;
        this.pointer = 0;
        this.zones = new LinkedList<>();
        zones.add(new Zone(0, size));
    }

    public List<Zone> getZones(){
        return this.zones;
    }


    /**
     * 首次适应算法
     *
     * @param size 指定需要分配的大小
     */
    public int firstFit(int size) {
        //遍历分区链表
        for (pointer = 0; pointer < zones.size(); pointer++) {
            Zone tmp = zones.get(pointer);
            //找到可用分区（空闲且大小足够）
            if (tmp.isFree() && (tmp.getSize() > size)) {
                doAllocation(size, pointer, tmp);
                System.out.println("[INFO] 内存分配成功......");
                //返回空间索引
                return zones.indexOf(tmp);
            }
        }
        //遍历结束后未找到可用分区, 则内存分配失败
        System.out.println("无可用内存空间!");
        return -1;
    }

    /**
     * 最佳适应算法
     *
     * @param size 指定需要分配的大小
     */
    private void bestFit(int size) {
        int flag = -1;
        int min = this.size;
        for (pointer = 0; pointer < zones.size(); pointer++) {
            Zone tmp = zones.get(pointer);
            if (tmp.isFree() && (tmp.getSize() > size)) {
                if (min > tmp.getSize() - size) {
                    min = tmp.getSize() - size;
                    flag = pointer;
                }
            }
        }
        if (flag == -1) {
            System.out.println("无可用内存空间!");
        } else {
            doAllocation(size, flag, zones.get(flag));
        }
    }

    /**
     * 执行分配
     *
     * @param size     申请大小
     * @param location 当前可用分区位置
     * @param tmp      可用空闲区
     */
    private void doAllocation(int size, int location, Zone tmp) {
        //如果分割后分区剩余大小过小（MIN_SIZE）则将分区全部分配，否则分割为两个分区
        if (tmp.getSize() - size <= MIN_SIZE) {
            tmp.setFree(false);
        } else {
            Zone split = new Zone(tmp.getHead() + size, tmp.getSize() - size);
            zones.add(location + 1, split);
            tmp.setSize(size);
            tmp.setFree(false);
        }
        System.out.println("成功分配 " + size + "KB 内存!");
    }

    /**
     * 内存回收
     *
     * @param id 指定要回收的分区好号
     */
    public void collection(int id) {
        if (id >= zones.size()) {
            System.out.println("无此分区编号!");
            return;
        }
        Zone tmp = zones.get(id);
        int size = tmp.getSize();
        if (tmp.isFree()) {
            System.out.println("指定分区未被分配, 无需回收");
            return;
        }
        //如果回收分区不是尾分区且后一个分区为空闲, 则与后一个分区合并
        if (id < zones.size() - 1 && zones.get(id + 1).isFree()) {
            Zone next = zones.get(id + 1);
            int s = tmp.getSize() + next.getSize();
            tmp.setSize(s);
            zones.remove(next);
        }
        //如果回收分区不是首分区且前一个分区为空闲, 则与前一个分区合并
        if (id > 0 && zones.get(id - 1).isFree()) {
            Zone previous = zones.get(id - 1);
            int s = previous.getSize() + tmp.getSize();
            previous.setSize(s);
            zones.remove(id);
            id--;
        }
        zones.get(id).setFree(true);
        System.out.println("内存回收成功!, 本次回收了 " + size + "KB 空间!");
    }

}
