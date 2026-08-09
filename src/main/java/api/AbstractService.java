package api;

import api.clients.AppClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractService {

    protected static final Logger log = LogManager.getLogger(AbstractService.class);

    public static final String FACT_ENDPOINT = "fact";
    public static final String FACTS_ENDPOINT = "facts";
    public static final String BREEDS_ENDPOINT = "breeds";
    public static final String QUERY_PARAM_LENGTH = "max_length";
    public static final String QUERY_PARAM_LIMIT = "limit";

    protected final AppClient client = new AppClient();
}
