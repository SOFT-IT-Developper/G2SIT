import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { G2SitSharedModule } from '../../shared';
import {
    OperantService,
    OperantPopupService,
    OperantComponent,
    OperantDetailComponent,
    OperantDialogComponent,
    OperantPopupComponent,
    OperantDeletePopupComponent,
    OperantDeleteDialogComponent,
    operantRoute,
    operantPopupRoute,
    OperantResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...operantRoute,
    ...operantPopupRoute,
];

@NgModule({
    imports: [
        G2SitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OperantComponent,
        OperantDetailComponent,
        OperantDialogComponent,
        OperantDeleteDialogComponent,
        OperantPopupComponent,
        OperantDeletePopupComponent,
    ],
    entryComponents: [
        OperantComponent,
        OperantDialogComponent,
        OperantPopupComponent,
        OperantDeleteDialogComponent,
        OperantDeletePopupComponent,
    ],
    providers: [
        OperantService,
        OperantPopupService,
        OperantResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class G2SitOperantModule {}
