package ru.fridrock.jir_backend.mappers;

public interface IMapper<D, E> {
  D mapToDto(E e);
  E mapToEntity(D e);
}
