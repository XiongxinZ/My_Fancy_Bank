import com.intrinio.api.*;
import com.intrinio.models.*;
import com.intrinio.invoker.*;
import com.intrinio.invoker.auth.*;
import org.threeten.bp.*;
import java.math.BigDecimal;
import java.util.*;

public class test {
    public static void main(String[] args) throws Exception {

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth auth = (ApiKeyAuth) defaultClient.getAuthentication("ApiKeyAuth");
        auth.setApiKey("OjE5ODkyYjc5MzhjMDE2ZWEwMDJmNTI2Y2MzNWY3OTYx");
        defaultClient.setAllowRetries(true);

        SecurityApi securityApi = new SecurityApi();
        String identifier = "AAPL";
        LocalDate startDate = LocalDate.of(2018,1,01);
        LocalDate endDate = LocalDate.of(2019,1,01);
        String frequency = "daily";
        Integer pageSize = 100;
        String nextPage = null;

        ApiResponseSecurityStockPrices result = securityApi.getSecurityStockPrices(identifier, startDate, endDate, frequency, pageSize, nextPage);
        System.out.println(result);
    }
}
