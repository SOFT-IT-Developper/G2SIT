import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Operant } from './operant.model';
import { OperantPopupService } from './operant-popup.service';
import { OperantService } from './operant.service';

@Component({
    selector: 'jhi-operant-delete-dialog',
    templateUrl: './operant-delete-dialog.component.html'
})
export class OperantDeleteDialogComponent {

    operant: Operant;

    constructor(
        private operantService: OperantService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'operantListModification',
                content: 'Deleted an operant'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('g2SitApp.operant.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-operant-delete-popup',
    template: ''
})
export class OperantDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operantPopupService: OperantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.operantPopupService
                .open(OperantDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
