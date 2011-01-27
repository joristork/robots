import matplotlib.pyplot as plt

data = [[ 0, 0.00, int(b'00000000', 2)],
        [ 1, 0.02, int(b'00000001', 2)],
        [ 2, 0.09, int(b'00000010', 2)],
        [ 3, 0.29, int(b'00000011', 2)],
        [ 4, 0.52, int(b'00000110', 2)],
        [ 5, 0.74, int(b'00001111', 2)],
        [ 6, 0.98, int(b'00011000', 2)],
        [ 7, 1.38, int(b'00011111', 2)],
        [ 8, 2.19, int(b'00110011', 2)],
        [ 9, 3.42, int(b'01001111', 2)],
        [10, 4.77, int(b'01110001', 2)]]

plt.plot(zip(*data)[0], zip(*data)[1])
plt.xlabel('potentiometer')
plt.ylabel('spanning (Volt)')
plt.grid(True)
plt.savefig('plot.png')

for i, b in enumerate(zip(*data)[2]):
    if b > 0:
        print i, b, zip(*data)[1][i] / b
    else:
        print i, 0, 0
