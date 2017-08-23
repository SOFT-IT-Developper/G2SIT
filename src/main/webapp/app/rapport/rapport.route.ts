import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import {RapportComponent} from './rapport.component';

export const RAPPORT_ROUTE: Route = {
    path: 'rapport',
    component: RapportComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'home.title'
    },
    canActivate: [UserRouteAccessService]
};
