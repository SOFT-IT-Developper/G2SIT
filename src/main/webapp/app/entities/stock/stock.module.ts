import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {EssenceNg2PrintModule} from 'essence-ng2-print';
import { G2SitSharedModule } from '../../shared';
import {
    StockService,
    StockPopupService,
    StockComponent,
    StockDetailComponent,
    StockDialogComponent,
    StockPopupComponent,
    StockDeletePopupComponent,
    StockDeleteDialogComponent,
    stockRoute,
    stockPopupRoute,
    StockResolvePagingParams,
} from './';
import { NewStockComponent } from './new-stock/new-stock.component';

const ENTITY_STATES = [
    ...stockRoute,
    ...stockPopupRoute,
];

@NgModule({
    imports: [
        G2SitSharedModule,
        EssenceNg2PrintModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StockComponent,
        StockDetailComponent,
        StockDialogComponent,
        StockDeleteDialogComponent,
        StockPopupComponent,
        StockDeletePopupComponent,
        NewStockComponent,
    ],
    entryComponents: [
        StockComponent,
        StockDialogComponent,
        StockPopupComponent,
        StockDeleteDialogComponent,
        StockDeletePopupComponent,
    ],
    providers: [
        StockService,
        StockPopupService,
        StockResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class G2SitStockModule {}
