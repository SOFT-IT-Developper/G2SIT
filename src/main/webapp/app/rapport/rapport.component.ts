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

@Component({
  selector: 'jhi-rapport',
  templateUrl: './rapport.component.html',
  styles: []
})

export class RapportComponent implements OnInit {

    // audits: Audit[];
    out_rapport: OutStock[]
    fromDate: string;
    itemsPerPage: any;
    links: any;
    page: number;
    orderProp: string;
    reverse: boolean;
    toDate: string;
    totalItems: number;

    constructor(
        private outService: OutStockService,
        private parseLinks: JhiParseLinks,
        private paginationConfig: PaginationConfig,
        private datePipe: DatePipe
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 1;
        this.reverse = false;
        this.orderProp = 'datesortir';
    }

    getAudits() {
        return this.sortAudits(this.out_rapport);
    }

    loadPage(page: number) {
        this.page = page;
        this.onChangeDate();
    }

    ngOnInit() {
        this.today();
        this.previousMonth();
        this.onChangeDate();
    }

    onChangeDate() {
        console.log(this.fromDate)
        console.log(this.toDate)
        this.outService.findByDate({page: this.page - 1, size: this.itemsPerPage,
            fromDate: this.fromDate, toDate: this.toDate}).subscribe((res) => {
            console.log(res);
            this.out_rapport = res.json();
            this.links = this.parseLinks.parse(res.headers.get('link'));
            this.totalItems = + res.headers.get('X-Total-Count');
        });
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

    private sortAudits(audits: OutStock[]) {
        console.log(audits)
        audits = audits.slice(0).sort((a, b) => {
            if (a[this.orderProp] < b[this.orderProp]) {
                return -1;
            } else if ([b[this.orderProp] < a[this.orderProp]]) {
                return 1;
            } else {
                return 0;
            }
        });

        return this.reverse ? audits.reverse() : audits;
    }
}
