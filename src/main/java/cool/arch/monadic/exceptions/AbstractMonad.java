package cool.arch.monadic.exceptions;

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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

abstract class AbstractMonad<T extends Throwable> implements Monad<T> {

	private static final Monad<? extends Throwable> EMPTY = new Empty<>();

	protected abstract T unit();

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

}
