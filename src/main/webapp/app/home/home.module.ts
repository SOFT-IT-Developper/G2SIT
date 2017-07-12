import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { G2SitSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {StatModule} from '../shared/stat/stat.module';

@NgModule({
    imports: [
        G2SitSharedModule,
        StatModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class G2SitHomeModule {}
