package cn.MrZhang.util;

import cn.MrZhang.vo.ResultVo;

public class ResultVoUtil {

    public static ResultVo success(Object object) {
        ResultVo resultVo = new ResultVo<>();
        resultVo.setCode(200);
        resultVo.setMsg("成功");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo failed(Integer code, String msg) {
        ResultVo resultVo = new ResultVo<>();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
