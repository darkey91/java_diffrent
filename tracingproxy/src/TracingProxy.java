import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TracingProxy {
    private int depth;

    public TracingProxy(int depth){
        this.depth = depth;
    }
    public Object create(Object target) {
        if (isPrimitive(target.getClass())) return target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new TracingProxyHandler(target, depth));
    }

    private boolean isPrimitive(Class<?> target) {
        if (target.isArray() || target.isPrimitive() || target.isEnum() || target.equals(void.class)) {
            return true;
        }
        return false;
    }

    public class TracingProxyHandler implements InvocationHandler {
        private Object target;
        private Map<String, Method> methods;
        private int depth;

        public TracingProxyHandler(Object target, int depth) {
            this.target = target;
            this.depth = depth;
            methods = new HashMap<>();

            Class<?> currentClass = target.getClass();
            while (currentClass != null) {
                for (Method method : target.getClass().getDeclaredMethods()) {
                    methods.put(method.getName(), method);
                }
                currentClass = currentClass.getSuperclass();
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Method " + method.getName() + " invocation with " + method.getParameterCount() + " arguments: ");
            Arrays.stream(args).forEach(arg -> System.out.println("\t" + arg.getClass().getSimpleName() + ": " + arg.toString()));
            Method foundMethod = methods.get(method.getName());
            Object result = null;
            if (foundMethod == null) {
                System.err.println("Can't find method " + method.getName());
            } else {
                try {
                    result = foundMethod.invoke(target, args);
                    System.out.println("Invocation of " + method.getName() + " method finished successfully. ");
                    if (result != null) {
                        System.out.println("Returned value " + (!method.getReturnType().equals(void.class) ? result : "void"));
                        if (depth > 0 && !isPrimitive(method.getReturnType())) {
                            method.getReturnType().cast(result);
                            return new TracingProxy(depth - 1).create(result);
                        }
                    }

                } catch (Throwable e) {
                    System.err.println("Exception occurs during " + method.getName() + " invocation. " + e.getMessage());
                }
            }
            return result;
        }
    }

    public static void main(String[] args) throws Throwable {
        TracingProxy tracingProxy = new TracingProxy(8);

        String str = "Hello!";
        var test1 = tracingProxy.create(str);
        System.out.println(((CharSequence)test1).charAt(0));
        System.out.println(((Comparable<String>) test1).compareTo("ZZZZZZZZZ!"));

        Integer a = 2;
        var test2 = tracingProxy.create(a);
        System.out.println(((Comparable<Integer>) test2).compareTo(8));

        HashMap<Integer, String> map = new HashMap<>();

        var test3 = tracingProxy.create(map);
        ((Map<Integer, String>)test3).clear();

        System.out.println(map.getClass().getDeclaredMethod("clear").getReturnType());
    }

}