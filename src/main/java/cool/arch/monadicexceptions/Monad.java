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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @param <T>
 */
public interface Monad<T extends Throwable> {

	/**
	 * @throws T
	 */
	void thenThrow() throws T;

	/**
	 * @param mapper
	 * @return
	 */
	<E extends Throwable> Monad<E> map(Function<? super T, ? extends E> mapper);

	/**
	 * @param throwableClass
	 * @return
	 */
	<E extends Throwable> Monad<E> unwrapAs(Class<? extends E> throwableClass);

	/**
	 * @return
	 */
	boolean isPresent();

	/**
	 * If a value is present, invoke the specified consumer with the value, otherwise do nothing.
	 * @param consumer block to be executed if a value is present
	 * @return Monad instance on which the method was called
	 */
	Monad<T> ifPresent(Consumer<? super T> consumer);

	/**
	 * @param predicate
	 * @return
	 */
	Monad<T> filter(Predicate<? super T> predicate);

	/**
	 * @return
	 */
	T get();
}
