package plasticdemo.services;

import java.util.List;

public interface ChainBuilder {
   <T> T build(Class<T> interfaceClass, List<T> commands);
}
