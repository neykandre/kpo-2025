package hse.studying.bank.config;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.importer.CsvDataImporter;
import hse.studying.bank.importer.JsonDataImporter;
import hse.studying.bank.importer.YamlDataImporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImporterConfig {

    private final CommandFacade commandFacade;

    public ImporterConfig(CommandFacade commandFacade) {
        this.commandFacade = commandFacade;
    }

    @Bean
    public JsonDataImporter<BankAccount> jsonBankAccountImporter() {
        return new JsonDataImporter<>(BankAccount.class, commandFacade);
    }

    @Bean
    public JsonDataImporter<Category> jsonCategoryImporter() {
        return new JsonDataImporter<>(Category.class, commandFacade);
    }

    @Bean
    public JsonDataImporter<Operation> jsonOperationImporter() {
        return new JsonDataImporter<>(Operation.class, commandFacade);
    }

    @Bean
    public CsvDataImporter<BankAccount> csvBankAccountImporter() {
        return new CsvDataImporter<>(BankAccount.class, commandFacade);
    }

    @Bean
    public CsvDataImporter<Category> csvCategoryImporter() {
        return new CsvDataImporter<>(Category.class, commandFacade);
    }

    @Bean
    public CsvDataImporter<Operation> csvOperationImporter() {
        return new CsvDataImporter<>(Operation.class, commandFacade);
    }

    @Bean
    public YamlDataImporter<BankAccount> yamlBankAccountImporter() {
        return new YamlDataImporter<>(BankAccount.class, commandFacade);
    }

    @Bean
    public YamlDataImporter<Category> yamlCategoryImporter() {
        return new YamlDataImporter<>(Category.class, commandFacade);
    }

    @Bean
    public YamlDataImporter<Operation> yamlOperationImporter() {
        return new YamlDataImporter<>(Operation.class, commandFacade);
    }
}
