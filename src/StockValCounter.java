public interface StockValCounter<T extends StockInfo> {
    double getCountedPrice(T target);
}
