import matplotlib.pyplot as plt

path = '/Users/generalic/Documents/projects/APRLabs/output.txt'
data = [map(float, line.split(',')) for line in open(path, 'r') if line.strip()]

ts = map(lambda x: float(x), data[0])
x1s = map(lambda x: float(x), data[1])
x2s = map(lambda x: float(x), data[2])

print ts
print x1s
print x2s

plt.plot(ts, x1s, label='x1')
plt.plot(ts, x2s, label='x2')
plt.legend(loc='best')
plt.grid()
plt.show()
