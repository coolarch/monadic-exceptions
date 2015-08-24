package cool.arch.monadicexceptions;

/*
 * @formatter:off
 * cool.arch.monadicexceptions:monadic-exceptions
 * %%
 * Copyright (C) 2015 CoolArch
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * @formatter:on
 */

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import cool.arch.monadicexceptions.MonadicException;

/**
 * @param <TF>
 * @param <F>
 */
public abstract class AbstractLambdaTest<TF, F> {

	private final Predicate<F> predicate;

	private final Function<TF, F> invoker;

	private final TF thrower;

	private final TF identity;

	protected AbstractLambdaTest(final Predicate<F> predicate, final Function<TF, F> invoker, final TF thrower, final TF identity) {
		this.predicate = requireNonNull(predicate, "predicate shall not be null");
		this.invoker = requireNonNull(invoker, "invoker shall not be null");
		this.thrower = requireNonNull(thrower, "thrower shall not be null");
		this.identity = requireNonNull(identity, "identity shall not be null");
	}

	/**
	 * 
	 */
	@Test
	public final void testTypes() {
		final String simpleClassName = getClass().getSimpleName();
		final String expectedThrowableLambdaName = simpleClassName.replace("Test", "");
		final String expectedLambdaName = expectedThrowableLambdaName.replace("Throwable", "");

		final Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

		assertEquals(2, types.length);

		final String actualParameter0Name = types[0].getTypeName();
		final String actualParameter1Name = types[1].getTypeName();

		final String simplifiedActualParameterName0 = actualParameter0Name.replaceAll("<.*>", "")
			.replaceAll("^.*\\.", "");

		final String simplifiedActualParameterName1 = actualParameter1Name.replaceAll("<.*>", "")
			.replaceAll("^.*\\.", "");

		assertEquals(expectedThrowableLambdaName, simplifiedActualParameterName0);
		assertEquals(expectedLambdaName, simplifiedActualParameterName1);
	}

	/**
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	@Test
	public final void testWrapperMethods() throws SecurityException, ClassNotFoundException {
		final Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

		assertEquals(2, types.length);

		final Class<?> throwableInterface = Class.forName(types[0].getTypeName()
			.replaceAll("<.*>", ""));
		final String simpleClassName = getClass().getSimpleName();
		final String lambdaName = simpleClassName.replace("Test", "")
			.replace("Throwable", "");
		final String wrapperMethodName = "as" + lambdaName;

		final Method method = Arrays.stream(throwableInterface.getDeclaredMethods())
			.filter(m -> wrapperMethodName.equals(m.getName()))
			.filter(m -> Modifier.isStatic(m.getModifiers()))
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getParameterCount() == 1)
			.findFirst()
			.get();

		assertNotNull(method);
	}

	/**
	 * 
	 */
	@Test
	public final void testExceptionThrown() {
		try {
			predicate.test(invoker.apply(thrower));
		} catch (MonadicException e) {
			assertFalse(e.when(NullPointerException.class)
				.isPresent());

			assertTrue(e.when(IOException.class)
				.isPresent());
		}
	}

	/**
	 * 
	 */
	@Test
	public final void testExceptionNotThrown() {
		assertTrue(predicate.test(invoker.apply(identity)));
	}
}
