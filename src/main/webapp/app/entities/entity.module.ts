import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { G2SitCategorieModule } from './categorie/categorie.module';
import { G2SitProduitsModule } from './produits/produits.module';
import { G2SitStockModule } from './stock/stock.module';
import { G2SitOperantModule } from './operant/operant.module';
import { G2SitOutStockModule } from './out-stock/out-stock.module';
import { G2SitHistoriquesModule } from './historiques/historiques.module';
import {EssenceNg2PrintModule} from 'essence-ng2-print';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [

        G2SitCategorieModule,
        G2SitProduitsModule,
        G2SitStockModule,
        G2SitOperantModule,
        G2SitOutStockModule,
        G2SitHistoriquesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class G2SitEntityModule {}
