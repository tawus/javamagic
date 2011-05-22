package plasticdemo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used as a marker for {@link plasticdemo.transforms.PropertyTransformer}
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
   /**
    * whether setter method has to be created
    */
   boolean write() default true;
}
