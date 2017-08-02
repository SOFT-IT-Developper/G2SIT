package bj.softit.g2sit.service;

import bj.softit.g2sit.repository.StockRepository;
import bj.softit.g2sit.repository.StockRespersitoryExtend;
import bj.softit.g2sit.repository.search.StockSearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@Transactional
public class StockServiceExtend extends StockService {
   private  final StockRespersitoryExtend stockRespersitoryExtend;
    public StockServiceExtend(StockRepository stockRepository, StockSearchRepository stockSearchRepository, StockRespersitoryExtend stockRespersitoryExtend) {
        super(stockRepository, stockSearchRepository);
        this.stockRespersitoryExtend = stockRespersitoryExtend;
    }
    public long countAllStockBetwenToDate(ZonedDateTime fromDate, ZonedDateTime toDate){
        return    this.stockRespersitoryExtend.countAllByDateentrerBetween( fromDate, toDate);
//     return    this.historiquesRepository.countHistoriquesByDateBetweenAndStocksIsNotNull( fromDate, toDate);
    }}
