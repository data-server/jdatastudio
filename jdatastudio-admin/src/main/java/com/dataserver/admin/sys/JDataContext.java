package com.dataserver.admin.sys;

import lombok.Data;

/**
 * Created by gongxinyi on 2018/11/27.
 */
@Data
public class JDataContext {
    private Tenant tenant;

    private static InheritableThreadLocal<JDataContext> threadLocal = new InheritableThreadLocal<JDataContext>() {
        @Override
        protected JDataContext initialValue() {
            return new JDataContext();
        }
    };

    public static JDataContext get() {
        return threadLocal.get();
    }

    public static void set(JDataContext jDataContext) {
        threadLocal.set(jDataContext);
    }
}
