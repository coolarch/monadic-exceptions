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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * 
 */
public class ThrowableFunctionTest extends AbstractLambdaTest<ThrowableFunction<String, String>, Function<String, String>> {

	/**
	 * 
	 */
	public ThrowableFunctionTest() {
		super(lambda -> "foo".equals(lambda.apply("foo")), ThrowableFunction::asFunction, s -> {
			throw new IOException();
		} , s -> s);
	}

	/**
	 * 
	 */
	@Test
	public void testStreamThrow() {
		String result = null;

		try {
			IntStream.range(0, 100)
				.boxed()
				.parallel()
				.map(ThrowableFunction.asFunction(i -> {
					Thread.sleep(1000);
					final int value = i.intValue();

					if (value > 10 && value % 2 == 0) {
						throw new IOException("expected exception");
					}

					return i;
				}))
				.map(i -> i.toString())
				.collect(Collectors.toSet());
		} catch (final MonadicException e) {
			result = e.when(IOException.class)
				.get()
				.getMessage();
		}

		assertEquals("expected exception", result);
	}
}
