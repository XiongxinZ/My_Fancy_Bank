public interface ValCounter<T extends Valuable> {
    double getCountedPrice(T target);
}
