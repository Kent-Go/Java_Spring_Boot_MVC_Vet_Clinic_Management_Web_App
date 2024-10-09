package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

@Service
public class ReminderScheduler {

    @Autowired
    private EmailService emailService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Logger logger = LoggerFactory.getLogger(ReminderScheduler.class);

    // Map to track the scheduled email tasks for each appointment
    private final ConcurrentHashMap<Integer, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    // Modify the scheduleEmail method to return a boolean indicating success or failure
    public boolean scheduleEmail(String to, String subject, String text, long delayInSeconds, int appointmentId) {
        try {
            // Schedule the email and track it
            ScheduledFuture<?> scheduledTask = scheduler.schedule(() -> {
                boolean emailSent = emailService.sendReminderEmail(to, subject, text);  // Call sendReminderEmail in EmailService
                if (emailSent) {
                    logger.info("Email successfully sent to {}", to);
                } else {
                    logger.error("Failed to send email to {}", to);
                }
            }, delayInSeconds, TimeUnit.SECONDS);

            // Store the scheduled task for future cancellation if needed
            scheduledTasks.put(appointmentId, scheduledTask);
            return true;  // Successfully scheduled the task
        } catch (Exception e) {
            logger.error("Failed to schedule email to {}: {}", to, e.getMessage());
            return false;  // Failed to schedule the task
        }
    }

    // Method to cancel the scheduled email task
    public boolean cancelScheduledEmail(int appointmentId) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(appointmentId);
        if (scheduledTask != null && !scheduledTask.isDone()) {
            // Cancel the scheduled task
            boolean cancelled = scheduledTask.cancel(false);  // 'false' does not interrupt if already running
            if (cancelled) {
                logger.info("Successfully canceled scheduled email for appointment ID {}", appointmentId);
                scheduledTasks.remove(appointmentId);  // Remove the task from the map after cancellation
            } else {
                logger.error("Failed to cancel scheduled email for appointment ID {}", appointmentId);
            }
            return cancelled;
        } else {
            logger.warn("No scheduled email found for appointment ID {}", appointmentId);
            return false;
        }
    }
}