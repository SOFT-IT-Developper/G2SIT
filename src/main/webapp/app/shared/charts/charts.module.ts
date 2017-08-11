import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartsModule as Ng2Charts } from 'ng2-charts';

import { ChartsRoutingModule } from './charts-routing.module';
import { ChartsComponent } from './charts.component';

@NgModule({
    imports: [
        CommonModule,
        Ng2Charts

    ],
    declarations: [ChartsComponent],
    exports: [ChartsComponent]
})
export class ChartsModule { }
