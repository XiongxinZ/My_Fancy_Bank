public interface Modifiable {
    void markDirty(boolean isDirty);
    boolean isDirty();
}
