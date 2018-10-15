#!/usr/bin/env python
d = {'Michael': 95, 'Bob': 75, 'Tracy':85}
print d
print len(d)
print d['Michael']
print d['Bob']
d['Jack'] = 90
print d['Jack']
d['Adam'] = 67
d['Adam'] = 99
print d['Adam']
print d.get('Thomas')
print d.get('Thomas', -1)
print d.pop('Bob')
print d
print d.pop('Michael')
print d
print d.pop('Tracy')
print d
s = set([1, 2, 3])
print s
s = set([1, 2, 3, 1, 2, 3, 1, 2, 3])
print s
s.add(4)
print s
s.remove(1)
print s

s1 = set([1, 2, 3])
s2 = set([2, 3, 4])
# intersection
s3 = s1 & s2
print 'Intersection:', s3
# union
s4 = s1 | s2
print 'Union:', s4


# s5 = set([s1, s2, s3, s4])
# print 's5:', s5
# We cannot place mutable object into set

# We cannot replace an element in set directly
# s1.replace(1, 0)
# print s1
s1.remove(1)
s1.add(0)
print s1

