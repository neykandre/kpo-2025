package hse.studying.bank.importer;

import com.opencsv.bean.CsvToBeanBuilder;
import hse.studying.bank.facades.CommandFacade;
import java.io.StringReader;
import java.util.List;

public class CsvDataImporter<T> extends DataImporter<T> {

    private final Class<T> type;

    public CsvDataImporter(Class<T> type, CommandFacade facade) {
        super(facade);
        this.type = type;
    }

    @Override
    protected List<T> parseData(String rawData) throws ParseException {
        try {
            return new CsvToBeanBuilder<T>(new StringReader(rawData))
                    .withType(type)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new ParseException("CSV parsing error: " + e.getMessage());
        }
    }
}