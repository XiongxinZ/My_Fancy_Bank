import java.util.HashMap;

public class StockPool<T extends StockInfo> extends HashMap<String, T>{

    public HashMap<String, Double> calTotal(StockValCounter<T> v){
        HashMap<String, Double> ret = new HashMap<>();
        ret.put("USD", 0.0);
        ret.put("CNY", 0.0);
        ret.put("JPY", 0.0);
        for (T value : values()) {
            ret.put(value.getCurrency(), ret.get(value.getCurrency())+ v.getCountedPrice(value));
        }
        return ret;
    }
}
