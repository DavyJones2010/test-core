#!/usr/bin/env python
print map(lambda x: x * x, [1, 2, 3, 4, 5])
f = lambda x: x * x
print f
print f(2)

def build(x, y):
	return lambda: x * x + y * y
print build(1, 2)
print build(1, 2)()
