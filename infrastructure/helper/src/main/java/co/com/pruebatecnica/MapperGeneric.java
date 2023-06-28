package co.com.pruebatecnica;

public interface MapperGeneric <E, M>{

    E toEntity(M model);
    M toModel(E entity);
}
