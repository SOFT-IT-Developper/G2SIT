import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { G2SitSharedModule } from '../../shared';
import {
    ProduitsService,
    ProduitsPopupService,
    ProduitsComponent,
    ProduitsDetailComponent,
    ProduitsDialogComponent,
    ProduitsPopupComponent,
    ProduitsDeletePopupComponent,
    ProduitsDeleteDialogComponent,
    produitsRoute,
    produitsPopupRoute,
    ProduitsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...produitsRoute,
    ...produitsPopupRoute,
];

@NgModule({
    imports: [
        G2SitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProduitsComponent,
        ProduitsDetailComponent,
        ProduitsDialogComponent,
        ProduitsDeleteDialogComponent,
        ProduitsPopupComponent,
        ProduitsDeletePopupComponent,
    ],
    entryComponents: [
        ProduitsComponent,
        ProduitsDialogComponent,
        ProduitsPopupComponent,
        ProduitsDeleteDialogComponent,
        ProduitsDeletePopupComponent,
    ],
    providers: [
        ProduitsService,
        ProduitsPopupService,
        ProduitsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class G2SitProduitsModule {}
