new Thread() {
    public void run() {
        int tiltX = 0;
        int tiltY = 0;
        while (true) {
            try {
                /* Measure the degrees. */
                tiltX = (int) Math.toDegrees(accel.getTiltX());
                tiltY = (int) Math.toDegrees(accel.getTiltY());
                result = STOP;

                /*
                 * If the Sunspot is in a bigger sideways tilt than a
                 * frontal tilt. If none of the tilts is more than 15,
                 * the Sunspot is in a STOP state.
                 */
                if ((Math.abs(tiltX) > Math.abs(tiltY))
                        && Math.abs(tiltX) > ANGLE) {
                    if (tiltX < 0) {
                        result = RIGHT;
                    } else {
                        result = LEFT;
                    }
                } else if ((Math.abs(tiltX) < Math.abs(tiltY))
                        && Math.abs(tiltY) > ANGLE) {
                    if (tiltY > 0) {
                        result = DRIVE;
                    } else {
                        result = REVERSE;
                    }
                }
                /* Set the result on the leds. */
                set_Leds(result);
            } catch (IOException ex) { }
        }
    }
}.start();
