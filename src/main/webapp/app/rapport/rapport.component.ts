import {Component, OnDestroy, OnInit} from "@angular/core";
import {OutStock} from "../entities/out-stock/out-stock.model";
import {Subscription} from "rxjs/Subscription";
import {OutStockService} from "../entities/out-stock/out-stock.service";
import {JhiAlertService, JhiEventManager, JhiPaginationUtil, JhiParseLinks} from "ng-jhipster";
import {Principal} from "../shared/auth/principal.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PaginationConfig} from "../blocks/config/uib-pagination.config";
import {ITEMS_PER_PAGE} from "../shared/constants/pagination.constants";
import {ResponseWrapper} from "../shared/model/response-wrapper.model";

import {DatePipe} from "@angular/common";
import {AuditsService} from "../admin/audits/audits.service";
import {Historiques} from "../entities/historiques/historiques.model";
import {HistoriquesService} from "../entities/historiques/historiques.service";
import {ProduitsService} from "../entities/produits/produits.service";
import {Produits} from "../entities/produits/produits.model";
declare var $;
@Component({
  selector: 'jhi-rapport',
  templateUrl: './rapport.component.html',
  styles: []
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


    constructor(
        private outService: OutStockService,
        private produitsService: ProduitsService,
        private parseLinks: JhiParseLinks,
        private paginationConfig: PaginationConfig,
        private datePipe: DatePipe,
        private historiquesService: HistoriquesService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 1;
        this.reverse = false;
        this.orderProp = 'datesortir';
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
        console.log(this.fromDate)
        console.log(this.toDate)
        if(this.produit != null){
            this.historiquesService.findByDateAndProduit({page: this.page - 1, size: this.itemsPerPage,
                fromDate: this.fromDate, toDate: this.toDate, produitId: this.produit}).subscribe((res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
                (res: ResponseWrapper) => this.onError(res.json)


                /*(res) => {
            console.log(res);
            this.out_rapport = res.json();
            this.links = this.parseLinks.parse(res.headers.get('link'));
            this.totalItems = + res.headers.get('X-Total-Count');*/
            );

        }else
            {


        this.historiquesService.findByDate({page: this.page - 1, size: this.itemsPerPage,
            fromDate: this.fromDate, toDate: this.toDate}).subscribe((res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
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
    onChange(event: string ): void {
        this.produit = JSON.parse(event);
        console.log('produit');
        console.log(this.produit);
        this.onChangeDate();
    }
}
