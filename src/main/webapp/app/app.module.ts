import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { G2SitSharedModule, UserRouteAccessService } from './shared';
import { G2SitHomeModule } from './home/home.module';
import { G2SitAdminModule } from './admin/admin.module';
import { G2SitAccountModule } from './account/account.module';
import { G2SitEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { ChartsModule } from 'ng2-charts';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import {SidebarComponent} from './shared/sidebar/sidebar.component';
import {G2SitRapportModule} from './rapport/rapport.module';
import {ChartsRoutingModule} from './shared/charts/charts-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BarChartComponent } from './charts/bar-chart/bar-chart.component';

@NgModule({
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        G2SitSharedModule,
        G2SitHomeModule,
        G2SitAdminModule,
        G2SitAccountModule,
        G2SitEntityModule,
        G2SitRapportModule,
        ChartsModule,


        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        SidebarComponent,
        BarChartComponent,
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class G2SitAppModule {}
