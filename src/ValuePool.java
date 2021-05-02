import java.util.HashMap;

public class ValuePool<T extends Valuable> extends HashMap<String, T>{

    public HashMap<String, Double> calTotal(ValCounter<T> v){
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
