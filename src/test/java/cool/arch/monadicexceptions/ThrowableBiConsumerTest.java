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

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import org.junit.Before;

import cool.arch.monadicexceptions.ThrowableBiConsumer;

public class ThrowableBiConsumerTest extends AbstractLambdaTest<ThrowableBiConsumer<String, String>, BiConsumer<String, String>> {

	private static final AtomicReference<String> captured0 = new AtomicReference<>();

	private static final AtomicReference<String> captured1 = new AtomicReference<>();

	@Before
	public void setup() {
		captured0.set(null);
		captured1.set(null);
	}

	public ThrowableBiConsumerTest() {
		super(lambda -> {
			lambda.accept("foo", "bar");
			return "foo".equals(captured0.get()) && "bar".equals(captured1.get());
		} , ThrowableBiConsumer::asBiConsumer, (t, u) -> {
			throw new IOException();
		} , (t, u) -> {
			captured0.set(t);
			captured1.set(u);
		});
	}
}
