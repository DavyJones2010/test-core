#!/usr/bin/env python
import math
def my_abs(x):
	if not isinstance(x, (int, float)):
		raise TypeError('Bad Operand Type')
	if x > 0:
		return x
	elif x < 0:
		return -x
	else:
		return

print my_abs(100)
print my_abs(-100)
print my_abs(0)


word = str(raw_input('Please input your number: '))
while 'stop' != word:
	print my_abs(float(word))
	word = str(raw_input('Please input your number: '))

# we can return multiple values in a single function
def move(x, y, step, angle=0):
	nx = x + step * math.cos(angle)
	ny = y - step * math.sin(angle)
	return nx, ny

x, y = move(100, 100, 60, math.pi / 6)
print x, y

z = move(10, 20, 30, math.pi / 3)
print z
print len(z)
