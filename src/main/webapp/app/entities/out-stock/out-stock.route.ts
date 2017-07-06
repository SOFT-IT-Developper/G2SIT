import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OutStockComponent } from './out-stock.component';
import { OutStockDetailComponent } from './out-stock-detail.component';
import { OutStockPopupComponent } from './out-stock-dialog.component';
import { OutStockDeletePopupComponent } from './out-stock-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OutStockResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const outStockRoute: Routes = [
    {
        path: 'out-stock',
        component: OutStockComponent,
        resolve: {
            'pagingParams': OutStockResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.outStock.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'out-stock/:id',
        component: OutStockDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.outStock.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const outStockPopupRoute: Routes = [
    {
        path: 'out-stock-new',
        component: OutStockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.outStock.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'out-stock/:id/edit',
        component: OutStockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.outStock.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'out-stock/:id/delete',
        component: OutStockDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.outStock.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
