package api.tests;

import api.TickerData;
import api.interfaces.TickerComparator;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ApiTests {

    public List<TickerData> getAllTickers () {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://api.kucoin.com/api/v1/market/allTickers")
                .then().log().body()
                .extract().jsonPath().getList("data.ticker", TickerData.class);

    }

    @Test
    public void getALlUSDTTickers () {
        List<TickerData> usdtTickers = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT")).collect(Collectors.toList());
        Assert.assertTrue(usdtTickers.stream().allMatch(x->x.getSymbol().endsWith("USDT")));
        int i = 0;
    }
    @Test
    public void getALlUSDTTickersNew () {
        List<TickerData> usdtTickers = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT")).collect(Collectors.toList());
        Assert.assertTrue(usdtTickers.stream().allMatch(x->x.getSymbol().endsWith("USDT")));
        int i = 0;
    }
    @Test
    public void getALlUSDTTickersNewNew () {
        List<TickerData> usdtTickers = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT")).collect(Collectors.toList());
        Assert.assertTrue(usdtTickers.stream().allMatch(x->x.getSymbol().endsWith("USDT")));
        int i = 0;
    }



    @Test
    public void getFromHighToLowChangeRate () {
        List<TickerData> highTickers = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT")).sorted(new Comparator<TickerData>() {
            @Override
            public int compare(TickerData o1, TickerData o2) {
                return o2.getChangeRate().compareTo(o1.getChangeRate());
            }
        }).collect(Collectors.toList());
        List<TickerData> top10Tickers = highTickers.stream().limit(10).collect(Collectors.toList());

        Assert.assertTrue(top10Tickers.stream().allMatch(x->x.getSymbol().endsWith("USDT")));
    }

    @Test
    public void getFromLowToHighChangeRate () {
        List<TickerData> lowTickers = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT"))
                .sorted(new TickerComparator()).limit(10).collect(Collectors.toList());
    }

    @Test
    public void map () {
        Map<String, Float> usd = new HashMap<>();

        getAllTickers().stream().forEach(x->usd.put(x.getSymbol(), Float.parseFloat(x.getChangeRate())));
    }

}
