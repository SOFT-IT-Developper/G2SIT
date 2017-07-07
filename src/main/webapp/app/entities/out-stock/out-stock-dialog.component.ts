import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OutStock } from './out-stock.model';
import { OutStockPopupService } from './out-stock-popup.service';
import { OutStockService } from './out-stock.service';
import { Operant, OperantService } from '../operant';
import { Produits, ProduitsService } from '../produits';
import { ResponseWrapper } from '../../shared';
import {Stock, StockService } from '../stock';

@Component({
    selector: 'jhi-out-stock-dialog',
    templateUrl: './out-stock-dialog.component.html'
})
export class OutStockDialogComponent implements OnInit {

    outStock: OutStock;
    authorities: any[];
    isSaving: boolean;

    operants: Operant[];

    produits: Produits[];

    stock: Stock[];

    constructor(
        public activeModal:
            NgbActiveModal,
        private alertService: JhiAlertService,
        private outStockService: OutStockService,
        private operantService: OperantService,
        private produitsService: ProduitsService,
        private stockService: StockService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.operantService.query()
            .subscribe((res: ResponseWrapper) => { this.operants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.produitsService.query()
            .subscribe((res: ResponseWrapper) => { this.produits = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;

        if (this.outStock.id !== undefined) {
            this.subscribeToSaveResponse(
                this.outStockService.update(this.outStock), false);
        } else {
            this.subscribeToSaveResponse(
                this.outStockService.create(this.outStock), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<OutStock>, isCreated: boolean) {
        result.subscribe((res: OutStock) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: OutStock, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'g2SitApp.outStock.created'
            : 'g2SitApp.outStock.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'outStockListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }
    onChange(produitsOption) {
        console.log( produitsOption)
        console.log( produitsOption.id)
        this.stockService.findByProduitId(produitsOption.id)
            .subscribe((res: ResponseWrapper) => { this.stock = res.json; console.log(this.stock) }, (res: ResponseWrapper) => {this.onError(res.json); console.log(res.json);} );

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

    trackOperantById(index: number, item: Operant) {
        return item.id;
    }

    trackProduitsById(index: number, item: Produits) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-out-stock-popup',
    template: ''
})
export class OutStockPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private outStockPopupService: OutStockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.outStockPopupService
                    .open(OutStockDialogComponent, params['id']);
            } else {
                this.modalRef = this.outStockPopupService
                    .open(OutStockDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

}
