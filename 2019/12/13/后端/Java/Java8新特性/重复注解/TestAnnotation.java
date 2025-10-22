import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 重复注解
 */
public class TestAnnotation {


    @Test
    public void test1() throws NoSuchMethodException {
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method show = clazz.getMethod("show");
        MyAnnotation[] annotationsByType = show.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation myAnnotation : annotationsByType) {
            System.out.println(myAnnotation.value());
        }
    }

    @MyAnnotation("hello")
    @MyAnnotation("world")
    @MyAnnotation
    public void show(@MyAnnotation("abec") String string){

    }
}
