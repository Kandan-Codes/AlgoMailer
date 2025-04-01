
package EmailScheduler;

import EmailSender.EmailSender;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleEmail {
    public static void scheduleEmailing() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable emailTask = () -> {
            EmailSender.sendEmails();
        };

        long initialDelay = computeInitialDelay(8,24);
        long period = 24 * 60;

        scheduler.scheduleAtFixedRate(emailTask, initialDelay, period, TimeUnit.MINUTES);
    }

    private static long computeInitialDelay(int targetHour, int targetMinute) {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.LocalDateTime nextRun = now.withHour(targetHour).withMinute(targetMinute).withSecond(0);
        if(now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }
        return java.time.Duration.between(now, nextRun).toMinutes();
    }
}

