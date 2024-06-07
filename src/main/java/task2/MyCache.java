package task2;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MyCache implements InvocationHandler {

    private final Object target;
    private final Map<Method, CachedResult> cache = new HashMap<>();
    private final Map<Class<? extends Annotation>, Set<String>> annotatedMethods = new HashMap<>();

    public MyCache(Object object) {
        this.target = object;
        mapAnnotatedMethods(object);
    }

    private void mapAnnotatedMethods(Object object) {
        Method[] methods = object.getClass().getMethods();
        annotatedMethods.put(Cache.class, getAnnotatedMethodsSet(methods, Cache.class));
        annotatedMethods.put(Mutator.class, getAnnotatedMethodsSet(methods, Mutator.class));
    }

    private Set<String> getAnnotatedMethodsSet(Method[] methods, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(annotationClass))
                .map(this::getMethodNameWithParams)
                .collect(Collectors.toSet());
    }

    private String getMethodNameWithParams(Method method) {
        String params = Arrays.stream(method.getParameterTypes())
                .map(Class::getName)
                .collect(Collectors.joining(","));
        return method.getName() + "(" + params + ")";
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean toCache = false;

        if (isMethodAnnotated(method, Cache.class)) {
            if (isResultCached(method, args)) {
                return getResultFromCache(method);
            } else
                toCache = true;

        } else if (isMethodAnnotated(method, Mutator.class)) {
            cache.clear();
        }

        Object result = method.invoke(target, args);
        if (toCache)
            putResultToCache(method, args, result);

        return result;
    }

    private void putResultToCache(Method method, Object[] args, Object result) {
        cache.put(method, new CachedResult(result, args));
    }

    private Object getResultFromCache(Method method) {
        return cache.get(method).getResult();
    }

    private boolean isResultCached(Method method, Object[] args) {
        if (!cache.containsKey(method))
            return false;

        CachedResult cachedResult = cache.get(method);
        return Arrays.equals(cachedResult.getCallParameters(), args);
    }

    private boolean isMethodAnnotated(Method method, Class<? extends Annotation> annotation) {
        // Если аннотация на интерфейсе (вдруг)
        if (method.isAnnotationPresent(annotation))
            return true;

        String methodName = getMethodNameWithParams(method);
        return annotatedMethods.containsKey(annotation)
                && annotatedMethods.get(annotation).contains(methodName);
    }
}
