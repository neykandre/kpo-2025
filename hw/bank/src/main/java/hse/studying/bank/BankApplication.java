package hse.studying.bank;

import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.menu.ConsoleApplication;
import hse.studying.bank.menu.MainMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BankApplication.class, args);

        ConsoleApplication consoleApplication = context.getBean(ConsoleApplication.class);
        consoleApplication.run();

        context.close();
    }
}
