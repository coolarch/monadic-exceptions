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
	 * @throws Exception 
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
	 * Test method for cool.arch.monadicexceptions.Empty#map(java.util.function.Function).
	 * @throws Exception 
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
