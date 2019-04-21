package pl.on.full.hack.base.utils;

import org.modelmapper.ModelMapper;
import pl.on.full.hack.base.mapping.LeaguePlayerToDTOMapping;
import pl.on.full.hack.base.mapping.MatchToDTOMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MappingUtil {

    private static ModelMapper modelMapper;

    private MappingUtil() {}

    static {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new LeaguePlayerToDTOMapping());
        modelMapper.addMappings(new MatchToDTOMapping());
    }

    public static <S, T> T map(final S sourceObject, final Class<T> targetClass) {
        return modelMapper.map(sourceObject, targetClass);
    }

    public static <S, T> List<T> mapCollection(final Collection<S> sourceCollection, final Class<T> targetClass) {
        return sourceCollection
                .stream()
                .map(element -> map(element, targetClass))
                .collect(Collectors.toList());
    }
}
