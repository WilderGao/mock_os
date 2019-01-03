package enums;

/**
 * @author WilderGao
 * time 2018-12-31 16:21
 * motto : everything is no in vain
 * description 进程状态枚举
 */
public enum PcbStatus {
    /**
     * 等待
     */
    WAIT(-1),
    /**
     * 就绪
     */
    READY(0),
    /**
     * 运行
     */
    RUN(1),
    /**
     * 完成
     */
    FINISH(2);

    private int state;

    PcbStatus(int state){this.state = state;}
    public int getState(){
        return this.state;
    }
}
