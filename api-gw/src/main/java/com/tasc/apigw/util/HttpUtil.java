package com.tasc.apigw.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class HttpUtil {
    public static String getValueFromHeader(HttpServletRequest request, String header) {
        String value = request.getHeader(header);

        if (StringUtils.isBlank(value)) return null;

        return value;
    }
}
