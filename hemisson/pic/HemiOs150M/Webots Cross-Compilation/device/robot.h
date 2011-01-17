#include <hemisson.h>

typedef int DeviceTag;
#define robot_live(r) { reset(); hemisson_init(); }
#define robot_step(ms) delay_ms(ms)

#define HEMISSON_DS0 0
#define HEMISSON_DS1 1
#define HEMISSON_DS2 2
#define HEMISSON_DS3 3
#define HEMISSON_DS4 4
#define HEMISSON_DS5 5
#define HEMISSON_DS6 6
#define HEMISSON_DS7 7

#define HEMISSON_LS0 0
#define HEMISSON_LS1 1
#define HEMISSON_LS3 3
#define HEMISSON_LS4 4
#define HEMISSON_LS5 5
#define HEMISSON_LS6 6
#define HEMISSON_LS7 7

#define HEMISSON_LED_FRONT_LEFT  0
#define HEMISSON_LED_FRONT_RIGHT 1
#define HEMISSON_LED_ON_OFF      2
#define HEMISSON_LED_PGM_EXEC    3

#define robot_get_device(s) (s)
