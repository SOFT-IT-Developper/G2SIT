import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { G2SitSharedModule } from '../../shared';
import {
    OutStockService,
    OutStockPopupService,
    OutStockComponent,
    OutStockDetailComponent,
    OutStockDialogComponent,
    OutStockPopupComponent,
    OutStockDeletePopupComponent,
    OutStockDeleteDialogComponent,
    outStockRoute,
    outStockPopupRoute,
    OutStockResolvePagingParams,
} from './';
import {EssenceNg2PrintModule} from 'essence-ng2-print';

const ENTITY_STATES = [
    ...outStockRoute,
    ...outStockPopupRoute,
];

@NgModule({
    imports: [
        G2SitSharedModule,
        EssenceNg2PrintModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OutStockComponent,
        OutStockDetailComponent,
        OutStockDialogComponent,
        OutStockDeleteDialogComponent,
        OutStockPopupComponent,
        OutStockDeletePopupComponent,
    ],
    entryComponents: [
        OutStockComponent,
        OutStockDialogComponent,
        OutStockPopupComponent,
        OutStockDeleteDialogComponent,
        OutStockDeletePopupComponent,
    ],
    providers: [
        OutStockService,
        OutStockPopupService,
        OutStockResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class G2SitOutStockModule {}
