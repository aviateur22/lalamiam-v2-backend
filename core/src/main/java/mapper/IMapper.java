package mapper;

public interface IMapper<T, U> {
  public U map(T data);
}
