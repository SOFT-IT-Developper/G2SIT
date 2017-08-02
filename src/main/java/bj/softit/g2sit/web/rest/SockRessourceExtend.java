package bj.softit.g2sit.web.rest;

import bj.softit.g2sit.service.HistoriquesService;
import bj.softit.g2sit.service.StockService;
import bj.softit.g2sit.service.StockServiceExtend;
import com.codahale.metrics.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;


public class SockRessourceExtend  extends StockResource{
    private final StockServiceExtend stockServiceExtend;
    public SockRessourceExtend(StockService stockService, HistoriquesService historiquesService, StockServiceExtend stockServiceExtend) {
        super(stockService, stockServiceExtend, historiquesService);
        this.stockServiceExtend = stockServiceExtend;
    }
    @GetMapping(path = "/stocks/countAllBetwenToDate",params = {"fromDate", "toDate"})
    @Timed
    public long getCountStockBetwen(@RequestParam(value = "fromDate") LocalDate fromDate,
                                    @RequestParam(value = "toDate") LocalDate toDate) {
//        log.debug("REST request to get count manquant stock");
        long count  = stockServiceExtend.countAllStockBetwenToDate(fromDate.atStartOfDay(ZoneId.systemDefault()),
            toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1));
        return count;
    }
}
