import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import {Router} from "@angular/router";

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import {Stock} from "../stock.model";
import {Produits} from "../../produits/produits.model";
import {Operant} from "../../operant/operant.model";
import {StockService} from "../stock.service";
import {ProduitsService} from "../../produits/produits.service";
import {OperantService} from "../../operant/operant.service";
import {ResponseWrapper} from "../../../shared/model/response-wrapper.model";


@Component({
  selector: 'jhi-new-stock',
  templateUrl: './new-stock.component.html',
  styles: []
})
export class NewStockComponent implements OnInit {

    stock: Stock;
    authorities: any[];
    isSaving: boolean;

    produits: Produits[];

    operants: Operant[];

    constructor(private router: Router,
       /* public activeModal: NgbActiveModal,*/
        private alertService: JhiAlertService,
        private stockService: StockService,
        private produitsService: ProduitsService,
        private operantService: OperantService,
        private eventManager: JhiEventManager
    ) {
        this.stock = new Stock();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.produitsService
            .query({filter: 'stock(nameproduit)-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.stock.produit || !this.stock.produit.id) {
                    this.produits = res.json;
                } else {
                    this.produitsService
                        .find(this.stock.produit.id)
                        .subscribe((subRes: Produits) => {
                            this.produits = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.operantService.query()
            .subscribe((res: ResponseWrapper) => { this.operants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        // this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stock.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stockService.update(this.stock), false);
        } else {
            this.subscribeToSaveResponse(
                this.stockService.create(this.stock), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Stock>, isCreated: boolean) {
        result.subscribe((res: Stock) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Stock, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'g2SitApp.stock.created'
                : 'g2SitApp.stock.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'stockListModification', content: 'OK'});
        this.isSaving = false;
        this.router.navigate(['../stock']);
         // this.activeModal.dismiss(result);
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
    onChange(produitsOption) : boolean {
        console.log(produitsOption)
        console.log(produitsOption.id)
        this.stockService.findByProduitId(produitsOption.id)
            .subscribe(res => {
              //  this.stock = res;
                if(res != null){
                    return true
                }
                return false;

            }, (res: ResponseWrapper) => {

                this.onError(res.json);
                console.log(res.json);
                return false ;
            });
        return false;

    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackProduitsById(index: number, item: Produits) {
        return item.id;
    }

    trackOperantById(index: number, item: Operant) {
        return item.id;
    }
}
