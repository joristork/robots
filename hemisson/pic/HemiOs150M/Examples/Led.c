#include <hemisson.h>

// Add your variables here
// ...

void main()
{
    hemisson_init();			// Start Hemisson Initialisation
    while(1)
    {
      // Add your Code here
      // ...
      hemisson_led_frontright(1);	// Turn On Led FrontRight 
      hemisson_delay_s(1);             	// Wait 1s
      hemisson_led_frontright(0);	// Turn Off Led FrontRight
      hemisson_delay_s(1);             	// Wait 1s
    }
}
