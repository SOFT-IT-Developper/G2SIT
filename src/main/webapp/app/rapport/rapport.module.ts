import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { G2SitSharedModule } from '../shared';
import {RapportComponent} from './rapport.component';
import {RAPPORT_ROUTE} from './rapport.route';
import {PrintComponent} from '../print/print.component';

@NgModule({
    imports: [
        G2SitSharedModule,
        RouterModule.forRoot([ RAPPORT_ROUTE ], { useHash: true })
    ],
    declarations: [
        RapportComponent,
        PrintComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class G2SitRapportModule {}
