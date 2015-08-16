package cool.arch.monadic.exceptions;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;

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

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

import cool.arch.monadic.exceptions.function.ThrowableBiConsumer;
import cool.arch.monadic.exceptions.function.ThrowableBiFunction;
import cool.arch.monadic.exceptions.function.ThrowableBiPredicate;
import cool.arch.monadic.exceptions.function.ThrowableBooleanSupplier;
import cool.arch.monadic.exceptions.function.ThrowableConsumer;
import cool.arch.monadic.exceptions.function.ThrowableDoubleBinaryOperator;
import cool.arch.monadic.exceptions.function.ThrowableDoubleConsumer;
import cool.arch.monadic.exceptions.function.ThrowableDoubleFunction;
import cool.arch.monadic.exceptions.function.ThrowableDoublePredicate;
import cool.arch.monadic.exceptions.function.ThrowableDoubleSupplier;
import cool.arch.monadic.exceptions.function.ThrowableDoubleToIntFunction;
import cool.arch.monadic.exceptions.function.ThrowableDoubleToLongFunction;
import cool.arch.monadic.exceptions.function.ThrowableDoubleUnaryOperator;
import cool.arch.monadic.exceptions.function.ThrowableFunction;
import cool.arch.monadic.exceptions.function.ThrowableIntBinaryOperator;
import cool.arch.monadic.exceptions.function.ThrowableIntConsumer;
import cool.arch.monadic.exceptions.function.ThrowableIntFunction;
import cool.arch.monadic.exceptions.function.ThrowableIntPredicate;
import cool.arch.monadic.exceptions.function.ThrowableIntSupplier;
import cool.arch.monadic.exceptions.function.ThrowableIntToDoubleFunction;
import cool.arch.monadic.exceptions.function.ThrowableIntToLongFunction;
import cool.arch.monadic.exceptions.function.ThrowableIntUnaryOperator;
import cool.arch.monadic.exceptions.function.ThrowableLongBinaryOperator;
import cool.arch.monadic.exceptions.function.ThrowableLongConsumer;
import cool.arch.monadic.exceptions.function.ThrowableLongFunction;
import cool.arch.monadic.exceptions.function.ThrowableLongPredicate;
import cool.arch.monadic.exceptions.function.ThrowableLongSupplier;
import cool.arch.monadic.exceptions.function.ThrowableLongToDoubleFunction;
import cool.arch.monadic.exceptions.function.ThrowableLongToIntFunction;
import cool.arch.monadic.exceptions.function.ThrowableLongUnaryOperator;
import cool.arch.monadic.exceptions.function.ThrowableObjDoubleConsumer;
import cool.arch.monadic.exceptions.function.ThrowableObjIntConsumer;
import cool.arch.monadic.exceptions.function.ThrowableObjLongConsumer;
import cool.arch.monadic.exceptions.function.ThrowablePredicate;
import cool.arch.monadic.exceptions.function.ThrowableSupplier;
import cool.arch.monadic.exceptions.function.ThrowableToDoubleBiFunction;
import cool.arch.monadic.exceptions.function.ThrowableToDoubleFunction;
import cool.arch.monadic.exceptions.function.ThrowableToIntBiFunction;
import cool.arch.monadic.exceptions.function.ThrowableToIntFunction;
import cool.arch.monadic.exceptions.function.ThrowableToLongBiFunction;
import cool.arch.monadic.exceptions.function.ThrowableToLongFunction;

public final class Wrap {

	public static <T, R> Function<T, R> asFunction(final ThrowableFunction<T, R> function) {
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

	public static <T, U, R> BiFunction<T, U, R> asBiFunction(final ThrowableBiFunction<T, U, R> function) {
		return (t,u) -> {
			R result = null;

			try {
				result = function.apply(t,u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T> Predicate<T> asPredicate(final ThrowablePredicate<T> predicate) {
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

	public static DoublePredicate asDoublePredicate(final ThrowableDoublePredicate predicate) {
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

	public static <T,U> BiPredicate<T,U> asBiPredicate(final ThrowableBiPredicate<T,U> predicate) {
		return (t,u) -> {
			boolean result = false;

			try {
				result = predicate.test(t,u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T> ToLongFunction<T> asToLongFunction(final ThrowableToLongFunction<T> function) {
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

	public static DoubleToLongFunction asDoubleToLongFunction(final ThrowableDoubleToLongFunction function) {
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

	public static <T, U> ToLongBiFunction<T, U> asToLongBiFunction(final ThrowableToLongBiFunction<T, U> function) {
		return (t, u) -> {
			long result;

			try {
				result = function.applyAsLong(t, u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T> ToIntFunction<T> asToIntFunction(final ThrowableToIntFunction<T> function) {
		return t -> {
			int result;

			try {
				result = function.applyAsInt(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static DoubleToIntFunction asDoubleToIntFunction(final ThrowableDoubleToIntFunction function) {
		return t -> {
			int result;

			try {
				result = function.applyAsInt(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T> ToDoubleFunction<T> asToDoubleFunction(final ThrowableToDoubleFunction<T> function) {
		return t -> {
			double result;

			try {
				result = function.applyAsDouble(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T, U> ToDoubleBiFunction<T, U> asToDoubleBiFunction(final ThrowableToDoubleBiFunction<T, U> function) {
		return (t, u) -> {
			double result;

			try {
				result = function.applyAsDouble(t, u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T, U> ToIntBiFunction<T, U> asToIntBiFunction(final ThrowableToIntBiFunction<T, U> function) {
		return (t, u) -> {
			int result;

			try {
				result = function.applyAsInt(t, u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <R> Supplier<R> asSupplier(final ThrowableSupplier<R> function) {
		return () -> {
			R result = null;

			try {
				result = function.get();
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static DoubleSupplier asDoubleSupplier(final ThrowableDoubleSupplier function) {
		return () -> {
			double result;

			try {
				result = function.getAsDouble();
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static <T> ObjLongConsumer<T> asObjLongConsumer(final ThrowableObjLongConsumer<T> consumer) {
		return (o, l) -> {
			try {
				consumer.accept(o, l);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}

	public static <T> ObjIntConsumer<T> asObjIntConsumer(final ThrowableObjIntConsumer<T> consumer) {
		return (o, l) -> {
			try {
				consumer.accept(o, l);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}

	public static DoubleConsumer asDoubleConsumer(final ThrowableDoubleConsumer consumer) {
		return d -> {
			try {
				consumer.accept(d);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}

	public static <T> ObjDoubleConsumer<T> asObjDoubleConsumer(final ThrowableObjDoubleConsumer<T> consumer) {
		return (o, l) -> {
			try {
				consumer.accept(o, l);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}

	public static LongUnaryOperator asLongUnaryOperator(final ThrowableLongUnaryOperator operator) {
		return t -> {
			long result;

			try {
				result = operator.applyAsLong(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static LongToIntFunction asLongToIntFunction(final ThrowableLongToIntFunction function) {
		return t -> {
			int result;

			try {
				result = function.applyAsInt(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static LongToDoubleFunction asLongToDoubleFunction(final ThrowableLongToDoubleFunction function) {
		return t -> {
			double result;

			try {
				result = function.applyAsDouble(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static LongSupplier asLongSupplier(final ThrowableLongSupplier supplier) {
		return () -> {
			long result;

			try {
				result = supplier.getAsLong();
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static LongPredicate asLongPredicate(final ThrowableLongPredicate predicate) {
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

	public static <R> LongFunction<R> asLongFunction(final ThrowableLongFunction<R> function) {
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

	public static <R> DoubleFunction<R> asDoubleFunction(final ThrowableDoubleFunction<R> function) {
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

	public static LongConsumer asLongConsumer(final ThrowableLongConsumer consumer) {
		return t -> {
			try {
				consumer.accept(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}

	public static LongBinaryOperator asLongBinaryOperator(final ThrowableLongBinaryOperator operator) {
		return (t, u) -> {
			long result;

			try {
				result = operator.applyAsLong(t, u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static IntBinaryOperator asIntBinaryOperator(final ThrowableIntBinaryOperator operator) {
		return (t, u) -> {
			int result;

			try {
				result = operator.applyAsInt(t, u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static IntUnaryOperator asIntUnaryOperator(final ThrowableIntUnaryOperator operator) {
		return t -> {
			int result;

			try {
				result = operator.applyAsInt(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static DoubleUnaryOperator asDoubleUnaryOperator(final ThrowableDoubleUnaryOperator operator) {
		return t -> {
			double result;

			try {
				result = operator.applyAsDouble(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static DoubleBinaryOperator asDoubleBinaryOperator(final ThrowableDoubleBinaryOperator operator) {
		return (t,u) -> {
			double result;

			try {
				result = operator.applyAsDouble(t,u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static IntToLongFunction asIntToLongFunction(final ThrowableIntToLongFunction function) {
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

	public static IntToDoubleFunction asIntToDoubleFunction(final ThrowableIntToDoubleFunction function) {
		return t -> {
			double result;

			try {
				result = function.applyAsDouble(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static IntSupplier asIntSupplier(final ThrowableIntSupplier supplier) {
		return () -> {
			int result;

			try {
				result = supplier.getAsInt();
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static BooleanSupplier asBooleanSupplier(final ThrowableBooleanSupplier supplier) {
		return () -> {
			boolean result;

			try {
				result = supplier.getAsBoolean();
			} catch (Exception e) {
				throw new MonadicException(e);
			}

			return result;
		};
	}

	public static IntPredicate asIntPredicate(final ThrowableIntPredicate predicate) {
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

	public static <R> IntFunction<R> asIntFunction(final ThrowableIntFunction<R> function) {
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

	public static IntConsumer asIntConsumer(final ThrowableIntConsumer consumer) {
		return t -> {
			try {
				consumer.accept(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}

	public static <T> Consumer<T> asConsumer(final ThrowableConsumer<T> consumer) {
		return t -> {
			try {
				consumer.accept(t);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}

	public static <T,U> BiConsumer<T,U> asBiConsumer(final ThrowableBiConsumer<T,U> consumer) {
		return (t,u) -> {
			try {
				consumer.accept(t,u);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}
}
