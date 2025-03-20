package hse.studying.bank.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.studying.bank.facades.CommandFacade;
import java.util.List;

public class JsonDataImporter<T> extends DataImporter<T> {
    private final Class<T> type;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonDataImporter(Class<T> type, CommandFacade facade) {
        super(facade);
        this.type = type;
    }

    @Override
    protected List<T> parseData(String rawData) throws ParseException {
        try {
            return mapper.readValue(
                    rawData,
                    mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (Exception e) {
            throw new ParseException("JSON parsing error: " + e.getMessage());
        }
    }
}