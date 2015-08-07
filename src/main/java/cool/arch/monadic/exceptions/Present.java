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

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Present<T extends Throwable> extends AbstractMonad<T> {

	private final T unit;

	@SuppressWarnings("unchecked")
	Present(Throwable cause) {
		this.unit = (T) requireNonNull(cause, "cause shall not be null");
	}

	@Override
	public void thenThrow() throws T {
		throw unit();
	}

	@Override
	public <E extends Throwable> Monad<E> map(Function<? super T, ? extends E> mapper) {
		if (mapper == null) {
			return empty();
		}

		return of(mapper.apply(unit()));
	}

	@Override
	public boolean isPresent() {
		return true;
	}

	@Override
	public Monad<T> ifPresent(Consumer<? super T> consumer) {
		if (consumer != null) {
			consumer.accept(unit());
		}

		return this;
	}

	@Override
	public Monad<T> filter(Predicate<? super T> predicate) {
		if (predicate == null) {
			return empty();
		}

		return predicate.test(unit())
			? this
			: empty();
	}

	@Override
	protected T unit() {
		return unit;
	}
}
