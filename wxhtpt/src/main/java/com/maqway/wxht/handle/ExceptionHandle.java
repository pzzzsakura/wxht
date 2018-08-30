package com.maqway.wxht.handle;

import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.Exception.UserOperationException;
import com.maqway.wxht.dto.Execution.Result;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
/**
 * 统一异常处理
 * Created by Ma.li.ran on 2017/09/23 17:14
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {

    private final static Logger logger  = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> handle(Exception e) {
            Map<String,Object> modelMap = new HashMap<>();
            logger.info(e.getClass()+"{}",printMessaege(e));
              modelMap.put("success",false);
              modelMap.put("errCode",ResultEnum.ERROR.getState());
              modelMap.put("errMsg",ResultEnum.ERROR.getStateInfo());
            return modelMap;

    }

    public String printMessaege(Exception e) {
        String message = null;
        String messages = null;
        StackTraceElement[] st = e.getStackTrace();
        for (int i = 0; i < st.length; i++) {
            message = st[i].getFileName() + "文件" + "\t" + st[i].getClassName()
                + "类\t" + st[i].getMethodName() + "方法\t"
                + st[i].getLineNumber() + "行发生"+"异常\n"+e.getMessage();
            messages += message;
        }
        return message;
    }
}
