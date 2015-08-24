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
	 * @throws Exception 
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
	 * @throws Exception 
	 */
	@Test
	public void testIfPresent_nullConsumer() throws Exception {
		final Monad<Exception> monad = AbstractMonad.of(new Exception());

		monad.ifPresent(null);

		assertEquals(Present.class, monad.getClass());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#filter(java.util.function.Predicate)}.
	 * @throws Exception 
	 */
	@Test
	public void testFilter() throws Exception {
		final Monad<Exception> monad = AbstractMonad.of(new Exception());
		final Monad<Exception> result = monad.filter(null);

		assertEquals(Empty.class, result.getClass());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#thenThrow()}.
	 * @throws Exception 
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
	 * @throws Exception 
	 */
	@Test
	public void testMap_nullFunction() throws Exception {
		final Monad<Exception> monad = AbstractMonad.of(new Exception());
		final Monad<Exception> result = monad.map(null);

		assertEquals(Empty.class, result.getClass());
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.Present#map(java.util.function.Function)}.
	 * @throws Exception 
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
