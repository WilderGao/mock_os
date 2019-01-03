package memory;

import lombok.Data;

/**
 * @author WilderGao
 * time 2018-12-25 09:47
 * motto : everything is no in vain
 * description 节点信息
 */
@Data
public class Zone {
    /**
     * 分区大小
     */
    private int size;
    /**
     * 分区始址
     */
    private int head;
    /**
     * 空闲状态
     */
    private boolean isFree;

    public Zone(int head, int size) {
        this.head = head;
        this.size = size;
        this.isFree = true;
    }

    @Override
    public String toString() {
        return head+"                                   "+size+"kb"+"                                       "+"free:"+isFree;
    }
}
