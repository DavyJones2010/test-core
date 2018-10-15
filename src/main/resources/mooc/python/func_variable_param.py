#!/usr/bin/env python
def append_end(list=[]):
	list.append('END')
	return list
print append_end()
# ['END']
print append_end()
# ['END', 'END']
print append_end()
# ['END', 'END', 'END']
print append_end()
# ['END', 'END', 'END', 'END']
print append_end()
# ['END', 'END', 'END', 'END', 'END']

def actual_append_end(list=None):
	if list is None:
		list = []
	list.append('END')
	return list
print actual_append_end()
print actual_append_end()
print actual_append_end(['A', 'B', 'C'])

def calc(numbers):
	sum = 0
	for n in numbers:
		sum += n * n
	return sum
print calc([1, 2, 3])
print calc([2, 3])

def calc_2(*numbers):
	sum = 0
	for n in numbers:
		sum += n * n
	return sum
print calc_2(1, 2, 3)
print calc_2(2, 3)

nums_1 = [1, 2, 3]
nums_2 = [2, 3]
print calc_2(*nums_1)
print calc_2(*nums_2)
