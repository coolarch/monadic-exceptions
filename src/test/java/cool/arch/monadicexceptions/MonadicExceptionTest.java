package cool.arch.monadicexceptions;

import static cool.arch.monadicexceptions.ThrowableFunction.asFunction;

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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

/**
 * 
 */
public class MonadicExceptionTest {

	/**
	 * Test method for {@link cool.arch.monadicexceptions.MonadicException#when(java.lang.Class)}.
	 * @throws IOException 
	 */
	@Test
	public final void testWhen() throws IOException {
		new MonadicException(new IOException()).when(IOException.class)
			.unwrapAs(IOException.class)
			.thenThrow();
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.MonadicException.AbstractMonad#unwrapAs(java.lang.Class)}.
	 * @throws Exception 
	 */
	@Test
	public void testUnwrapAs_nullCause() throws Exception {
		final boolean result = new MonadicException(null).when(IOException.class)
			.unwrapAs(IOException.class)
			.isPresent();

		assertFalse(result);
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.MonadicException.AbstractMonad#unwrapAs(java.lang.Class)}.
	 * @throws Exception 
	 */
	@Test
	public void testUnwrapAs_nullExceptionClass() throws Exception {
		final boolean result = new MonadicException(new IOException()).when(IOException.class)
			.unwrapAs(null)
			.isPresent();

		assertFalse(result);
	}

	@Test(expected=InstantiationException.class)
	public void testWrappedAndThrowException() throws Exception {
		try {
			Optional.ofNullable(List.class)
				.map(asFunction(Class::newInstance));
		} catch (final MonadicException e) {
			e.when(NullPointerException.class)
				.thenThrow();
			e.when(InstantiationException.class)
				.ifPresent(Throwable::printStackTrace)
				.thenThrow();
		}
	}
}
