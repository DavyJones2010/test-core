#!/usr/bin/env python
import functools

def my_func_a(x, y, z):
	return x + y + z

my_func_b = functools.partial(my_func_a, z = 2)
print my_func_b(0, 0)

my_func_c = functools.partial(my_func_b, y = 2)
print my_func_c(0)

my_func_d = functools.partial(my_func_a, y = 2, z = 2)
print my_func_d(0)

print 'my_func_c == my_func_d ? ', my_func_c == my_func_d
