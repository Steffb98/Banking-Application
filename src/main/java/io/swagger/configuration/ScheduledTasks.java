package io.swagger.configuration;

import io.swagger.service.AccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ScheduledTasks {

    private static final Logger logger = Logger.getLogger(ScheduledTasks.class.getName());
    private final AccountService accountService;

    public ScheduledTasks(AccountService accountService) {
        this.accountService = accountService;
    }

    @Scheduled(cron = "0 1 * * * ?")
    public void resetAccountDayLimit() {
        logger.log(Level.INFO, "Resetting day limit...");
        accountService.getAllAccounts().forEach(account -> {
            account.setNumberoftransactions(0L);
            accountService.updateAccount(account);
        });

    }
}