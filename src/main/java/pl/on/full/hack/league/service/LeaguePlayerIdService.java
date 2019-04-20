//package pl.on.full.hack.league.service;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import pl.on.full.hack.league.dto.LeaguePlayerIdDTO;
//import pl.on.full.hack.league.entity.LeaguePlayerId;
//import pl.on.full.hack.league.repository.LeaguePlayerIdRepository;
//
//@Service
//public class LeaguePlayerIdService {
//
//    private ModelMapper modelMapper;
//
//    private LeaguePlayerIdRepository repository;
//
//    @Autowired
//    public LeaguePlayerIdService(ModelMapper modelMapper, LeaguePlayerIdRepository repository) {
//        this.modelMapper = modelMapper;
//        this.repository = repository;
//    }
//
//    public LeaguePlayerIdDTO joinToLeague(final LeaguePlayerIdDTO leaguePlayerIdDTO ){
//        if(leaguePlayerIdDTO != null){
//            final LeaguePlayerId leaguePlayerId = modelMapper.map(leaguePlayerIdDTO, LeaguePlayerId.class);
//            repository.save(leaguePlayerId);
//            return modelMapper.map(leaguePlayerId, LeaguePlayerIdDTO.class);
//        }else {
//            throw new NullPointerException("League and User cannot be null");
//        }
//    }
//}
