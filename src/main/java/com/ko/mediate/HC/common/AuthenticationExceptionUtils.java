package com.ko.mediate.HC.common;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationExceptionUtils {

    public static String EXCEPTION_ATTRIBUTE_KEY = "exception";

    public static void setResponse(
            HttpServletResponse response, ErrorCode errorCode) throws IOException {
        JsonObject json = new JsonObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());
        json.addProperty("code", errorCode.getCode());
        json.addProperty("description", errorCode.getDescription());
        response.getWriter().print(json);
    }
}
