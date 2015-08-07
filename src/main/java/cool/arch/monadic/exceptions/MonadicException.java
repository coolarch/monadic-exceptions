package cool.arch.monadic.exceptions;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

import cool.arch.monadic.exceptions.function.ThrowableFunction;
import cool.arch.monadic.exceptions.function.ThrowablePredicate;
import cool.arch.monadic.exceptions.function.ThrowableToLongFunction;

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

public class MonadicException extends RuntimeException {

	private static final long serialVersionUID = 650942982495284918L;

	private final Monad<Throwable> monad;

	public MonadicException(Throwable cause) {
		super(cause);
		monad = AbstractMonad.of(cause);
	}

	@SuppressWarnings("unchecked")
	public <T extends Throwable> Monad<T> when(final Class<T> throwableClass) {
		return (Monad<T>) monad.filter(e -> e.getClass()
			.isAssignableFrom(throwableClass));
	}

	public static <T, R> Function<T, R> wrapAsFunction(final ThrowableFunction<T, R> function) {
		return t -> {
			R result = null;

			try {
				result = function.apply(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T> Predicate<T> wrapAsPredicate(final ThrowablePredicate<T> predicate) {
		return t -> {
			boolean result = false;

			try {
				result = predicate.test(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T> ToLongFunction<T> wrapAsToLongFunction(final ThrowableToLongFunction<T> function) {
		return t -> {
			long result;

			try {
				result = function.applyAsLong(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}
}
