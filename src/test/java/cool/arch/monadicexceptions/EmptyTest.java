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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * 
 */
public class EmptyTest {

	/**
	 * Test method for {@link cool.arch.monadic.exceptions.Empty#thenThrow()}.
	 * @throws Throwable 
	 */
	@Test
	public final void testThenThrow() throws Throwable {
		new Empty<>().thenThrow();
	}

	/**
	 * Test method for {@link cool.arch.monadic.exceptions.Empty#unit()}.
	 */
	@Test
	public final void testUnit() {
		assertNull(new Empty<>().unit());
	}

	/**
	 * Test method for {@link cool.arch.monadic.exceptions.Empty#isPresent()}.
	 */
	@Test
	public final void testIsPresent() {
		assertFalse(new Empty<>().isPresent());
	}

	/**
	 * Test method for {@link cool.arch.monadic.exceptions.Monad#empty()}.
	 */
	@Test
	public final void testEmpty() {
		assertNotNull(AbstractMonad.empty());
		assertEquals(Empty.class, AbstractMonad.empty()
			.getClass());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Empty#ifPresent(java.util.function.Consumer)}.
	 */
	@Test
	public void testIfPresent() throws Exception {
		final AtomicBoolean reference = new AtomicBoolean(false);
		final Monad<Exception> monad = AbstractMonad.empty();

		monad.ifPresent(e -> {
			reference.set(true);
		});

		assertFalse(reference.get());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Empty#map(java.util.function.Function)}.
	 */
	@Test
	public void testMap() throws Exception {
		final AtomicBoolean reference = new AtomicBoolean(false);
		final Monad<Exception> monad = AbstractMonad.empty();

		final Monad<Exception> result = monad.map(e -> {
			reference.set(true);
			return e;
		});

		assertFalse(reference.get());
		assertSame(monad, result);
	}
}
