import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { G2SitSharedModule } from '../../shared';
import {
    HistoriquesService,
    HistoriquesPopupService,
    HistoriquesComponent,
    HistoriquesDetailComponent,
    HistoriquesDialogComponent,
    HistoriquesPopupComponent,
    HistoriquesDeletePopupComponent,
    HistoriquesDeleteDialogComponent,
    historiquesRoute,
    historiquesPopupRoute,
    HistoriquesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...historiquesRoute,
    ...historiquesPopupRoute,
];

@NgModule({
    imports: [
        G2SitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HistoriquesComponent,
        HistoriquesDetailComponent,
        HistoriquesDialogComponent,
        HistoriquesDeleteDialogComponent,
        HistoriquesPopupComponent,
        HistoriquesDeletePopupComponent,
    ],
    entryComponents: [
        HistoriquesComponent,
        HistoriquesDialogComponent,
        HistoriquesPopupComponent,
        HistoriquesDeleteDialogComponent,
        HistoriquesDeletePopupComponent,
    ],
    providers: [
        HistoriquesService,
        HistoriquesPopupService,
        HistoriquesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class G2SitHistoriquesModule {}
