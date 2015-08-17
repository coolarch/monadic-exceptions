/**
 * 
 */
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

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

/**
 * 
 */
public class PresentTest {

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#ifPresent(java.util.function.Consumer)}.
	 */
	@Test
	public void testIfPresent() throws Exception {
		final AtomicBoolean reference = new AtomicBoolean(false);
		final Monad<Exception> monad = AbstractMonad.of(new Exception());

		monad.ifPresent(e -> {
			reference.set(true);
		});

		assertEquals(Present.class, monad.getClass());
		assertTrue(reference.get());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#ifPresent(java.util.function.Consumer)}.
	 */
	@Test
	public void testIfPresent_nullConsumer() throws Exception {
		final Monad<Exception> monad = AbstractMonad.of(new Exception());

		monad.ifPresent(null);

		assertEquals(Present.class, monad.getClass());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#filter(java.util.function.Predicate)}.
	 */
	@Test
	public void testFilter() throws Exception {
		final Monad<Exception> monad = AbstractMonad.of(new Exception());
		final Monad<Exception> result = monad.filter(null);

		assertEquals(Empty.class, result.getClass());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#thenThrow()}.
	 */
	@Test
	public void testThenThrow() throws Exception {
		final Monad<Exception> monad = AbstractMonad.of(new Exception("I am expecting this!"));
		final AtomicReference<Exception> exception = new AtomicReference<>();

		try {
			monad.thenThrow();
		} catch (final Exception e) {
			exception.set(e);
		}

		assertEquals("I am expecting this!", exception.get()
			.getMessage());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#map(java.util.function.Function)}.
	 */
	@Test
	public void testMap_nullFunction() throws Exception {
		final Monad<Exception> monad = AbstractMonad.of(new Exception());
		final Monad<Exception> result = monad.map(null);

		assertEquals(Empty.class, result.getClass());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#map(java.util.function.Function)}.
	 */
	@Test
	public void testMap() throws Exception {
		final Exception innerException = new Exception();
		final Monad<Exception> monad = AbstractMonad.of(innerException);
		final Monad<Exception> result = monad.map(e -> new Exception("from function", e));

		assertEquals(Present.class, result.getClass());
		assertSame(innerException, result.get()
			.getCause());
		assertEquals("from function", result.get()
			.getMessage());
	}
}
