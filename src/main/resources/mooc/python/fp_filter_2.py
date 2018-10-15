#!/usr/bin/env python
def isPrime(s):
	i = 2
	while(i < s):
		if (s % i == 0):
			return False
		i += 1
	return True

print(filter(isPrime, range(1, 100)))
