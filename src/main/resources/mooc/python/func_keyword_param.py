#!/usr/bin/env python
def person(name, age, **kw):
	print 'name:', name, 'age:', age, 'other:', kw

person('Yang', 25)
person('Yang', 25, city='Shanghai')
kw = {'city':'Shanghai', 'job':'Engineer'}
person('Yang', 25, **{'city':'Shanghai', 'job':'Engineer'})
person('Yang', 25, city=kw['city'], job=kw['job'])
person('Yang', 25, **kw)

def func(a, b, c = 0, *args, **kw):
	print 'a=', a, 'b=', b, 'args=', args, 'kw=', kw
func(1, 2)
func(1, 2, 3)
func(1, 2, 3, 'a', 'b')
func(1, 2, 3, 'a', 'b', **{'name':'yang'})
func(1, 2, 3, 'a', 'b', name='yang')
list = ['a', 'b', 'c']
dict = {'name':'yang', 'age':'25', 'gender':'male'}
func(1, 2, 3, *list, **dict)
