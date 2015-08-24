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
import java.util.function.ObjDoubleConsumer;

import org.junit.Before;

import com.google.common.util.concurrent.AtomicDouble;

import cool.arch.monadicexceptions.ThrowableObjDoubleConsumer;

public class ThrowableObjDoubleConsumerTest extends AbstractLambdaTest<ThrowableObjDoubleConsumer<String>, ObjDoubleConsumer<String>> {

	private static final AtomicReference<String> param0 = new AtomicReference<>();

	private static final AtomicDouble param1 = new AtomicDouble();

	@Before
	public void setup() {
		param0.set(null);
	}

	public ThrowableObjDoubleConsumerTest() {
		super(lambda -> {
			lambda.accept("1.5", 1.5D);
			return param0.get()
				.equals("1.5") && param1.get() == 1.5D;
		} , ThrowableObjDoubleConsumer::asObjDoubleConsumer, (o, d) -> {
			throw new IOException();
		} , (o, d) -> {
			param0.set(o);
			param1.set(d);
		});
	}
}
