package bj.softit.g2sit.service;

import bj.softit.g2sit.repository.HistoriquesRepository;
import bj.softit.g2sit.repository.ProduitsRepository;
import bj.softit.g2sit.repository.StockRespersitoryExtend;
import bj.softit.g2sit.repository.search.HistoriquesSearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
@Service
@Transactional
public class HistoriqueServiceExtend extends HistoriquesService {
    private final StockRespersitoryExtend stockRespersitoryExtend;
    public HistoriqueServiceExtend(HistoriquesRepository historiquesRepository, ProduitsRepository produitsRepository, HistoriquesSearchRepository historiquesSearchRepository, UserService userService, StockRespersitoryExtend stockRespersitoryExtend) {
        super(historiquesRepository, produitsRepository, historiquesSearchRepository, userService);
        this.stockRespersitoryExtend = stockRespersitoryExtend;
    }
    public long countAllRepportBetwenToDate(ZonedDateTime fromDate, ZonedDateTime toDate){
     return    this.historiquesRepository.countAllByDateBetween( fromDate, toDate);
//     return    this.historiquesRepository.countHistoriquesByDateBetweenAndStocksIsNotNull( fromDate, toDate);
    }
}
