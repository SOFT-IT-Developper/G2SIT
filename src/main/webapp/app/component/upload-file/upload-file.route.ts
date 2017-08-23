import {Route} from '@angular/router';
import {UploadFileComponent} from './upload-file.component';
import {UserRouteAccessService} from '../../shared/auth/user-route-access-service';

export const UPLOAD_ROUTE: Route = {
    path: 'upload',
    component: UploadFileComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'home.title'
    },
    canActivate: [UserRouteAccessService]
};
