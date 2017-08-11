import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ChartsComponent } from './charts.component';

const routes: Routes = [
    { path: 'chart', component: ChartsComponent ,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'home.title'
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ChartsRoutingModule { }
