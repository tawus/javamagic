package plasticdemo.services;

public interface MethodShadowBuilder {
   <T> T build(Object object, String methodName, Class<T> interfaceType, Object ... args);
}
