import {NgModule, CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID} from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    G2SitSharedLibsModule,
    G2SitSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent
} from './';
import { SidebarComponent } from './sidebar/sidebar.component';
import {JhiLanguageHelper} from './language/language.helper';
import {JhiLanguageService} from 'ng-jhipster';
import {BarChartComponent} from '../charts/bar-chart/bar-chart.component';
import {ChartsModule} from './charts/charts.module';

@NgModule({
    imports: [
        G2SitSharedLibsModule,
        G2SitSharedCommonModule,
        ChartsModule

    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        BarChartComponent

    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe,
        { provide: LOCALE_ID, useValue: 'fr-FR' }
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        G2SitSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe, BarChartComponent, ChartsModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class G2SitSharedModule {}
