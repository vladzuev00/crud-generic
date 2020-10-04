package by.nhorushko.crudgeneric.mapper;


import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class DtoMappers {

    private static Map<TypeMap, Mapper> mappers = new HashMap<>();

    public static void register(Class<? extends AbstractEntity> from, Class<? extends AbstractDto> to, Mapper mapper) {
        mappers.put(new TypeMap(from, to), mapper);
    }

    public static Mapper get(Class<? extends AbstractEntity> fromEntity, Class<? extends AbstractDto> toDto) {
        TypeMap t = new TypeMap(fromEntity, toDto);
        Mapper mapper = mappers.get(t);
        if (mapper == null) {
            throw new IllegalArgumentException(String.format("Mappers for convert from: %s to: %s", fromEntity, toDto));
        }
        return mapper;
    }

    static class TypeMap {
        Class<? extends AbstractEntity> fromEntity;
        Class<? extends AbstractDto> toDto;

        public TypeMap(Class<? extends AbstractEntity> fromEntity, Class<? extends AbstractDto> toDto) {
            this.fromEntity = fromEntity;
            this.toDto = toDto;
        }

        @Override
        public String toString() {
            return "TypeMap{" +
                    "fromEntity=" + fromEntity +
                    ", toDto=" + toDto +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TypeMap)) return false;
            TypeMap typeMap = (TypeMap) o;
            return fromEntity.equals(typeMap.fromEntity) &&
                    toDto.equals(typeMap.toDto);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fromEntity, toDto);
        }
    }
}
