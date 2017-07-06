import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Operant } from './operant.model';
import { OperantPopupService } from './operant-popup.service';
import { OperantService } from './operant.service';

@Component({
    selector: 'jhi-operant-dialog',
    templateUrl: './operant-dialog.component.html'
})
export class OperantDialogComponent implements OnInit {

    operant: Operant;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private operantService: OperantService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.operant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.operantService.update(this.operant), false);
        } else {
            this.subscribeToSaveResponse(
                this.operantService.create(this.operant), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Operant>, isCreated: boolean) {
        result.subscribe((res: Operant) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Operant, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'g2SitApp.operant.created'
            : 'g2SitApp.operant.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'operantListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-operant-popup',
    template: ''
})
export class OperantPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operantPopupService: OperantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.operantPopupService
                    .open(OperantDialogComponent, params['id']);
            } else {
                this.modalRef = this.operantPopupService
                    .open(OperantDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
