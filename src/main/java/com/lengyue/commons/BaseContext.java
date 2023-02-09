package com.lengyue.commons;

/**
 * ThreadLocal工具类，用于获取当前登录用户ID
 *
 * @author 陌年
 * @date 2022/12/25
 */
public class BaseContext {
    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前id
     *
     * @param id id
     */
    public static void setCurrentId(Long id) {
        THREAD_LOCAL.set(id);
    }

    public static Long getCurrentId() {
        return THREAD_LOCAL.get();
    }
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
