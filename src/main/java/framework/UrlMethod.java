package framework;

import java.lang.reflect.Method;

public class UrlMethod {
    private String url;
    private Class<?> controllerClass;
    private Method method;

    public UrlMethod(String url, Class<?> controllerClass, Method method) {
        this.url = url;
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }
}
