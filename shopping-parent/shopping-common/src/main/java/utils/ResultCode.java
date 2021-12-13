package utils;

/**
 * 公共的返回结果枚举类
 * 时间：2021年2月14日 19:36:21
 */
public enum ResultCode {
    /**
     * 状态码： 200->成功
     * 400->失败
     * 401->密码不正确
     * 402->尚未登录
     * 403->没有找到数据
     * 404->服务器异常
     * 405->未知错误，请联系管理员
     * 406->用户已存在
     * 407->部门不存在
     */
    SUCCESS("200", "成功"),
    ERROR("400", "失败"),
    PASSWORD_ERROR("401", "密码不正确"),
    LOGIN_ERROR("402", "用户名密码错误"),
    DATA_ERROR("403", "没有找到数据"),
    SERVER_ERROR("404", "服务器异常"),
    UNKNOWN_ERROR("405", "未知错误，请联系管理员"),
    USER_ALREADY_EXISTS("406", "用户已存在"),
    DEPART_NO_EXISTS("407", "部门不存在"),
    LOGIN_NO_PASS("408", "账号被禁用，请联系管理员"),
    LOGIN_NO("409", "未登录"),
    LOGIN_NO_ROLE("410", "权限不足"),
    CAPTCHA_ERROR("411", "验证码错误"),
    CAPTCHA_EXPIRE("412", "验证码过期"),
    ADD_ERROR("413", "添加失败"),
    DEL_ERROR("414", "删除失败"),
    UPDATE_ERROR("415", "修改失败"),
    PHONE_ERROR("416", "手机号格式不正确"),
    MEMBER_ERROR("417", "用户名已存在"),
    PARAM_ERROR("418", "参数错误"),
    SYSTEM_ERROR("419", "系统错误"),
    BUS_ERROR("420", "系统错误");



    private String errCode;
    private String errMsg;

    ResultCode(String errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public static String getErrMsg(String errCode) {
        for(ResultCode resultCode:ResultCode.values()) {
            if(errCode.equals(resultCode.getErrCode())) {
                return resultCode.getErrMsg();
            }
        }
        return "";
    }

}
