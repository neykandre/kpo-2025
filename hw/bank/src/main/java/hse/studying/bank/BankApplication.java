package hse.studying.bank;

import hse.studying.bank.console.ConsoleMenu;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.CommandFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BankApplication.class, args);

        ConsoleMenu menu = context.getBean(ConsoleMenu.class);
        menu.run();

        context.close();
    }
}
