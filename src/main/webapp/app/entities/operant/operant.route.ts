import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OperantComponent } from './operant.component';
import { OperantDetailComponent } from './operant-detail.component';
import { OperantPopupComponent } from './operant-dialog.component';
import { OperantDeletePopupComponent } from './operant-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OperantResolvePagingParams implements Resolve<any> {

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

export const operantRoute: Routes = [
    {
        path: 'operant',
        component: OperantComponent,
        resolve: {
            'pagingParams': OperantResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.operant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'operant/:id',
        component: OperantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.operant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operantPopupRoute: Routes = [
    {
        path: 'operant-new',
        component: OperantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.operant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operant/:id/edit',
        component: OperantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.operant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operant/:id/delete',
        component: OperantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'g2SitApp.operant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
