import {Component, OnInit, OnDestroy, ViewChild, ElementRef} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Stock } from './stock.model';
import { StockService } from './stock.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {EssenceNg2PrintComponent} from 'essence-ng2-print';

@Component({
    selector: 'jhi-stock',
    templateUrl: './stock.component.html',
    styleUrls: [
        'stock.component.scss'
    ]
})
export class StockComponent implements OnInit, OnDestroy {

currentAccount: any;
    stocks: Stock[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    @ViewChild('print1') printComponent1: EssenceNg2PrintComponent;
    @ViewChild('print2') printComponent2: EssenceNg2PrintComponent;

    printDiv: any;
    showHead = true;
    hideTable1 = false;
    datas: any[];
    printCSS: string[];
    printStyle: string;
    modeimp: true;
    // editorText = '<p style="text-align:center;line-height:150%"><strong><span style="font-family: Roboto;line-height: 150%;font-size: 21px"><span style="font-family:Roboto">sur</span>×××× division projets de références (lettre)</span></strong><span style="font-family: Times New Roman; font-size: 21px; text-indent: 315px;">&nbsp;</span></p><p style="line-height:150%"><strong><span style="font-family: Times New Roman;line-height: 150%;font-size: 16px">Haidian District, l\'eau Conservancy station de contrôle de la qualité：</span></strong></p><p style="text-indent:38px;line-height:150%"><span style=";font-family:Roboto;line-height:150%;font-size:16px">test</span></p><p style="text-indent:38px;line-height:150%"><span style=";font-family:Roboto;line-height:150%;font-size:16px"><span style="font-family:Roboto">test</p><p style="text-indent:38px;line-height:150%"></p>';

    constructor(
        private stockService: StockService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig,
        private elRef: ElementRef
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
        // this.printDiv = document.getElementById('print_section');
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
        this.printCSS = ['http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css'];
       /* this.printStyle =
            `
        th, td {
            color: red !important;
        }
        `;*/
    }

    loadAll() {
        if (this.currentSearch) {
            this.stockService.search({
                query: this.currentSearch,
                size: this.itemsPerPage,
                sort: this.sort()}).subscribe(
                    (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
        }
        this.stockService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/stock'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate(['/stock', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate(['/stock', {
            search: this.currentSearch,
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInStocks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Stock) {
        return item.id;
    }
    registerChangeInStocks() {
        this.eventSubscriber = this.eventManager.subscribe('stockListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
    levelStock(qt) : boolean {
        if (qt < 5){
            return true;
        }
        return false;
    }
   stockIsFinish(qt) : boolean {
        if (qt === 0){
            return true;
        }
        return false;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.stocks = data;
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }


    printComplete() {
        console.log('L\'impression est terminée!！');
        this.showHead = true;
        this.hideTable1 = false;
    }
    starMode(event) {
        console.log(event);
        console.log(this.modeimp);

};

}
