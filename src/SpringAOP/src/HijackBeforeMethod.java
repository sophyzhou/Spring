import org.springframework.aop.ThrowsAdvice;
import java.util.Arrays;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class HijackBeforeMethod implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {

		System.out.println("HijackAroundMethod : Before method hijacked!");

		try {
			Object result = methodInvocation.proceed();

			System.out.println("HijackAroundMethod : Before after hijacked!");
			return result;
		} catch (IllegalArgumentException e) {
			// same with ThrowsAdvice
			System.out.println("HijackAroundMethod : Throw exception hijacked!");
			throw e;
		}
	}
}