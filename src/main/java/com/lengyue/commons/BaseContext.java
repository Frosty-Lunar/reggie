package com.lengyue.commons;

/**
 * ThreadLocal工具类，用于获取当前登录用户ID
 *
 * @author 陌年
 * @date 2022/12/25
 */
public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前id
     *
     * @param id id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
    public static void remove(){
        threadLocal.remove();
    }
}
