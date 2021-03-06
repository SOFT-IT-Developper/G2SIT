import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Historiques } from './historiques.model';
import { HistoriquesPopupService } from './historiques-popup.service';
import { HistoriquesService } from './historiques.service';
import { User, UserService } from '../../shared';
import { Stock, StockService } from '../stock';
import { OutStock, OutStockService } from '../out-stock';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-historiques-dialog',
    templateUrl: './historiques-dialog.component.html'
})
export class HistoriquesDialogComponent implements OnInit {

    historiques: Historiques;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    stocks: Stock[];

    outstocks: OutStock[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private historiquesService: HistoriquesService,
        private userService: UserService,
        private stockService: StockService,
        private outStockService: OutStockService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.stockService.query()
            .subscribe((res: ResponseWrapper) => { this.stocks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.outStockService.query()
            .subscribe((res: ResponseWrapper) => { this.outstocks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.historiques.id !== undefined) {
            this.subscribeToSaveResponse(
                this.historiquesService.update(this.historiques), false);
        } else {
            this.subscribeToSaveResponse(
                this.historiquesService.create(this.historiques), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Historiques>, isCreated: boolean) {
        result.subscribe((res: Historiques) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Historiques, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'g2SitApp.historiques.created'
            : 'g2SitApp.historiques.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'historiquesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackStockById(index: number, item: Stock) {
        return item.id;
    }

    trackOutStockById(index: number, item: OutStock) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-historiques-popup',
    template: ''
})
export class HistoriquesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historiquesPopupService: HistoriquesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.historiquesPopupService
                    .open(HistoriquesDialogComponent, params['id']);
            } else {
                this.modalRef = this.historiquesPopupService
                    .open(HistoriquesDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
