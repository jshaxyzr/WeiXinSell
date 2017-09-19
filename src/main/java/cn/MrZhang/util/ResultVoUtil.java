package cn.MrZhang.util;

import cn.MrZhang.vo.ResultVo;

public class ResultVoUtil {

    public static ResultVo<Object> success(Object object) {
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(200);
        resultVo.setMsg("成功");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo<Object> failed(Integer code, String msg) {
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
