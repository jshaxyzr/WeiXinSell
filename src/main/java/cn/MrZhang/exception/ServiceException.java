package cn.MrZhang.exception;

/**
 * 
 * Title:ServiceException
 * @Description: TODO 服务层异常
 * @author MrZhang
 * @date 2017年9月19日 下午3:28:27 
 * @version V1.0
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -4886808529139756375L;

    public ServiceException(String msg) {
        super(msg);
    }

}
