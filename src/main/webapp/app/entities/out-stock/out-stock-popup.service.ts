import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { OutStock } from './out-stock.model';
import { OutStockService } from './out-stock.service';

@Injectable()
export class OutStockPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private outStockService: OutStockService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.outStockService.find(id).subscribe((outStock) => {
                outStock.datesortir = this.datePipe
                    .transform(outStock.datesortir, 'yyyy-MM-ddThh:mm');
                this.outStockModalRef(component, outStock);
            });
        } else {
            return this.outStockModalRef(component, new OutStock());
        }
    }

    outStockModalRef(component: Component, outStock: OutStock): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.outStock = outStock;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
