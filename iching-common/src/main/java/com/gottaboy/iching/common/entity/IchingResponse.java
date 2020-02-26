package com.gottaboy.iching.common.entity;

import java.util.HashMap;

/**
 * @author gottaboy
 */
public class IchingResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public IchingResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public IchingResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public IchingResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
