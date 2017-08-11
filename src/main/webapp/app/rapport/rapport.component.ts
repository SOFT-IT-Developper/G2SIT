import {Component, OnDestroy, OnInit} from '@angular/core';
import {OutStockService} from '../entities/out-stock/out-stock.service';
import {JhiParseLinks} from 'ng-jhipster';
import {PaginationConfig} from '../blocks/config/uib-pagination.config';
import {ITEMS_PER_PAGE} from '../shared/constants/pagination.constants';
import {ResponseWrapper} from '../shared/model/response-wrapper.model';
import {DatePipe} from '@angular/common';
import {Historiques} from '../entities/historiques/historiques.model';
import {HistoriquesService} from '../entities/historiques/historiques.service';
import {ProduitsService} from '../entities/produits/produits.service';
import {Produits} from '../entities/produits/produits.model';
import {EssenceNg2PrintComponent} from 'essence-ng2-print';
// import {routerTransition} from '../router.animations';

declare var $;

@Component({
    selector: 'jhi-rapport',
    templateUrl: './rapport.component.html',
    styles: [],
    styleUrls: [
        'style.css'
    ]
    // animations: [routerTransition()]
})

export class RapportComponent implements OnInit {

    // audits: Audit[];
    out_rapport: Historiques[];
    fromDate: string;
    itemsPerPage: any;
    links: any;
    page: number;
    orderProp: string;
    reverse: boolean;
    toDate: string;
    totalItems: number;
    findByTodate: boolean;
    findByTodateAndProduit: boolean;
    produits: Produits[];
    produit: any;
    showDate: any;
    isprintable = false;
    printCSS: string[];
    printStyle: string;
    modeAll: true;
    titleRepport: string;

    constructor(private outService: OutStockService,
                private produitsService: ProduitsService,
                private parseLinks: JhiParseLinks,
                private paginationConfig: PaginationConfig,
                private datePipe: DatePipe,
                private historiquesService: HistoriquesService) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 1;
        this.reverse = false;
        this.orderProp = 'datesortir';
        // this.printCSS = ['http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css'];

    }

    getRapport() {
        return this.sortRapport(this.out_rapport);
    }

    loadPage(page: number) {
        this.page = page;
        this.onChangeDate();
    }

    ngOnInit() {
        this.today();
        this.previousMonth();
        this.onChangeDate();
        this.produitsService.query()
            .subscribe((res: ResponseWrapper) => {
                this.produits = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    onChangeDate() {
        this.titleRepport = `Rapport des opérations de ${this.datePipe.transform( this.fromDate,'dd  MMMM  y' ) } à ${this.datePipe.transform( this.toDate,'dd  MMMM  y' ) }   `;
        console.log(this.fromDate);
        console.log(this.toDate);
        if (this.produit != null) {
            this.historiquesService.findByDateAndProduit({
                page: this.page - 1, size: this.itemsPerPage,
                fromDate: this.fromDate, toDate: this.toDate, produitId: this.produit
            }).subscribe((res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
                (res: ResponseWrapper) => this.onError(res.json)

                /*(res) => {
            console.log(res);
            this.out_rapport = res.json();
            this.links = this.parseLinks.parse(res.headers.get('link'));
            this.totalItems = + res.headers.get('X-Total-Count');*/
            );

        } else {
            this.historiquesService.findByDate({
                page: this.page - 1, size: this.itemsPerPage,
                fromDate: this.fromDate, toDate: this.toDate
            }).subscribe((res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
                (res: ResponseWrapper) => this.onError(res.json)

                /*(res) => {
            console.log(res);
            this.out_rapport = res.json();
            this.links = this.parseLinks.parse(res.headers.get('link'));
            this.totalItems = + res.headers.get('X-Total-Count');*/
            );
        }
    }

    previousMonth() {
        const dateFormat = 'yyyy-MM-dd';
        let fromDate: Date = new Date();

        if (fromDate.getMonth() === 0) {
            fromDate = new Date(fromDate.getFullYear() - 1, 11, fromDate.getDate());
        } else {
            fromDate = new Date(fromDate.getFullYear(), fromDate.getMonth() - 1, fromDate.getDate());
        }

        this.fromDate = this.datePipe.transform(fromDate, dateFormat);
    }

    today() {
        const dateFormat = 'yyyy-MM-dd';
        // Today + 1 day - needed if the current day must be included
        const today: Date = new Date();
        today.setDate(today.getDate() + 1);
        const date = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        this.toDate = this.datePipe.transform(date, dateFormat);
    }

    private sortRapport(rapport: Historiques[]) {
        if (!rapport || !rapport.length) {
            return;
        }
        // console.log(rapport)
        rapport = rapport.slice(0).sort((a, b) => {
            if (a[this.orderProp] < b[this.orderProp]) {
                return -1;
            } else if ([b[this.orderProp] < a[this.orderProp]]) {
                return 1;
            } else {
                return 0;
            }
        });

        return this.reverse ? rapport.reverse() : rapport;
    }

    private onSuccess(data, headers) {
        console.log(data);
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        // console.log(res);
        // this.out_rapport = res.json();
        // this.links = this.parseLinks.parse(res.headers.get('link'));
        // this.totalItems = + res.headers.get('X-Total-Count');
        // this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.out_rapport = data;
    }

    private onError(error) {
        // this.alertService.error(error.message, null, null);
    }

    /*   transition() {
           this.router.navigate(['/rapport'], {queryParams:
               {
                   page: this.page,
                   size: this.itemsPerPage,
                   search: this.currentSearch,
                   sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
               }
           });
           this.onChangeDate();
       }*/

    /*  search(query) {
          if (!query) {
             // return this.clear();
          }
          this.page = 0;
          this.currentSearch = query;
          this.router.navigate(['/rapport', {
              search: this.currentSearch,
              page: this.page,
              sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
          }]);
          this.loadAll();
      }*/

    onChange(event: string): void {
        console.log(' selected ' + event);
        if (event !== 'all') {
            console.log(this.produit);
            this.produit = JSON.parse(event);
            console.log('produit');
            this.onChangeDate();
        } else {
            this.produit = null;
            this.onChangeDate();
        }

    }

    print(): void {
        this.isprintable = true;
        let printContents, popupWin;
        printContents = document.getElementById('print-section').innerHTML;
        popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
        popupWin.document.open();
        popupWin.document.write(`
      <html>
        <head>
          <title>Rapport</title>
          <!--<link rel="stylesheet" type="text/css" href="style.css" />-->
    <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.css" media="all"/>
      
        </head>
    <body onload="window.print();window.close()">${printContents}</body>
      </html>`
        );
        popupWin.document.close();
    }
    printComplete() {
        console.log('L\'impression est terminée!！');
       // this.showHead = true;
       // this.hideTable1 = false;
    }
    starMode(event) {
        console.log(event);
        if ( this.modeAll ) {
            this.itemsPerPage = 10000 * 10000 ;
            this.onChangeDate();
        } else {
            this.itemsPerPage = 20;
            this.onChangeDate();
        }
        console.log(this.modeAll);

    };
}
