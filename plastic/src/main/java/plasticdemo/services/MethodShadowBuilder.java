package plasticdemo.services;

/**
 * A Method shadow builder
 */
public interface MethodShadowBuilder {
   /**
    * Builds a service for the method call with the specified arguments.
    *  
    * @param <T>
    * @param object
    * @param methodName
    * @param interfaceType
    * @param args
    * @return
    */
   <T> T build(Object object, String methodName, Class<T> interfaceType, Object ... args);
}
