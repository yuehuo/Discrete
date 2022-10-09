package com.qianfeng.vo;

/**
 * 返回给前端json数据的统一规范格式
 */
public class ResultVo {

    //状态码
    private int code;
    //状态提示信息
    private String msg;
    //长度：layui查询时给表格做分页需要这个属性
    private int count;
    //返回的数据
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private ResultVo(){}

    /**
     * 封装一个成功时返回resultVo的方法
     * @param data
     * @return
     */
    public static ResultVo getSuccessVo(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);//正常来说状态码200：表示成功，但是该死的layui表格查询时要求code必须为0
        resultVo.setMsg("success");
        resultVo.setData(data);
        return resultVo;
    }
    /**
     * 封装一个失败时返回resultVo的方法
     * @param
     * @return
     */
    public static ResultVo getFailVo(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(500);//失败的时候状态码随便编
        resultVo.setMsg("fail");
        return resultVo;
    }
}
