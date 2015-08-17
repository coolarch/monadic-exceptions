package cool.arch.monadicexceptions;

import java.util.function.ObjDoubleConsumer;

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

@FunctionalInterface
public interface ThrowableObjDoubleConsumer<T> {

	void accept(T t, double value) throws Exception;

	public static <T> ObjDoubleConsumer<T> asObjDoubleConsumer(final ThrowableObjDoubleConsumer<T> consumer) {
		return (o, l) -> {
			try {
				consumer.accept(o, l);
			} catch (Exception e) {
				throw new MonadicException(e);
			}
		};
	}

}
