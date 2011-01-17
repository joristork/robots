#define led_set(a,b) {switch(a) { case 0: hemisson_led_frontleft(b); break; case 1: hemisson_led_frontright(b); break; case 2: hemisson_led_pgmexec(b); break; case 3: hemisson_led_onoff(b); break; }
