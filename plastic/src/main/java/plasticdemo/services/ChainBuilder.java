package plasticdemo.services;

import java.util.List;

/**
 * A "Chain Of Responsibility" or "Chain Of Commands" pattern 
 */
public interface ChainBuilder {
   /**
    * Builds a chain instance from a given chain of commands implementing a particular
    * interface
    * @param <T>
    * @param comamndInterface interface type of the command
    * @param commands list of commands
    * @return chain instance
    */
   <T> T build(Class<T> commandInterface, List<T> commands);
}
