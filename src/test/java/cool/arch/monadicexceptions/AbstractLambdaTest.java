package cool.arch.monadicexceptions;

/*
 * #%L cool.arch.monadicexceptions:monadic-exceptions %% Copyright (C) 2015 CoolArch %%
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License. #L%
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

public abstract class AbstractLambdaTest<TF, F> {

	private final Predicate<F> predicate;

	private final Function<TF, F> invoker;

	private final TF thrower;

	private final TF identity;

	protected AbstractLambdaTest(final Predicate<F> predicate, final Function<TF, F> invoker, final TF thrower,
		final TF identity) {

		this.predicate = requireNonNull(predicate, "predicate shall not be null");
		this.invoker = requireNonNull(invoker, "invoker shall not be null");
		this.thrower = requireNonNull(thrower, "thrower shall not be null");
		this.identity = requireNonNull(identity, "identity shall not be null");
	}

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

	@Test
	public final void testExceptionNotThrown() {
		assertTrue(predicate.test(invoker.apply(identity)));
	}
}
