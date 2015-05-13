package security;

public interface Security<T, DTO extends T> {

    T secure(DTO dto);
}
