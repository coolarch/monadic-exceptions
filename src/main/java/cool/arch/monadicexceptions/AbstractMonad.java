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

abstract class AbstractMonad<T extends Throwable> implements Monad<T> {

	private static final Monad<? extends Throwable> EMPTY = new Empty<>();

	protected abstract T unit();

	@Override
	public final <E extends Throwable> Monad<E> unwrapAs(Class<? extends E> throwableClass) {
		final Throwable cause = unit();

		if (cause == null) {
			return empty();
		}

		if (throwableClass == null) {
			return empty();
		}

		if (throwableClass.isAssignableFrom(cause.getClass())) {
			of(cause);
		}

		return empty();
	}

	@SuppressWarnings("unchecked")
	protected static final <E extends Throwable> Monad<E> empty() {
		return (Monad<E>) EMPTY;
	}

	static final <E extends Throwable> Monad<E> of(E throwable) {
		if (throwable == null) {
			return empty();
		}

		return new Present<>(throwable);
	}

	@Override
	public final T get() {
		return unit();
	}
}
