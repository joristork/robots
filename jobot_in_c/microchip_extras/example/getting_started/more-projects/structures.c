struct {
  int x;
  char y[4];
} s1 = { 0x5A, "abc" };

struct {
  int x[5];
  int y;
} s2 = { { 1, 2, 3, 4, 5 }, 0xA5 };

void main (void)
{
  while (1)
    ;
}
