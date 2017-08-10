import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import {Subscription} from 'rxjs/Subscription';
import { Account, LoginModalService, Principal } from '../shared';
import {ProduitsService} from '../entities/produits/produits.service';
import {StockService} from '../entities/stock/stock.service';
import {OutStockService} from '../entities/out-stock/out-stock.service';
@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    nbProduit: any;
    nbStock: any;
    nbStockManquant: any;
    nboutStock: any;

    constructor(
        private principal: Principal,
        private stockService: StockService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private produitsService: ProduitsService,
        private outStockService: OutStockService,
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.produitsService.cont().subscribe((res: any) => {
            this.nbProduit = res;
            console.log(this.nbProduit);
        });
        this.stockService.cont().subscribe((res: any) => {
            this.nbStock = res;
            console.log(this.nbStock);
        });
        this.stockService.contManquant().subscribe((res: any) => {
            this.nbStockManquant = res;
            console.log(this.nbStockManquant);
        });
        this.outStockService.cont().subscribe((res: any) => {
            this.nboutStock = res;
            console.log(this.nboutStock);
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
