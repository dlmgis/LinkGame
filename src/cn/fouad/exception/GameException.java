package cn.fouad.exception;


/**
 * 未完成
 * 自定义游戏异常
 */
public class GameException extends Exception {
    private static final long serialVersionUID = 1L;

    public GameException() {
        super();
    }

    public GameException(String arg0, Throwable arg1, boolean arg2,
                         boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public GameException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * 抛出异常信息
     * @param arg0 异常信息
     */
    public GameException(String arg0) {
        super(arg0);
    }

    public GameException(Throwable arg0) {
        super(arg0);
    }

}
