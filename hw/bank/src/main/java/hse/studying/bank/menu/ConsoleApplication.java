package hse.studying.bank.menu;

import hse.studying.bank.facades.CommandFacade;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsoleApplication {
    private final CommandFacade commandFacade;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        MainMenu mainMenu = new MainMenu();
        mainMenu.execute(commandFacade, scanner);
    }
}
