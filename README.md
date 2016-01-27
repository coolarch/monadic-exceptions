# monadic-exceptions
Monadic exception to assist in handling use of checked exceptions in Java 8+ functional programming

Example of use:

```java
try {
	Optional.ofNullable(List.class)
		.map(asFunction(Class::newInstance));
} catch (final MonadicException e) {
	e.when(NullPointerException.class)
		.thenThrow();
	e.when(InstantiationException.class)
		.ifPresent(Throwable::printStackTrace)
		.thenThrow();
}
```

See cool.arch.monadicexceptions.MonadicExceptionTest#testWrappedAndThrowException for a full in-context example.